package com.example.Co_Todo.controller;

import com.example.Co_Todo.dto.ResponseDTO;
import com.example.Co_Todo.dto.TodoDTO;
import com.example.Co_Todo.model.Todo;
import com.example.Co_Todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service; // 서비스 계층의 기능을 사용하기 위함

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){
        try{
            String tempUserId = "temp-user"; // User ID
            LocalDate date = LocalDate.now();
            Todo todo = TodoDTO.toTodo(todoDTO); // DB로 넘길 todo를 만들기
            todo.setId(null); // 명시
            todo.setUserId(tempUserId);
            todo.setDate(date);
            // todo가 올바른 상태인지 확인하고 올바르다면 저장 후에 그대로 반환
            List<Todo> todos = service.create(todo);
            // todos에 들어가 있는 Todo들을 하나씩 순회하면서 TodoDTO로 변경 후에 모두 toDoDTOs에 리스트 형태로 저장
            List<TodoDTO> todoDTOs = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            // todoDTOs을 가지는 ResponseDTO<TodoDTO>인 response 생성
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoDTOs).build();
            return ResponseEntity.ok().body(response); // response를 응답 메시지에 포함하여 전달함으로써 todo 생성 완료
        } catch (Exception e){
            String error = e.getMessage();
            // 에러 정보를 담은 ResponseDTO인 response를 생성
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response); // 에러 정보를 응답 메시지에 포함하여 badRequest로 전달
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findTodoList(){
        try{
            String tempUserId = "temp-user"; // User ID

            List<Todo> todos = service.retrieve(tempUserId);

            List<TodoDTO> todoDTOs = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoDTOs).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO){
        try {
            String tempUserId = "temp-user";

            Todo todo = todoDTO.toTodo(todoDTO);
            todo.setUserId(tempUserId);

            List<Todo> todos = service.update(todo);
            List<TodoDTO> todoDTOs = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoDTOs).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO){
        try {
            String tempUserId = "temp-user";
            Todo todo = todoDTO.toTodo(todoDTO);
            todo.setUserId(tempUserId);
            List<Todo> todos = service.delete(todo);
            List<TodoDTO> todoDTOs = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoDTOs).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
}

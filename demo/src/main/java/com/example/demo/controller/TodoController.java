package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    // TODO: POST,PUT,DELETE 용 DTO 만들기
    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String tempUserId = "temporary-user";   // TODO: 사용자 입력으로 바꿀 예정

            TodoEntity entity = TodoDTO.toEntity(todoDTO);

            entity.setId(null);

            entity.setUserId(tempUserId);

            List<TodoEntity> entities = service.create(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error  = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> retrieveTodoList() {
        try {
            String tempUserId = "temporary-user";   // TODO: 사용자 입력으로 바꿀 예정

            List<TodoEntity> entities = service.retrieve(tempUserId);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String tempUserId = "temporary-user";   // TODO: 사용자 입력으로 바꿀 예정

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(tempUserId);

        List<TodoEntity> entities = service.update(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String tempUserId = "temporary-user";   // TODO: 사용자 입력으로 바꿀 예정

            TodoEntity entity = TodoDTO.toEntity(todoDTO);
            entity.setUserId(tempUserId);

            List<TodoEntity> entities = service.delete(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }
}

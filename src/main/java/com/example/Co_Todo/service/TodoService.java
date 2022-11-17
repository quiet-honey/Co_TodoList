package com.example.Co_Todo.service;

import com.example.Co_Todo.model.Todo;
import com.example.Co_Todo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j // 다양한 로깅 프레임 워크에 대한 추상화(인터페이스) 역할을 하는 라이브러리
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public String testService() {
        // TodoEntity 생성 (Create)
        Todo todo = Todo.builder().title("My first todo item").build();
        // TodoEntity 저장 (Create)
        todoRepository.save(todo);

        // TodoEntity 검색 (Read)
        // TODO: Optional 값을 어떻게 처리해주는 것이 좋은 것인가?
        Optional<Todo> optionalTodoEntity = todoRepository.findById(todo.getId());
        if (optionalTodoEntity.isPresent()) {
            Todo savedEntity = optionalTodoEntity.get();
            return savedEntity.getTitle();
        }
        return "";
    }


    @Transactional
    public List<Todo> create(Todo todo) { // todo 검증 후 저장을 한 후에 로그 작성하고 저장한 todo를 반환
        validate(todo);
        todoRepository.save(todo);
        log.info("Todo Id : {} is saved", todo.getId());
        return retrieve(todo.getUserId());
    }

    public List<Todo> retrieve(String userId) { // 해당 uid로 작성된 todo들 반환
        return todoRepository.findByUserId(userId);
    }

    private void validate(Todo todo) { // 올바른 todo인지 검증
        if(todo == null) {
            log.warn("Entity can not be NULL.");
            throw new RuntimeException("Entity can not be NULL.");
        }
        if(todo.getUserId() == null) {
            log.warn("Unknown User.");
            throw new RuntimeException("Unknown User.");
        }
    }


    public List<Todo> update(Todo todo) {
        validate(todo);
        final Optional<Todo> optionalTodo = todoRepository.findById(todo.getId());
        optionalTodo.ifPresent((offeredTodo) -> {
            // spring JPA의 더티체킹 기능 덕에 todoRepository.save(entity)를 해주지 않아도 된다.
            offeredTodo.setTitle(todo.getTitle()); // todo 내용 수정
            offeredTodo.setDone(todo.isDone()); // todo 완료
        });
        return retrieve(todo.getUserId());
    }

    public List<Todo> delete(Todo todo) {
        validate(todo);
        try{
            todoRepository.deleteById(todo.getId());
        }catch (Exception e){
            log.error("error deleting entity {} not exists!", todo.getId());
            throw new RuntimeException("error deleting entity " + todo.getId());
        }
        return retrieve(todo.getUserId());
    }
}

package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public String testService() {
        // TodoEntity 생성 (Create)
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장 (Create)
        todoRepository.save(entity);

        // TodoEntity 검색 (Read)
        // TODO: Optional 값을 어떻게 처리해주는 것이 좋은 것인가?
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(entity.getId());
        if (optionalTodoEntity.isPresent()) {
            TodoEntity savedEntity = optionalTodoEntity.get();
            return savedEntity.getTitle();
        }
        return "";
    }

    private void validate(TodoEntity entity) {
        // Validations
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    /**
     * CREATE
     * request에서 보낸 데이터를 이용해 데이베이스에 저장
     */
    @Transactional
    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        todoRepository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return retrieve(entity.getUserId());
    }

    /**
     * READ
     * userId가 동일한 데이터 읽어오기
     */
    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    /**
     * UPDATE
     */
    @Transactional
    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        // persistence context에 의해 관리가 된다.
        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent((todo) -> {
            // spring jpa의 더티체킹 기능 덕에 todoRepository.save(entity)를 해주지 않아도 된다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
        });

        return retrieve(entity.getUserId());
    }

    /**
     * DELETE
     * target todo 삭제하기
     */
    @Transactional
    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            todoRepository.deleteById(entity.getId());
        } catch (Exception e) {
            log.error("error deleting entity {} not exists!", entity.getId());
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }
}

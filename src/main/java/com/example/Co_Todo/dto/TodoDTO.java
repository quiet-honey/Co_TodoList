package com.example.Co_Todo.dto;

import com.example.Co_Todo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(Todo todo) { // 주입되는 todo의 정보에 맞춰 TodoDTO를 생성한 후 전달
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }

    public static Todo toTodo(final TodoDTO todoDTO) { // 매개변수의 todoDTO를 todo로 변경
        return Todo.builder()
                .id(todoDTO.getId())
                .title(todoDTO.getTitle())
                .done(todoDTO.isDone())
                .build();
    }
}

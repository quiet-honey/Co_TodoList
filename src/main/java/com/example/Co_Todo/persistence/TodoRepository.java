package com.example.Co_Todo.persistence;

import com.example.Co_Todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByUserId(String userId); // 해당 uid를 가지는 todo들을 리스트 형태로 반환
}
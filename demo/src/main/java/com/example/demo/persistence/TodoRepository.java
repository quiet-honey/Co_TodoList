package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String userId);

    @Modifying
    @Query("update TodoEntity t set t.done = :doneState where t.id = :targetId and t.userId = :targetUserId")
    void updateDoneState(@Param("doneState") boolean doneState,
                         @Param("targetId") String targetId,
                         @Param("targetUserId") String targetUserId);

    @Modifying
    @Query("update TodoEntity t set t.title = :updateTitle where t.id = :targetId and t.userId = :targetUserId")
    void updateTitle(@Param("updateTitle") String updateTitle,
                     @Param("targetId") String targetId,
                     @Param("targetUserId") String targetUserId);

    @Modifying
    void deleteByIdAndUserId(@NonNull String id, String userId);
}

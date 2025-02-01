package com.david.mbaimbai.repository;

import com.david.mbaimbai.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    public List<Task> findTaskByAssignedUserId(Long userId);
}

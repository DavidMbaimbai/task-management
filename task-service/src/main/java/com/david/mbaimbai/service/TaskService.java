package com.david.mbaimbai.service;

import com.david.mbaimbai.entity.Task;
import com.david.mbaimbai.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requesterRole) throws Exception;
    Task findTaskById(Long id) throws Exception;
    List<Task> getAllATask(TaskStatus status);
    Task updateTask(Long id, Task updatedTask, Long userId) throws Exception;
    void deleteTask(Long id) throws Exception;
    Task assignToUser(Long userId, Long taskId)throws Exception;
    List<Task> assignedUserTask(Long userId, TaskStatus status);
    Task completeTask(Long taskId)throws Exception;
}

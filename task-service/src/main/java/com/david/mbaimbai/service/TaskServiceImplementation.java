package com.david.mbaimbai.service;

import com.david.mbaimbai.entity.Task;
import com.david.mbaimbai.enums.TaskStatus;
import com.david.mbaimbai.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (!requesterRole.equals("ROLE_ADMIN")) {
            throw new Exception("only admin can create a task");
        }
        task.setTaskStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task findTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("task not found with id " + id));
    }

    @Override
    public List<Task> getAllATask(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();
        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null ||
                        task.getTaskStatus().name()
                                .equals(status.toString())

        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = findTaskById(id);
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getTaskStatus() != null) {
            existingTask.setTaskStatus(updatedTask.getTaskStatus());
        }
        if (updatedTask.getDeadline() != null) {
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        findTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignToUser(Long userId, Long taskId) throws Exception {
        Task task = findTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setTaskStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUserTask(Long userId, TaskStatus status) {
        List<Task> allTask = taskRepository.findTaskByAssignedUserId(userId);
        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null ||
                        task.getTaskStatus().name()
                                .equals(status.toString())

        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = findTaskById(taskId);
        task.setTaskStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }
}

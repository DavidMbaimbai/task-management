package com.david.mbaimbai.service;

import com.david.mbaimbai.entity.Task;
import com.david.mbaimbai.enums.TaskStatus;
import com.david.mbaimbai.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplementationTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImplementation taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_AdminRole_Success() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task, "ROLE_ADMIN");

        assertNotNull(createdTask);
        assertEquals(TaskStatus.PENDING, createdTask.getTaskStatus());
        assertNotNull(createdTask.getCreatedAt());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void createTask_NonAdminRole_ThrowsException() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        Exception exception = assertThrows(Exception.class, () -> {
            taskService.createTask(task, "ROLE_USER");
        });

        assertEquals("only admin can create a task", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void findTaskById_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.findTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
        assertEquals("Test Task", foundTask.getTitle());
    }

    @Test
    void findTaskById_NonExistingTask_ThrowsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            taskService.findTaskById(1L);
        });

        assertEquals("task not found with id 1", exception.getMessage());
    }

    @Test
    void getAllATask_WithStatusFilter_Success() {
        Task task1 = new Task();
        task1.setTaskStatus(TaskStatus.PENDING);
        Task task2 = new Task();
        task2.setTaskStatus(TaskStatus.COMPLETED);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> filteredTasks = taskService.getAllATask(TaskStatus.PENDING);

        assertEquals(1, filteredTasks.size());
        assertEquals(TaskStatus.PENDING, filteredTasks.get(0).getTaskStatus());
    }

    @Test
    void updateTask_ExistingTask_Success() throws Exception {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Title");

        Task updatedTask = new Task();
        updatedTask.setTitle("New Title");
        updatedTask.setDescription("New Description");
        updatedTask.setTaskStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask, 1L);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getTaskStatus());
    }

    @Test
    void deleteTask_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void assignToUser_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.assignToUser(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getAssignedUserId());
        assertEquals(TaskStatus.IN_PROGRESS, result.getTaskStatus());
    }

    @Test
    void assignedUserTask_WithStatusFilter_Success() {
        Task task1 = new Task();
        task1.setAssignedUserId(1L);
        task1.setTaskStatus(TaskStatus.PENDING);
        Task task2 = new Task();
        task2.setAssignedUserId(1L);
        task2.setTaskStatus(TaskStatus.COMPLETED);

        when(taskRepository.findTaskByAssignedUserId(1L)).thenReturn(Arrays.asList(task1, task2));

        List<Task> filteredTasks = taskService.assignedUserTask(1L, TaskStatus.PENDING);

        assertEquals(1, filteredTasks.size());
        assertEquals(TaskStatus.PENDING, filteredTasks.get(0).getTaskStatus());
    }

    @Test
    void completeTask_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.completeTask(1L);

        assertNotNull(result);
        assertEquals(TaskStatus.COMPLETED, result.getTaskStatus());
    }
}
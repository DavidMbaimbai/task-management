package com.david.mbaimbai.controller;

import com.david.mbaimbai.dto.UserDto;
import com.david.mbaimbai.entity.Task;
import com.david.mbaimbai.enums.TaskStatus;
import com.david.mbaimbai.service.TaskService;
import com.david.mbaimbai.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_AdminRole_Success() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        UserDto user = new UserDto();
        user.setRole("ROLE_ADMIN");

        when(userService.getUserProfile(any(String.class))).thenReturn(user);
        when(taskService.createTask(any(Task.class), any(String.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.createTask(task, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void getTaskById_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(userService.getUserProfile(any(String.class))).thenReturn(new UserDto());
        when(taskService.findTaskById(1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(1L, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void getAssignUserTask_WithStatusFilter_Success() throws Exception {
        Task task1 = new Task();
        task1.setAssignedUserId(1L);
        task1.setTaskStatus(TaskStatus.PENDING);
        Task task2 = new Task();
        task2.setAssignedUserId(1L);
        task2.setTaskStatus(TaskStatus.COMPLETED);

        UserDto user = new UserDto();
        user.setId(1L);

        when(userService.getUserProfile(any(String.class))).thenReturn(user);
        when(taskService.assignedUserTask(1L, TaskStatus.PENDING)).thenReturn(List.of(task1));

        ResponseEntity<List<Task>> response = taskController.getAssignUserTask(TaskStatus.PENDING, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(TaskStatus.PENDING, response.getBody().get(0).getTaskStatus());
    }

    @Test
    void getTasks_WithStatusFilter_Success() throws Exception {
        Task task1 = new Task();
        task1.setTaskStatus(TaskStatus.PENDING);
        Task task2 = new Task();
        task2.setTaskStatus(TaskStatus.COMPLETED);

        when(userService.getUserProfile(any(String.class))).thenReturn(new UserDto());
        when(taskService.getAllATask(TaskStatus.PENDING)).thenReturn(List.of(task1));

        ResponseEntity<List<Task>> response = taskController.getTasks(TaskStatus.PENDING, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(TaskStatus.PENDING, response.getBody().get(0).getTaskStatus());
    }

    @Test
    void assignedTaskToUser_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(userService.getUserProfile(any(String.class))).thenReturn(new UserDto());
        when(taskService.assignToUser(1L, 1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.assignedTaskToUser(1L, 1L, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void updateTask_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        UserDto user = new UserDto();
        user.setId(1L);

        when(userService.getUserProfile(any(String.class))).thenReturn(user);
        when(taskService.updateTask(1L, task, 1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTask(1L, task, "Bearer token");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void completeTask_ExistingTask_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);

        when(taskService.completeTask(1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.completeTask(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void deleteTask_ExistingTask_Success() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
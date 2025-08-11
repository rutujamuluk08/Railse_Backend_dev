package com.workforcemgmt.controller;

import com.workforcemgmt.dto.*;
import com.workforcemgmt.mapper.TaskMapper;
import com.workforcemgmt.model.Task;
import com.workforcemgmt.model.TaskPriority;
import com.workforcemgmt.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }
    
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(taskMapper.tasksToTaskDtos(tasks));
    }
    
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<TaskDto>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Task> tasks = taskService.getTasksByDateRange(startDate, endDate);
        return ResponseEntity.ok(taskMapper.tasksToTaskDtos(tasks));
    }
    
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<TaskDto>> getTasksByStaff(@PathVariable String staffId) {
        List<Task> tasks = taskService.getTasksByStaff(staffId);
        return ResponseEntity.ok(taskMapper.tasksToTaskDtos(tasks));
    }
    
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable TaskPriority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(taskMapper.tasksToTaskDtos(tasks));
    }
    
    @PostMapping("/assign-by-ref")
    public ResponseEntity<TaskDto> assignTaskByReference(
            @RequestParam String customerReference,
            @RequestParam String newStaffId) {
        Task task = taskService.assignTaskByReference(customerReference, newStaffId);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @PutMapping("/{taskId}/priority")
    public ResponseEntity<TaskDto> updateTaskPriority(
            @PathVariable String taskId,
            @Valid @RequestBody UpdateTaskPriorityRequest request) {
        Task task = taskService.updateTaskPriority(taskId, request);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<TaskDto> addComment(
            @PathVariable String taskId,
            @Valid @RequestBody AddCommentRequest request) {
        Task task = taskService.addComment(taskId, request);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable String taskId) {
        Task task = taskService.completeTask(taskId);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
    
    @PutMapping("/{taskId}/cancel")
    public ResponseEntity<TaskDto> cancelTask(@PathVariable String taskId) {
        Task task = taskService.cancelTask(taskId);
        return ResponseEntity.ok(taskMapper.taskToTaskDto(task));
    }
}

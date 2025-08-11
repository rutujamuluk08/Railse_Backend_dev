package com.workforcemgmt.service;

import com.workforcemgmt.dto.AddCommentRequest;
import com.workforcemgmt.dto.CreateTaskRequest;
import com.workforcemgmt.dto.UpdateTaskPriorityRequest;
import com.workforcemgmt.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    
    private final Map<String, Task> tasks = new HashMap<>();
    private final Map<String, Staff> staff = new HashMap<>();
    
    public TaskService() {
        // Initialize with some sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Create sample staff
        Staff staff1 = new Staff("staff1", "John Doe", "john@example.com", "Sales");
        Staff staff2 = new Staff("staff2", "Jane Smith", "jane@example.com", "Operations");
        Staff staff3 = new Staff("staff3", "Bob Wilson", "bob@example.com", "Sales");
        
        staff.put("staff1", staff1);
        staff.put("staff2", staff2);
        staff.put("staff3", staff3);
        
        // Create sample tasks
        Task task1 = new Task("task1", "Customer Follow-up", "Follow up with customer ABC", 
                TaskStatus.ACTIVE, TaskPriority.HIGH, 
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(2), 
                staff1, "CUST001");
        task1.addActivity("Task created", "Manager");
        
        Task task2 = new Task("task2", "Inventory Check", "Check warehouse inventory", 
                TaskStatus.ACTIVE, TaskPriority.MEDIUM, 
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), 
                staff2, "INV001");
        task2.addActivity("Task created", "Manager");
        
        tasks.put("task1", task1);
        tasks.put("task2", task2);
    }
    
    public Task createTask(CreateTaskRequest request) {
        Staff assignedStaff = staff.get(request.getStaffId());
        if (assignedStaff == null) {
            throw new IllegalArgumentException("Staff not found with ID: " + request.getStaffId());
        }
        
        String taskId = "task" + (tasks.size() + 1);
        Task task = new Task(taskId, request.getTitle(), request.getDescription(), 
                TaskStatus.ACTIVE, request.getPriority(), 
                request.getStartDate(), request.getDueDate(), 
                assignedStaff, request.getCustomerReference());
        
        task.addActivity("Task created", "Manager");
        tasks.put(taskId, task);
        
        return task;
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    
    public List<Task> getTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return tasks.values().stream()
                .filter(task -> {
                    // Bug fix 2: Exclude CANCELLED tasks
                    if (task.getStatus() == TaskStatus.CANCELLED) {
                        return false;
                    }
                    
                    // Feature 1: Smart daily task view
                    // Return tasks that started within the range OR
                    // tasks that started before the range but are still active and not completed
                    boolean startedInRange = !task.getStartDate().isBefore(startDate) && !task.getStartDate().isAfter(endDate);
                    boolean startedBeforeButStillActive = task.getStartDate().isBefore(startDate) && 
                            task.getStatus() == TaskStatus.ACTIVE;
                    
                    return startedInRange || startedBeforeButStillActive;
                })
                .collect(Collectors.toList());
    }
    
    public List<Task> getTasksByStaff(String staffId) {
        return tasks.values().stream()
                .filter(task -> task.getAssignedTo().getId().equals(staffId))
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Bug fix 2: Exclude cancelled tasks
                .collect(Collectors.toList());
    }
    
    public List<Task> getTasksByPriority(TaskPriority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
                .collect(Collectors.toList());
    }
    
    public Task getTaskById(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found with ID: " + taskId);
        }
        return task;
    }
    
    public Task assignTaskByReference(String customerReference, String newStaffId) {
        Staff newStaff = staff.get(newStaffId);
        if (newStaff == null) {
            throw new IllegalArgumentException("Staff not found with ID: " + newStaffId);
        }
        
        // Bug fix 1: Cancel existing tasks for this customer reference
        tasks.values().stream()
                .filter(task -> customerReference.equals(task.getCustomerReference()))
                .filter(task -> task.getStatus() == TaskStatus.ACTIVE)
                .forEach(task -> {
                    task.setStatus(TaskStatus.CANCELLED);
                    task.addActivity("Task cancelled due to reassignment", "System");
                });
        
        // Find the most recent task for this customer reference to get its details
        Task existingTask = tasks.values().stream()
                .filter(task -> customerReference.equals(task.getCustomerReference()))
                .max(Comparator.comparing(Task::getCreatedAt))
                .orElseThrow(() -> new IllegalArgumentException("No task found for customer reference: " + customerReference));
        
        // Create new task with same details but assigned to new staff
        String newTaskId = "task" + (tasks.size() + 1);
        Task newTask = new Task(newTaskId, existingTask.getTitle(), existingTask.getDescription(),
                TaskStatus.ACTIVE, existingTask.getPriority(),
                existingTask.getStartDate(), existingTask.getDueDate(),
                newStaff, customerReference);
        
        newTask.addActivity("Task reassigned from " + existingTask.getAssignedTo().getName() + " to " + newStaff.getName(), "Manager");
        tasks.put(newTaskId, newTask);
        
        return newTask;
    }
    
    public Task updateTaskPriority(String taskId, UpdateTaskPriorityRequest request) {
        Task task = getTaskById(taskId);
        TaskPriority oldPriority = task.getPriority();
        task.setPriority(request.getPriority());
        task.addActivity("Priority changed from " + oldPriority + " to " + request.getPriority(), "Manager");
        return task;
    }
    
    public Task addComment(String taskId, AddCommentRequest request) {
        Task task = getTaskById(taskId);
        task.addComment(request.getContent(), request.getAuthor());
        return task;
    }
    
    public Task completeTask(String taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.COMPLETED);
        task.addActivity("Task marked as completed", task.getAssignedTo().getName());
        return task;
    }
    
    public Task cancelTask(String taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.CANCELLED);
        task.addActivity("Task cancelled", task.getAssignedTo().getName());
        return task;
    }
    
    public List<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }
    
    public Staff getStaffById(String staffId) {
        Staff staffMember = staff.get(staffId);
        if (staffMember == null) {
            throw new IllegalArgumentException("Staff not found with ID: " + staffId);
        }
        return staffMember;
    }
}

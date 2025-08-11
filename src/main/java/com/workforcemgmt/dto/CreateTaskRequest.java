package com.workforcemgmt.dto;

import com.workforcemgmt.model.TaskPriority;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Priority is required")
    private TaskPriority priority;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
    
    @NotBlank(message = "Staff ID is required")
    private String staffId;
    
    private String customerReference;
}

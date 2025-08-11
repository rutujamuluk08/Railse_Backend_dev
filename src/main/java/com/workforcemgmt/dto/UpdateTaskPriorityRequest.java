package com.workforcemgmt.dto;

import com.workforcemgmt.model.TaskPriority;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UpdateTaskPriorityRequest {
    @NotNull(message = "Priority is required")
    private TaskPriority priority;
}

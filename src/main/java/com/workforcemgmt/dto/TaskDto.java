package com.workforcemgmt.dto;

import com.workforcemgmt.model.TaskActivity;
import com.workforcemgmt.model.TaskComment;
import com.workforcemgmt.model.TaskPriority;
import com.workforcemgmt.model.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private StaffDto assignedTo;
    private String customerReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskActivity> activityHistory;
    private List<TaskComment> comments;
}

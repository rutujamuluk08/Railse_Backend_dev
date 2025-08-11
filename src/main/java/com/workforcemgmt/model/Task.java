package com.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Staff assignedTo;
    private String customerReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskActivity> activityHistory;
    private List<TaskComment> comments;

    public Task(String id, String title, String description, TaskStatus status, TaskPriority priority, 
                LocalDateTime startDate, LocalDateTime dueDate, Staff assignedTo, String customerReference) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.customerReference = customerReference;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.activityHistory = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void addActivity(String action, String performedBy) {
        TaskActivity activity = new TaskActivity(action, performedBy, LocalDateTime.now());
        this.activityHistory.add(activity);
        this.updatedAt = LocalDateTime.now();
    }

    public void addComment(String content, String author) {
        TaskComment comment = new TaskComment(content, author, LocalDateTime.now());
        this.comments.add(comment);
        this.updatedAt = LocalDateTime.now();
    }
}

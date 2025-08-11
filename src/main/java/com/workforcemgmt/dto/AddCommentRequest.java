package com.workforcemgmt.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class AddCommentRequest {
    @NotBlank(message = "Comment content is required")
    private String content;
    
    @NotBlank(message = "Author is required")
    private String author;
}

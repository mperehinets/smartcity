package com.smartcity.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;


public class Comment {
    private Long id;
    private String description;
    private Long userId;
    private Long taskId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public Comment() {
    }

    public Comment(Long id, String description, LocalDateTime createdDate, LocalDateTime updatedDate, Long userId, Long taskId) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userId = userId;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(description, comment.description) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(taskId, comment.taskId) &&
                Objects.equals(createdDate, comment.createdDate) &&
                Objects.equals(updatedDate, comment.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, userId, taskId, createdDate, updatedDate);
    }
}



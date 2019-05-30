package com.smartcity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDto {

    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a description")
    private String description;

    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long userId;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long taskId;

    public CommentDto() {
    }

    public CommentDto(Long id, String description, LocalDateTime createdDate, LocalDateTime updatedDate, Long userId, Long taskId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto commentDto = (CommentDto) o;
        return Objects.equals(id, commentDto.id) &&
                Objects.equals(description, commentDto.description) &&
                Objects.equals(taskId, commentDto.taskId) &&
                Objects.equals(createdDate, commentDto.createdDate) &&
                Objects.equals(updatedDate, commentDto.updatedDate) &&
                Objects.equals(userId, commentDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdDate, updatedDate, userId, taskId);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"description\":\"" + description + '\"' +
                ",\"createdDate\":\"" + createdDate + '\"' +
                ",\"updatedDate\":\"" + updatedDate + '\"' +
                ",\"userId\":" + userId + '\"' +
                ",\"taskId\":\"" + taskId + '\"' +
                '}';
    }
}

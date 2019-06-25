package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.constraint.LessThan;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskDto {

    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a title")
    private String title;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a description")
    private String description;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide an accomplishment date")
    @Future(groups = {NewRecord.class, ExistingRecord.class},
            message = "Accomplishment date should be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime deadlineDate;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private String taskStatus;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, add a budget")
    @Min(groups = {NewRecord.class, ExistingRecord.class},
            value = 0, message = "Budget must be greater than 0")
    private Long budget;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, add approved budget")
    private Long approvedBudget;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long usersOrganizationsId;

    public TaskDto(Long id, String title, String description, LocalDateTime deadlineDate, String taskStatus,
                   Long budget, Long approvedBudget, LocalDateTime createdAt,
                   LocalDateTime updatedAt, Long usersOrganizationsId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.taskStatus = taskStatus;
        this.budget = budget;
        this.approvedBudget = approvedBudget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usersOrganizationsId = usersOrganizationsId;
    }

    public TaskDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getApprovedBudget() {
        return approvedBudget;
    }

    public void setApprovedBudget(Long approvedBudget) {
        this.approvedBudget = approvedBudget;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUsersOrganizationsId() {
        return usersOrganizationsId;
    }

    public void setUsersOrganizationsId(Long usersOrganizationsId) {
        this.usersOrganizationsId = usersOrganizationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) &&
                Objects.equals(title, taskDto.title) &&
                Objects.equals(description, taskDto.description) &&
                Objects.equals(deadlineDate, taskDto.deadlineDate) &&
                Objects.equals(taskStatus, taskDto.taskStatus) &&
                Objects.equals(budget, taskDto.budget) &&
                Objects.equals(approvedBudget, taskDto.approvedBudget) &&
                Objects.equals(createdAt, taskDto.createdAt) &&
                Objects.equals(updatedAt, taskDto.updatedAt) &&
                Objects.equals(usersOrganizationsId, taskDto.usersOrganizationsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadlineDate, taskStatus, budget,
                approvedBudget, createdAt, updatedAt, usersOrganizationsId);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"title\":\"" + title + '\"' +
                ",\"description\":\"" + description + '\"' +
                ",\"deadlineDate\":\"" + deadlineDate + '\"' +
                ",\"taskStatus\":\"" + taskStatus + '\"' +
                ",\"budget\":" + budget +
                ",\"approvedBudget\":" + approvedBudget +
                ",\"createdAt\":\"" + createdAt + '\"' +
                ",\"updatedAt\":\"" + updatedAt + '\"' +
                ",\"usersOrganizationsId\":" + usersOrganizationsId +
                '}';
    }
}

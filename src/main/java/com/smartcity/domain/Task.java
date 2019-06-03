package com.smartcity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private Long id;
    private String title;
    private String description;
    private String taskStatus;
    private Long budget;
    private Long approvedBudget;
    private Long usersOrganizationsId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime deadlineDate;

    public Task(Long id, String title, String description, LocalDateTime deadlineDate, String taskStatus,
                Long budget, Long approvedBudget, LocalDateTime createdAt, LocalDateTime updatedAt,
                Long usersOrganizationsId) {
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

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadlineDate, task.deadlineDate) &&
                Objects.equals(taskStatus, task.taskStatus) &&
                Objects.equals(budget, task.budget) &&
                Objects.equals(approvedBudget, task.approvedBudget) &&
                Objects.equals(createdAt, task.createdAt) &&
                Objects.equals(updatedAt, task.updatedAt) &&
                Objects.equals(usersOrganizationsId, task.usersOrganizationsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadlineDate, taskStatus, budget, approvedBudget,
                createdAt, updatedAt, usersOrganizationsId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadlineDate=" + deadlineDate +
                ", taskStatus='" + taskStatus + '\'' +
                ", budget=" + budget +
                ", approved_budget=" + approvedBudget +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", usersOrganizationsId=" + usersOrganizationsId +
                '}';
    }
}

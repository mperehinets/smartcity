package com.smartcity.dto;

public class CommentNotificationDto {

    private String description;
    private String user;
    private String task;
    private Long id;
    private Long userId;
    private Long organizationId;

    public CommentNotificationDto(Long id, String description, String user, String task, Long userId, Long organizationId) {
        this.description = description;
        this.user = user;
        this.task = task;
        this.id = id;
        this.userId = userId;
        this.organizationId = organizationId;
    }

    public CommentNotificationDto() {

    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

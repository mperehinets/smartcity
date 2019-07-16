package com.smartcity.dto;

public class TaskNotificationDto {

    private String title;
    private String orgName;
    private Long budget;

    public TaskNotificationDto(String title, String orgName, Long budget) {
        this.title = title;
        this.orgName = orgName;
        this.budget = budget;
    }

    public TaskNotificationDto() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }
}

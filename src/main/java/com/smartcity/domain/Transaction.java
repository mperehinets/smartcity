package com.smartcity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private Long id;
    private Long taskId;
    private Long currentBudget;
    private Long transactionBudget;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public Transaction() {
    }

    public Transaction(Long id, Long taskId, Long currentBudget,
                       Long transactionBudget, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.taskId = taskId;
        this.currentBudget = currentBudget;
        this.transactionBudget = transactionBudget;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(Long currentBudget) {
        this.currentBudget = currentBudget;
    }

    public Long getTransactionBudget() {
        return transactionBudget;
    }

    public void setTransactionBudget(Long transactionBudget) {
        this.transactionBudget = transactionBudget;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                "taskId=" + taskId +
                "currentBudget=" + currentBudget +
                "transactionBudget=" + transactionBudget +
                "createdDate=" + createdDate +
                "updatedDate=" + updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(currentBudget, that.currentBudget) &&
                Objects.equals(transactionBudget, that.transactionBudget) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, currentBudget, transactionBudget, createdDate, updatedDate);
    }
}
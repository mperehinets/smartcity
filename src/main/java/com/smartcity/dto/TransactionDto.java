package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDto {

    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long taskId;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long currentBudget;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class},
            message = "This field can't be empty")
    private Long transactionBudget;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long taskId, Long currentBudget,
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
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

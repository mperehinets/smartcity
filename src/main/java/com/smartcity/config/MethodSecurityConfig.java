package com.smartcity.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration("securityConfiguration")
@PropertySource(value = "classpath:methodSecurity.properties")
public class MethodSecurityConfig {

    // UserController
    @Value("#{'${userController.deleteUser}'.split(',')}")
    private List<String> userControllerDeleteUserAllowedRoles;

    @Value("#{'${userController.activateUser}'.split(',')}")
    private List<String> userControllerActivateUserAllowedRoles;

    @Value("#{'${userController.setRolesByUserId}'.split(',')}")
    private List<String> userControllerSetRolesByUserIdAllowedRoles;

    // BudgetController
    @Value("#{'${budgetController.put}'.split(',')}")
    private List<String> budgetControllerPutAllowedRoles;

    // OrganizationController
    @Value("#{'${organizationController.create}'.split(',')}")
    private List<String> organizationControllerCreateAllowedRoles;

    @Value("#{'${organizationController.update}'.split(',')}")
    private List<String> organizationControllerUpdateAllowedRoles;

    @Value("#{'${organizationController.delete}'.split(',')}")
    private List<String> organizationControllerDeleteAllowedRoles;

    @Value("#{'${organizationController.addUserToOrganization}'.split(',')}")
    private List<String> organizationControllerAddUserToOrganizationAllowedRoles;

    @Value("#{'${organizationController.removeUserFromOrganization}'.split(',')}")
    private List<String> organizationControllerRemoveUserFromOrganizationAllowedRoles;

    // RoleController
    @Value("#{'${roleController.createRole}'.split(',')}")
    private List<String> roleControllerCreateRoleAllowedRoles;

    // TaskController
    @Value("#{'${taskController.createTask}'.split(',')}")
    private List<String> taskControllerCreateTaskAllowedRoles;

    @Value("#{'${taskController.updateTask}'.split(',')}")
    private List<String> taskControllerUpdateTaskAllowedRoles;

    @Value("#{'${taskController.deleteTask}'.split(',')}")
    private List<String> taskControllerDeleteTaskAllowedRoles;

    @Value("#{'${taskController.findByOrganizationId}'.split(',')}")
    private List<String> taskControllerFindByOrganizationIdAllowedRoles;

    public List<String> getTaskControllerFindByIdAllowedRoles() {
        return taskControllerFindByIdAllowedRoles;
    }

    public void setTaskControllerFindByIdAllowedRoles(List<String> taskControllerFindByIdAllowedRoles) {
        this.taskControllerFindByIdAllowedRoles = taskControllerFindByIdAllowedRoles;
    }

    @Value("#{'${taskController.findById}'.split(',')}")
    private List<String> taskControllerFindByIdAllowedRoles;


    public List<String> getTaskControllerFindByOrganizationIdAllowedRoles() {
        return taskControllerFindByOrganizationIdAllowedRoles;
    }

    public void setTaskControllerFindByOrganizationIdAllowedRoles(List<String> taskControllerFindByOrganizationIdAllowedRoles) {
        this.taskControllerFindByOrganizationIdAllowedRoles = taskControllerFindByOrganizationIdAllowedRoles;
    }

    // TransactionController
    @Value("#{'${transactionController.findById}'.split(',')}")
    private List<String> transactionControllerFindByIdAllowedRoles;

    @Value("#{'${transactionController.updateTransaction}'.split(',')}")
    private List<String> transactionControllerUpdateTransactionAllowedRoles;

    @Value("#{'${transactionController.deleteTransaction}'.split(',')}")
    private List<String> transactionControllerDeleteTransactionAllowedRoles;

    @Value("#{'${transactionController.findByTaskId}'.split(',')}")
    private List<String> transactionControllerFindByTaskIdAllowedRoles;

    @Value("#{'${transactionController.createTransaction}'.split(',')}")
    private List<String> transactionControllerCreateTransactionAllowedRoles;

    //CommentController
    @Value("#{'${commentController.updateComment}'.split(',')}")
    private List<String> commentControllerUpdateCommentAllowedRoles;

    @Value("#{'${commentController.findByTaskId}'.split(',')}")
    private List<String> commentControllerFindByTaskIdAllowedRoles;

    public List<String> getCommentControllerFindByTaskIdAllowedRoles() {
        return commentControllerFindByTaskIdAllowedRoles;
    }

    public void setCommentControllerFindByTaskIdAllowedRoles(List<String> commentControllerFindByTaskIdAllowedRoles) {
        this.commentControllerFindByTaskIdAllowedRoles = commentControllerFindByTaskIdAllowedRoles;
    }

    @Value("#{'${commentController.deleteComment}'.split(',')}")
    private List<String> commentControlerDeleteCommentAllowedRoles;

    public List<String> getCommentControllerUpdateCommentAllowedRoles() {
        return commentControllerUpdateCommentAllowedRoles;
    }

    public List<String> getCommentControlerDeleteCommentAllowedRoles() {
        return commentControlerDeleteCommentAllowedRoles;
    }

    public List<String> getUserControllerDeleteUserAllowedRoles() {
        return userControllerDeleteUserAllowedRoles;
    }

    public List<String> getUserControllerActivateUserAllowedRoles() {
        return userControllerActivateUserAllowedRoles;
    }

    public List<String> getUserControllerSetRolesByUserIdAllowedRoles() {
        return userControllerSetRolesByUserIdAllowedRoles;
    }

    public List<String> getBudgetControllerPutAllowedRoles() {
        return budgetControllerPutAllowedRoles;
    }

    public List<String> getOrganizationControllerCreateAllowedRoles() {
        return organizationControllerCreateAllowedRoles;
    }

    public List<String> getOrganizationControllerUpdateAllowedRoles() {
        return organizationControllerUpdateAllowedRoles;
    }

    public List<String> getOrganizationControllerDeleteAllowedRoles() {
        return organizationControllerDeleteAllowedRoles;
    }

    public List<String> getOrganizationControllerAddUserToOrganizationAllowedRoles() {
        return organizationControllerAddUserToOrganizationAllowedRoles;
    }

    public List<String> getOrganizationControllerRemoveUserFromOrganizationAllowedRoles() {
        return organizationControllerRemoveUserFromOrganizationAllowedRoles;
    }

    public List<String> getRoleControllerCreateRoleAllowedRoles() {
        return roleControllerCreateRoleAllowedRoles;
    }

    public List<String> getTaskControllerCreateTaskAllowedRoles() {
        return taskControllerCreateTaskAllowedRoles;
    }

    public List<String> getTaskControllerUpdateTaskAllowedRoles() {
        return taskControllerUpdateTaskAllowedRoles;
    }

    public List<String> getTaskControllerDeleteTaskAllowedRoles() {
        return taskControllerDeleteTaskAllowedRoles;
    }

    public List<String> getTransactionControllerFindByIdAllowedRoles() {
        return transactionControllerFindByIdAllowedRoles;
    }

    public List<String> getTransactionControllerUpdateTransactionAllowedRoles() {
        return transactionControllerUpdateTransactionAllowedRoles;
    }

    public List<String> getTransactionControllerDeleteTransactionAllowedRoles() {
        return transactionControllerDeleteTransactionAllowedRoles;
    }

    public List<String> getTransactionControllerFindByTaskIdAllowedRoles() {
        return transactionControllerFindByTaskIdAllowedRoles;
    }

    public List<String> getTransactionControllerCreateTransactionAllowedRoles() {
        return transactionControllerCreateTransactionAllowedRoles;
    }
}

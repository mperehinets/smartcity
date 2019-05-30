package com.smartcity.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration("securityConfiguration")
@PropertySource(value = "classpath:methodSecurity.properties")
public class MethodSecurityConfig {

    // UserController
    @Value("#{'${userController.setRolesByUserId}'.split(',')}")
    private List<String> userControllerSetRolesByUserIdRoles;

    @Value("#{'${userController.deleteUser}'.split(',')}")
    private List<String> userControllerDeleteUserRoles;

    // BudgetController
    @Value("#{'${budgetController.put}'.split(',')}")
    private List<String> budgetControllerPut;

    // OrganizationController
    @Value("#{'${organizationController.create}'.split(',')}")
    private List<String> organizationControllerCreate;

    @Value("#{'${organizationController.update}'.split(',')}")
    private List<String> organizationControllerUpdate;

    @Value("#{'${organizationController.delete}'.split(',')}")
    private List<String> organizationControllerDelete;

    @Value("#{'${organizationController.addUserToOrganization}'.split(',')}")
    private List<String> organizationControllerAddUserToOrganization;

    @Value("#{'${organizationController.removeUserFromOrganization}'.split(',')}")
    private List<String> organizationControllerRemoveUserFromOrganization;

    // RoleController
    @Value("#{'${roleController.createRole}'.split(',')}")
    private List<String> roleControllerCreateRole;

    // TaskController
    @Value("#{'${taskController.createTask}'.split(',')}")
    private List<String> taskControllerCreateTask;

    @Value("#{'${taskController.createTask}'.split(',')}")
    private List<String> taskControllerUpdateTask;

    @Value("#{'${taskController.createTask}'.split(',')}")
    private List<String> taskControllerDeleteTask;


    // TransactionController
    @Value("#{'${transactionController.findById}'.split(',')}")
    private List<String> transactionControllerFindById;

    @Value("#{'${transactionController.updateTransaction}'.split(',')}")
    private List<String> transactionControllerUpdateTransaction;

    @Value("#{'${transactionController.deleteTransaction}'.split(',')}")
    private List<String> transactionControllerDeleteTransaction;

    @Value("#{'${transactionController.findByTaskId}'.split(',')}")
    private List<String> transactionControllerFindByTaskId;

    @Value("#{'${transactionController.createTransaction}'.split(',')}")
    private List<String> transactionControllerCreateTransaction;


    public List<String> getUserControllerSetRolesByUserIdRoles() {
        return userControllerSetRolesByUserIdRoles;
    }

    public List<String> getUserControllerDeleteUserRoles() {
        return userControllerDeleteUserRoles;
    }

    public List<String> getBudgetControllerPut() {
        return budgetControllerPut;
    }

    public List<String> getOrganizationControllerCreate() {
        return organizationControllerCreate;
    }

    public List<String> getOrganizationControllerUpdate() {
        return organizationControllerUpdate;
    }

    public List<String> getOrganizationControllerDelete() {
        return organizationControllerDelete;
    }

    public List<String> getOrganizationControllerAddUserToOrganization() {
        return organizationControllerAddUserToOrganization;
    }

    public List<String> getOrganizationControllerRemoveUserFromOrganization() {
        return organizationControllerRemoveUserFromOrganization;
    }

    public List<String> getRoleControllerCreateRole() {
        return roleControllerCreateRole;
    }

    public List<String> getTaskControllerCreateTask() {
        return taskControllerCreateTask;
    }

    public List<String> getTaskControllerUpdateTask() {
        return taskControllerUpdateTask;
    }

    public List<String> getTaskControllerDeleteTask() {
        return taskControllerDeleteTask;
    }

    public List<String> getTransactionControllerFindById() {
        return transactionControllerFindById;
    }

    public List<String> getTransactionControllerUpdateTransaction() {
        return transactionControllerUpdateTransaction;
    }

    public List<String> getTransactionControllerDeleteTransaction() {
        return transactionControllerDeleteTransaction;
    }

    public List<String> getTransactionControllerFindByTaskId() {
        return transactionControllerFindByTaskId;
    }

    public List<String> getTransactionControllerCreateTransaction() {
        return transactionControllerCreateTransaction;
    }
}

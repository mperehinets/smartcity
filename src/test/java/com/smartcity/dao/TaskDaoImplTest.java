package com.smartcity.dao;

import com.smartcity.domain.Task;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TaskDaoImplTest extends BaseTest {

    private Task task = new Task(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    @Autowired
    private TaskDao taskDao;

//    @BeforeAll
//    public static void setUpDataSource() {
//        setup();
//        taskDao = new TaskDaoImpl(dataSource);
//    }

    @Test
    public void testCreateTask() {
        assertEquals(task, taskDao.create(task));
    }

    @Test
    public void testCreateTask_InvalidUsersOrganizationsId() {
        task.setUsersOrganizationsId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    public void testCreateTask_MissedUsersOrganizationsId() {
        task.setUsersOrganizationsId(null);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    public void testFindTaskById() {
        taskDao.create(task);
        Task resultTask = taskDao.findById(task.getId());
        assertThat(task).isEqualToIgnoringGivenFields(resultTask,
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    public void testFindTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.findById(Long.MAX_VALUE));
    }

    @Test
    public void testFindTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.findById(null));
    }

    @Test
    public void testFindTaskByOrganizationId() {
        taskDao.create(task);
        List<Task> resultTaskList = taskDao.findByOrganizationId(1L);
        List<Task> expTaskList = new ArrayList<>();
        expTaskList.add(taskDao.findById(1L));
        expTaskList.add(task);
        int i = 0;
        for (Task t1 : resultTaskList) {
            assertThat(t1).isEqualToIgnoringGivenFields(expTaskList.get(i), "deadlineDate",
                    "createdAt", "updatedAt");
            i++;
        }
    }

    @Test
    public void testFindTaskByUserId() {
        taskDao.create(task);
        List<Task> resultTaskList = taskDao.findByUserId(1L);
        List<Task> exTaskList = new ArrayList<>(taskDao.findAll());
        int i = 0;
        for (Task t1 : resultTaskList) {
            assertThat(t1).isEqualToIgnoringGivenFields(exTaskList.get(i), "deadlineDate",
                    "createdAt", "updatedAt");
            i++;
        }
    }

    @Test
    public void testUpdateTask() {
        taskDao.create(task);

        Task updatedTask = new Task(2L, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);

        taskDao.update(updatedTask);

        Task resultTask = taskDao.findById(updatedTask.getId());

        assertThat(updatedTask).isEqualToIgnoringGivenFields(resultTask,
                "deadlineDate", "createdAt",
                "updatedAt", "transactionList");
    }

    @Test
    public void testUpdateTask_InvalidId() {
        Task updatedTask = new Task(Long.MAX_VALUE, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }

    @Test
    public void testUpdateTask_NullId() {
        Task updatedTask = new Task(null, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }


    @Test
    public void testDeleteTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(Long.MAX_VALUE));
    }


    @Test
    public void testDeleteTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(null));
    }

    @Test
    public void testDeleteTask() {
        taskDao.create(task);
        assertTrue(taskDao.delete(task.getId()));
    }

    @AfterEach
    public void close() {
        clearTables("Transactions");
    }

}

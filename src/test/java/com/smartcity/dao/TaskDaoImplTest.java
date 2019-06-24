package com.smartcity.dao;

import com.smartcity.domain.Task;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskDaoImplTest extends BaseTest {

    private Task task = new Task(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L,
            LocalDateTime.now(), LocalDateTime.now(),
            1L);

    @Autowired
    private TaskDao taskDao;

    @Test
    void testCreateTask() {
        assertEquals(task, taskDao.create(task));
    }

    @Test
    void testCreateTask_InvalidUsersOrganizationsId() {
        task.setUsersOrganizationsId(Long.MAX_VALUE);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    void testCreateTask_MissedUsersOrganizationsId() {
        task.setUsersOrganizationsId(null);
        assertThrows(DbOperationException.class, () -> taskDao.create(task));
    }

    @Test
    void testFindTaskById() {
        taskDao.create(task);
        Task resultTask = taskDao.findById(task.getId());
        assertThat(task).isEqualToIgnoringGivenFields(resultTask,
                "transactionList", "deadlineDate",
                "createdAt", "updatedAt");
    }

    @Test
    void testFindTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.findById(Long.MAX_VALUE));
    }

    @Test
    void testFindTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.findById(null));
    }

    @Test
    void testFindTaskByOrganizationId() {
        taskDao.create(task);
        List<Task> resultTaskList = taskDao.findByOrganizationId(2L);
        List<Task> expTaskList = new ArrayList<>();
        expTaskList.add(taskDao.findById(2L));
        expTaskList.add(task);
        int i = 0;
        for (Task t1 : resultTaskList) {
            assertThat(t1).isEqualToIgnoringGivenFields(expTaskList.get(i), "deadlineDate",
                    "createdAt", "updatedAt");
            i++;
        }
    }

    @Test
    void testFindTaskByUserId() {
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
    void testUpdateTask() {
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
    void testUpdateTask_InvalidId() {
        Task updatedTask = new Task(Long.MAX_VALUE, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }

    @Test
    void testUpdateTask_NullId() {
        Task updatedTask = new Task(null, "Santasss", "Task for Santasss",
                LocalDateTime.now(), "TODOs",
                1000L, 1000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        assertThrows(NotFoundException.class, () -> taskDao.update(updatedTask));
    }


    @Test
    void testDeleteTask_InvalidId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(Long.MAX_VALUE));
    }


    @Test
    void testDeleteTask_NullId() {
        assertThrows(NotFoundException.class, () -> taskDao.delete(null));
    }

    @Test
    void testDeleteTask() {
        taskDao.create(task);
        assertTrue(taskDao.delete(task.getId()));
    }

    @Test
    void testFindUsersOrgsId(){
        assertEquals(taskDao.findUsersOrgIdByUserIdAndOrgId(1L,1L), 1L);
    }

    @Test
    void testFindUsersOrgsId_InvalidId(){
        assertThrows(NotFoundException.class, () -> taskDao.findUsersOrgIdByUserIdAndOrgId(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    @Test
    void testFindTaskByDate(){
        clearTables("Tasks");
        LocalDateTime date = LocalDateTime.now().minusMonths(1L);
        Task task = new Task(3L, "NewTask", "Task for test",
                LocalDateTime.now(), "TODO",
                2000L, 2000L,
                LocalDateTime.now(), LocalDateTime.now(),
                1L);
        taskDao.create(task);
        taskDao.create(this.task);
        List<Task> tasks = asList(task,this.task);
        List<Task> taskFromDb = taskDao.findByDate(this.task.getUsersOrganizationsId(),date,LocalDateTime.now());
        IntStream.range(0,taskFromDb.size())
                .mapToObj(
                i -> new Pair<>(tasks.get(i),taskFromDb.get(i)))
                .forEach(t->assertTaskEquals(t.getKey(),t.getValue()));

    }

    private void assertTaskEquals(Task t1, Task t2){
        assertThat(t1).isEqualToIgnoringGivenFields(t2,"deadlineDate","createdAt","updatedAt");
    }

    @AfterEach
    void close() {
        clearTables("Transactions");
    }

}

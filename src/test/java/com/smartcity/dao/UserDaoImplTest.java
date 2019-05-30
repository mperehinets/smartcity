package com.smartcity.dao;

import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

 class UserDaoImplTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void beforeEachSetUp() {
        // Initializing test user
        user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        user.setActive(true);

        // Creating user
        userDao.create(user);
    }

    @Test
    void testCreate_successFlow() {
        // Instantiating new user object
        User newUser = new User();
        newUser.setEmail("someAnotherEmail@gmail.com");
        newUser.setPassword("12345");
        newUser.setSurname("Johnson");
        newUser.setName("John");
        newUser.setPhoneNumber("0626552521415");

        // Should return reference to that same object
        assertEquals(newUser, userDao.create(newUser));
        assertThat(newUser.getId()).isNotNull();
    }

    @Test
    void testCreate_duplicateUsername() {
        // A user with this name already exists
        // Should throw DbOperationException
        assertThrows(DbOperationException.class, () -> userDao.create(user));
    }


    @Test
    void testCreate_omittedNotNullFields() {
        // Creating empty user item
        User emptyUserItem = new User();
        assertThrows(DbOperationException.class, () -> userDao.create(emptyUserItem));
    }

    @Test
    void testFind_successFlow() {
        // Getting user
        User resultUser = userDao.findById(user.getId());

        // Encrypting user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    void testFindUser_invalidId() {
        assertThrows(NotFoundException.class, () -> userDao.findById(Long.MAX_VALUE));
    }

    @Test
    void findAll_successFlow() {
        // Clearing table
        clearTables("Users");

        // Initializing users list
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setEmail("some@email.com");
        user1.setPassword("qwerty");
        user1.setSurname("Test");
        user1.setName("User");
        user1.setPhoneNumber("06558818");
        user1.setActive(true);
        User user2 = new User();
        user2.setEmail("another@email.com");
        user2.setPassword("trewq");
        user2.setSurname("tset");
        user2.setName("Resu");
        user2.setPhoneNumber("05811451");
        user2.setActive(false);

        users.add(user1);
        users.add(user2);
        // Adding more users to database
        userDao.create(user1);
        userDao.create(user2);

        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        List<User> resultUserList = userDao.findAll();
        for (int i = 0; i < users.size(); i++) {
            assertThat(users.get(i)).isEqualToIgnoringGivenFields(resultUserList.get(i),
                    "id", "createdDate", "updatedDate");
        }

    }

    @Test
    void findAll_EmptyUsersTable() {
        clearTables("Users");
        List<User> resultUserList = userDao.findAll();

        assertTrue(() -> resultUserList.size() == 0);
    }

    @Test
    void testFindByEmail_successFlow() {
        User resultUser = userDao.findByEmail(user.getEmail());

        // Encrypting user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    void testFindByEmail_incorrectEmail() {
        assertThrows(NotFoundException.class, () -> userDao.findByEmail("not_existing_email@gmail.com"));
    }

    @Test
    void testUpdate_successFlow() {
        // Creating updated user
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setEmail("updated@gmail.com");
        updatedUser.setSurname("Smith");
        updatedUser.setName("Den");
        updatedUser.setPhoneNumber("0333333333");
        updatedUser.setCreatedDate(user.getCreatedDate());
        userDao.update(updatedUser);
        User resultUser = userDao.findById(user.getId());
        assertThat(updatedUser).isEqualToIgnoringGivenFields(resultUser,
                "createdDate", "updatedDate", "password");
    }

    @Test
    void testUpdate_invalidId() {
        user.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> userDao.update(user));
    }

    @Test
    void testUpdate_omittedNotNullFieldsExceptId() {
        // Creating empty user item
        User someUser = new User();

        // Setting id of an existing user
        someUser.setId(user.getId());

        assertThrows(DbOperationException.class, () -> userDao.update(someUser));
    }

    @Test
    void testDelete_successFlow() {
        // Deleting user from db
        assertTrue(userDao.delete(user.getId()));
    }

    @Test
    void testDelete_invalidId() {
        // Deleting not existing user from db
        assertThrows(NotFoundException.class, () -> userDao.delete(Long.MAX_VALUE));
    }

    @AfterEach
    void AfterEachTearDown() {
        clearTables("Users");
    }

}
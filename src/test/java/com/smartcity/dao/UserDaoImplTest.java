package com.smartcity.dao;

import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void beforeEachSetUp() {
        // Initializing test user
        user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");

        // Creating user
        userDao.create(user);
    }

    @Test
    public void testCreate_successFlow() {
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
        assertTrue(newUser.isActive());
    }

    @Test
    public void testCreate_duplicateUsername() {
        // A user with this name already exists
        // Should throw DbOperationException
        assertThrows(DbOperationException.class, () -> userDao.create(user));
    }


    @Test
    public void testCreate_omittedNotNullFields() {
        // Creating empty user item
        User emptyUserItem = new User();
        assertThrows(DbOperationException.class, () -> userDao.create(emptyUserItem));
    }

    @Test
    public void testGet_successFlow() {
        // Getting user
        User resultUser = userDao.get(user.getId());

        // Encrypting user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    public void testGetUser_invalidId() {
        assertThrows(NotFoundException.class, () -> userDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testFindByEmail_successFlow() {
        User resultUser = userDao.findByEmail(user.getEmail());

        // Encrypting user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    public void testFindByEmail_incorrectEmail() {
        assertThrows(NotFoundException.class, () -> userDao.findByEmail("not_existing_email@gmail.com"));
    }

    @Test
    public void testUpdate_successFlow() {
        // Creating updated user
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setEmail("updated@gmail.com");
        updatedUser.setSurname("Smith");
        updatedUser.setName("Den");
        updatedUser.setPhoneNumber("0333333333");
        updatedUser.setCreatedDate(user.getCreatedDate());

        // Updating
        userDao.update(updatedUser);


        // Getting user from db
        User resultUser = userDao.get(user.getId());

        // Checking if both are equal
        assertThat(updatedUser).isEqualToIgnoringGivenFields(resultUser,
                "createdDate", "updatedDate", "password");
    }

    @Test
    public void testUpdate_invalidId() {
        user.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> userDao.update(user));
    }

    @Test
    public void testUpdate_omittedNotNullFieldsExceptId() {
        // Creating empty user item
        User someUser = new User();

        // Setting id of an existing user
        someUser.setId(user.getId());

        assertThrows(DbOperationException.class, () -> userDao.update(someUser));
    }

    @Test
    public void testDelete_successFlow() {
        // Deleting user from db
        assertTrue(userDao.delete(user.getId()));
    }

    @Test
    public void testDelete_invalidId() {
        // Deleting not existing user from db
        assertThrows(NotFoundException.class, () -> userDao.delete(Long.MAX_VALUE));
    }

    @AfterEach
    public void AfterEachTearDown() {
        clearTables("Users");
    }

}
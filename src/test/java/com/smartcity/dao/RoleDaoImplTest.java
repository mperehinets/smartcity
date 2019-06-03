package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleDaoImplTest extends BaseTest {

    @Autowired
    private RoleDaoImpl roleDao;
    @Autowired
    private UserMapper mapper;

    private Role role = new Role(Long.MAX_VALUE, "User", LocalDateTime.now(), LocalDateTime.now());

    @Test
    void createRole() {
        assertEquals(role, roleDao.create(role));
    }

    @Test
    void createRole_NameIsNull() {
        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.create(role));
    }

    @Test
    void findRole() {
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.findById(role.getId()),
                "createdDate", "updatedDate");
    }

    @Test
    void findAllRoles() {
        clearTables("Roles");
        roleDao.create(role);
        Role role1 = new Role(3L, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        roleDao.create(role1);
        List<Role> roles = roleDao.findAll();

        assertAll("are fields of element equals",
                () -> assertEquals(roles.get(0).getId(), role.getId()),
                () -> assertEquals(roles.get(1).getId(), role1.getId()),
                () -> assertEquals(roles.get(0).getName(), role.getName()),
                () -> assertEquals(roles.get(1).getName(), role1.getName())
        );
    }

    @Test
    void findRole_invalidId() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.findById(role.getId()));
    }

    @Test
    void addRoleToUserTest() {
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource, mapper).create(user);

        assertTrue(roleDao.addRoleToUser(user.getId(), role.getId()));

    }

    @Test
    void removeRoleFromUserTest() {
        clearTables("Users");
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource, mapper).create(user);

        roleDao.addRoleToUser(user.getId(), role.getId());
        assertTrue(roleDao.removeRoleFromUser(user.getId(), role.getId()));

    }

    @Test
    void removeRoleFromUser_invalidId() {
        clearTables("Users");
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource, mapper).create(user);

        roleDao.addRoleToUser(user.getId(), role.getId());
        assertFalse(roleDao.removeRoleFromUser(user.getId() + 12L, role.getId()));
    }

    @Test
    void findRolesByUserId() {
        clearTables("Users_roles");
        clearTables("Users");
        clearTables("Roles");

        roleDao.create(role);
        Role role1 = new Role(2L, "Admin", LocalDateTime.now(), LocalDateTime.now());
        roleDao.create(role1);

        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource, mapper).create(user);

        template.update("insert into Users_roles(role_id,user_id) values (1," + user.getId() + ");");
        template.update("insert into Users_roles(role_id,user_id) values (2," + user.getId() + ");");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role1);
        List<Role> rolesFromDB = roleDao.getRolesByUserId(user.getId());

        assertAll("equals roles",
                () -> assertEquals(roles.get(0).getId(), rolesFromDB.get(0).getId()),
                () -> assertEquals(roles.get(0).getName(), rolesFromDB.get(0).getName()),
                () -> assertEquals(roles.get(1).getId(), rolesFromDB.get(1).getId()),
                () -> assertEquals(roles.get(1).getName(), rolesFromDB.get(1).getName())
        );
    }

    @Test
    void deleteRole() {
        assertTrue(roleDao.delete(role.getId()));
    }

    @Test
    void deleteRole_invalidId() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.delete(role.getId()));
    }

    @Test
    void updateRole() {
        role.setName("Supervisor");
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.update(role), "updatedDate");
    }

    @Test
    void updateRole_invalidId() {
        Role updatedRole = new Role(Long.MAX_VALUE - 2, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> roleDao.update(updatedRole));
    }

    @Test
    void updateRole_invalidName() {
        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.update(role));
    }

    @BeforeEach
    void createTestRole() {
        roleDao.create(role);
    }

    @AfterEach
    void cleanRoles() {
        clearTables("Roles");
    }

}
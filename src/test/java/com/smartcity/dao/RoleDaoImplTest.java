package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RoleDaoImplTest extends BaseTest {

    @Autowired
    private RoleDaoImpl roleDao;

    private Role role = new Role(Long.MAX_VALUE, "User", LocalDateTime.now(), LocalDateTime.now());

    @Test
    public void createRole() {

        assertEquals(role, roleDao.create(role));
    }

    @Test
    public void createRole_NameIsNull() {
        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.create(role));
    }

    @Test
    public void getRole() {
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.get(role.getId()),
                "createdDate", "updatedDate");
    }

    @Test
    public void getAllRoles() {
        clearTables("Roles");
        roleDao.create(role);
        Role role1 = new Role(3L, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        roleDao.create(role1);
        List<Role> roles = roleDao.getAll();

        assertAll("are fields of element equals",
                () -> assertEquals(roles.get(0).getId(), role.getId()),
                () -> assertEquals(roles.get(1).getId(), role1.getId()),
                () -> assertEquals(roles.get(0).getName(), role.getName()),
                () -> assertEquals(roles.get(1).getName(), role1.getName())
        );
    }

    @Test
    public void getRole_unexistingRole() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.get(role.getId()));
    }

    @Test
    public void addRoleToUserTest() {
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource).create(user);

        assertTrue(roleDao.addRoleToUser(user.getId(), role.getId()));

    }

    @Test
    public void removeRoleFromUserTest() {
        clearTables("Users");
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource).create(user);

        roleDao.addRoleToUser(user.getId(), role.getId());
        assertTrue(roleDao.removeRoleFromUser(user.getId(), role.getId()));

    }

    @Test
    public void removeRoleFromUser_wrongId() {
        clearTables("Users");
        User user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");
        new UserDaoImpl(dataSource).create(user);

        roleDao.addRoleToUser(user.getId(), role.getId());
        assertFalse(roleDao.removeRoleFromUser(user.getId() + 12L, role.getId()));
    }

    @Test
    public void getRolesByUserId() {
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
        new UserDaoImpl(dataSource).create(user);

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
    public void deleteRole() {
        assertTrue(roleDao.delete(role.getId()));
    }

    @Test
    public void deleteRole_unexistingRole() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.delete(role.getId()));
    }

    @Test
    public void updateRole() {
        role.setName("Supervisor");
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.update(role), "updatedDate");
    }

    @Test
    public void updateRole_unexistingRole() {

        Role updatedRole = new Role(Long.MAX_VALUE - 2, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> roleDao.update(updatedRole));
    }

    @Test
    public void updateRole_NameIsNull() {

        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.update(role));
    }

    @BeforeEach
    public void createTestRole() {
        roleDao.create(role);
    }

    @AfterEach
    public void cleanRoles() {
        clearTables("Roles");
    }

}
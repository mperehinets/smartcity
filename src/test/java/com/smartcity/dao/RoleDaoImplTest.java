package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
        Role role1 = roleDao.get(1L);
        Role role3 = new Role(3L, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        roleDao.create(role3);
        List<Role> roles = roleDao.getAll();

        assertAll("are fields of element equals",
                () -> assertEquals(roles.get(0).getId(), role1.getId()),
                () -> assertEquals(roles.get(1).getId(), role.getId()),
                () -> assertEquals(roles.get(2).getId(), role3.getId()),
                () -> assertEquals(roles.get(0).getName(), role1.getName()),
                () -> assertEquals(roles.get(1).getName(), role.getName()),
                () -> assertEquals(roles.get(2).getName(), role3.getName())
        );
    }

    @Test
    public void getRole_unexistingRole() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.get(role.getId()));
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
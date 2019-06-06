package com.smartcity.service;

import com.smartcity.dao.RoleDao;
import com.smartcity.domain.Role;
import com.smartcity.dto.RoleDto;
import com.smartcity.mapperDto.RoleDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    private final RoleDto roleDto = new RoleDto(2L, "ADMIN", LocalDateTime.now(), LocalDateTime.now());
    private RoleDtoMapper roleDtoMapper = new RoleDtoMapper();

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        roleService = new RoleServiceImpl(roleDao, roleDtoMapper);
        role = roleDtoMapper.roleDtoToRole(roleDto);
    }

    @Test
    public void createRoleTest() {
        doReturn(role).when(roleDao).create(role);
        RoleDto result = roleService.create(roleDto);
        assertEquals(result, roleDtoMapper.roleToRoleDto(role));
    }

    @Test
    public void findRoleByIdTest() {
        doReturn(role).when(roleDao).findById(role.getId());
        RoleDto result = roleService.findById(roleDto.getId());
        assertEquals(result, roleDtoMapper.roleToRoleDto(role));
    }

    @Test
    public void findAllRolesTest() {
        Role role1 = new Role(3L, "USER", LocalDateTime.now(), LocalDateTime.now());

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role1);

        doReturn(roles).when(roleDao).findAll();

        List<RoleDto> roleDtos = roleService.findAll();

        assertAll("equals",
                () -> assertEquals(roleDtos.get(0), roleDtoMapper.roleToRoleDto(roles.get(0))),
                () -> assertEquals(roleDtos.get(1), roleDtoMapper.roleToRoleDto(roles.get(1))));
    }

}

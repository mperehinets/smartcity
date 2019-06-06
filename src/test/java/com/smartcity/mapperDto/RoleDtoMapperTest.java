package com.smartcity.mapperDto;

import com.smartcity.domain.Role;
import com.smartcity.dto.RoleDto;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RoleDtoMapperTest {

    private Role role;
    private RoleDto roleDto;

    private RoleDtoMapper roleDtoMapper = new RoleDtoMapper();

    @BeforeEach
    public void init() {
        role = new Role(1L, "ADMIN", LocalDateTime.now(), LocalDateTime.now());

        roleDto = new RoleDto();
        roleDto.setName(role.getName());
        roleDto.setId(role.getId());
        roleDto.setCreatedDate(role.getCreatedDate());
        roleDto.setUpdatedDate(role.getUpdatedDate());
    }

    @Test
    public void convertRoleToRoleDtoTest() {
        assertThat(roleDto).
                isEqualToIgnoringGivenFields(roleDtoMapper.roleToRoleDto(role), "createdDate");
    }

    @Test
    public void convertRoleDtoToRoleTest() {

        assertEquals(role, roleDtoMapper.roleDtoToRole(roleDto));
    }
}

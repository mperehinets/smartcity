package com.smartcity.mapperDto;

import com.smartcity.domain.Role;
import com.smartcity.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoMapper {

    public RoleDto roleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setCreatedDate(role.getUpdatedDate());
        roleDto.setUpdatedDate(role.getUpdatedDate());
        return roleDto;
    }

    public Role roleDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setCreatedDate(roleDto.getCreatedDate());
        role.setUpdatedDate(roleDto.getUpdatedDate());
        return role;
    }
}

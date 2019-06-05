package com.smartcity.service;

import com.smartcity.dao.RoleDao;
import com.smartcity.domain.Role;
import com.smartcity.dto.RoleDto;
import com.smartcity.mapperDto.RoleDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;
    private RoleDtoMapper roleDtoMapper;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, RoleDtoMapper roleDtoMapper) {
        this.roleDao = roleDao;
        this.roleDtoMapper = roleDtoMapper;
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        return roleDtoMapper.roleToRoleDto(roleDao.create(roleDtoMapper.roleDtoToRole(roleDto)));
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleDao.findAll();
        return roles.stream().map(roleDtoMapper::roleToRoleDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id) {
        return roleDtoMapper.roleToRoleDto(roleDao.findById(id));
    }

}

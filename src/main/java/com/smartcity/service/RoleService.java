package com.smartcity.service;

import com.smartcity.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto create(RoleDto roleDto);

    RoleDto findById(Long id);

    List<RoleDto> findAll();

}

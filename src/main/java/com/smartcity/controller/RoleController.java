package com.smartcity.controller;

import com.smartcity.dto.RoleDto;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getRoleControllerCreateRoleAllowedRoles())")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public RoleDto createRole(@Validated(NewRecord.class) @RequestBody RoleDto roleDto) {
        return roleService.create(roleDto);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleDto findRoleById(@PathVariable("id") Long id) {
        return roleService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDto> findAllRoles() {
        return roleService.findAll();
    }
}

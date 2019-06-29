package com.smartcity.controller;

import com.smartcity.dto.OrganizationDto;
import com.smartcity.dto.UserDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.OrganizationService;
import com.smartcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    private OrganizationService organizationService;
    private UserService userService;

    @Autowired
    public OrganizationController(OrganizationService organizationService, UserService userService) {
        this.organizationService = organizationService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getOrganizationControllerCreateAllowedRoles())")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public OrganizationDto create(@Validated(NewRecord.class) @RequestBody OrganizationDto organizationDto) {
        return organizationService.create(organizationDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto findById(@PathVariable("id") Long id) {
        return organizationService.findById(id);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getOrganizationControllerUpdateAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto update(
            @PathVariable("id") Long id,
            @Validated(ExistingRecord.class) @RequestBody OrganizationDto organizationDto) {
        organizationDto.setId(id);
        return organizationService.update(organizationDto);
    }

    @PreAuthorize("hasAnyRole(@securityConfiguration.getOrganizationControllerDeleteAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return organizationService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrganizationDto> findAll() {
        return organizationService.findAll();
    }

    @PreAuthorize("hasAnyRole" +
            "(@securityConfiguration.getOrganizationControllerAddUserToOrganizationAllowedRoles())")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{organizationId}/addUser/{userId}")
    public boolean addUserToOrganization(@PathVariable("organizationId") Long organizationId,
                                         @PathVariable("userId") Long userId) {
        UserDto userDto = userService.findById(userId);
        OrganizationDto organizationDto = organizationService.findById(organizationId);
        return organizationService.addUserToOrganization(organizationDto, userDto);
    }

    @PreAuthorize("hasAnyRole" +
            "(@securityConfiguration.getOrganizationControllerRemoveUserFromOrganizationAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{organizationId}/removeUser/{userId}")
    public boolean removeUserFromOrganization(@PathVariable("organizationId") Long organizationId,
                                              @PathVariable("userId") Long userId) {
        UserDto userDto = userService.findById(userId);
        OrganizationDto organizationDto = organizationService.findById(organizationId);
        return organizationService.removeUserFromOrganization(organizationDto, userDto);
    }

}

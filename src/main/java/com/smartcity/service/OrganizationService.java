package com.smartcity.service;

import com.smartcity.dto.OrganizationDto;
import com.smartcity.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationService {
    OrganizationDto create(OrganizationDto organizationDto);

    OrganizationDto findById(Long id);

    OrganizationDto update(OrganizationDto organizationDto);

    boolean delete(Long id);

    List<OrganizationDto> findAll();

    boolean addUserToOrganization(OrganizationDto organizationDto, UserDto userDto);

    boolean removeUserFromOrganization(OrganizationDto organizationDto, UserDto userDto);
}


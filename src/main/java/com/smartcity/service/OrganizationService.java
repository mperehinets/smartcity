package com.smartcity.service;

import com.smartcity.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {
    OrganizationDto create(OrganizationDto organizationDto);

    OrganizationDto findById(Long id);

    OrganizationDto update(OrganizationDto organizationDto);

    boolean delete(Long id);

    List<OrganizationDto> findAll();
}


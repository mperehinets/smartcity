package com.smartcity.mapperDto;

import com.smartcity.domain.Organization;
import com.smartcity.dto.OrganizationDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDtoMapper {

    public OrganizationDto organizationToOrganizationDto(Organization organization) {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organization.getId());
        organizationDto.setName(organization.getName());
        organizationDto.setAddress(organization.getAddress());
        organizationDto.setCreatedDate(organization.getCreatedDate());
        organizationDto.setUpdatedDate(organization.getUpdatedDate());
        return organizationDto;
    }

    public Organization organizationDtoToOrganization(OrganizationDto organizationDto) {
        Organization organization = new Organization();
        organization.setId(organizationDto.getId());
        organization.setName(organizationDto.getName());
        organization.setAddress(organizationDto.getAddress());
        organization.setCreatedDate(organizationDto.getCreatedDate());
        organization.setUpdatedDate(organizationDto.getUpdatedDate());
        return organization;
    }
}
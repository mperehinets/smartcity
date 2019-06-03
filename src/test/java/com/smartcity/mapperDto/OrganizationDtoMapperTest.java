package com.smartcity.mapperDto;

import com.smartcity.domain.Organization;
import com.smartcity.dto.OrganizationDto;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrganizationDtoMapperTest {

    private Organization organization;
    private OrganizationDto organizationDto;

    private OrganizationDtoMapper organizationDtoMapper = new OrganizationDtoMapper();

    @BeforeEach
    void setUp() {
        organization = new Organization();
        organization.setId(1L);
        organization.setName("komunalna");
        organization.setAddress("saharova 13");
        organization.setUpdatedDate(LocalDateTime.now());
        organization.setCreatedDate(LocalDateTime.now());

        organizationDto = new OrganizationDto();
        organizationDto.setId(organization.getId());
        organizationDto.setName(organization.getName());
        organizationDto.setAddress(organization.getAddress());
        organizationDto.setUpdatedDate(organization.getUpdatedDate());
        organizationDto.setCreatedDate(organization.getCreatedDate());
    }

    @Test
    void testConvertDaoToDto() {
        assertThat(organizationDto).isEqualToIgnoringGivenFields(organizationDtoMapper
                .organizationToOrganizationDto(organization));
    }

    @Test
    void testConvertDtoToDao() {
        assertThat(organizationDtoMapper.organizationDtoToOrganization(organizationDto))
                .isEqualToIgnoringGivenFields(organization);
    }
}
package com.smartcity.service;

import com.smartcity.dao.OrganizationDao;
import com.smartcity.domain.Organization;
import com.smartcity.domain.User;
import com.smartcity.dto.OrganizationDto;
import com.smartcity.mapperDto.OrganizationDtoMapper;
import com.smartcity.mapperDto.UserDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

    @Mock
    private OrganizationDao organizationDao;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private final OrganizationDto organizationDto = new OrganizationDto(1L,
            "komunalna",
            "saharova 13",
            LocalDateTime.now(), LocalDateTime.now());

    private OrganizationDtoMapper organizationDtoMapper = new OrganizationDtoMapper();
    private UserDtoMapper userDtoMapper = new UserDtoMapper();

    private Organization organization;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        organizationService = new OrganizationServiceImpl(organizationDao, organizationDtoMapper, userDtoMapper);
        organization = organizationDtoMapper.organizationDtoToOrganization(organizationDto);
    }

    @Test
    void testCreateOrganizationDto() {
        doReturn(organization).when(organizationDao).create(organization);
        assertThat(organizationService.create(organizationDto))
                .isEqualToIgnoringGivenFields(organizationDto,
                        "createdDate", "updatedDate");
    }

    @Test
    void testFindOrganizationDto() {
        doReturn(organization).when(organizationDao).findById(organization.getId());
        assertThat(organizationService.findById(organizationDto.getId()))
                .isEqualToIgnoringGivenFields(organizationDto,
                        "createdDate", "updatedDate");
    }

    @Test
    void testUpdateOrganizationDto() {
        doReturn(organization).when(organizationDao).update(organization);
        assertThat(organizationService.update(organizationDto)).isEqualToIgnoringGivenFields(
                organizationDto,
                "createdAt", "updatedAt");
    }

    @Test
    void testDeleteOrganizationDto() {
        doReturn(true).when(organizationDao).delete(organization.getId());
        assertTrue(organizationService.delete(organizationDto.getId()));
    }

    @Test
    void testFindAllOrganizationDto() {
        List<Organization> organizationList = Collections.singletonList(organization);
        List<OrganizationDto> organizationDtoList = Collections.singletonList(organizationDto);
        doReturn(organizationList).when(organizationDao).findAll();
        assertEquals(organizationDtoList, organizationService.findAll());
    }

    @Test
    public void testAddUserDtoToOrganizationDto() {
        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setSurname("Test");
        user.setEmail("example@gmail.com");

        doReturn(true).when(organizationDao).addUserToOrganization(organization, user);

        assertTrue(organizationService.addUserToOrganization(organizationDto, userDtoMapper.convertUserIntoUserDto(user)));
    }

    @Test
    public void testRemoveUserDtoFromOrganizationDto() {
        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setSurname("Test");
        user.setEmail("example@gmail.com");

        doReturn(true).when(organizationDao).removeUserFromOrganization(organization, user);

        assertTrue(organizationService.removeUserFromOrganization(organizationDto,
                userDtoMapper.convertUserIntoUserDto(user)));
    }


}

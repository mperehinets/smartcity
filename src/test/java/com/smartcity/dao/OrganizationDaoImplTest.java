package com.smartcity.dao;

import com.smartcity.domain.Organization;
import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationDaoImplTest extends BaseTest {

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private UserDao userDao;

    private Organization organization = new Organization(1L,
            "komunalna",
            "saharova 13",
            LocalDateTime.now(), LocalDateTime.now());

    @Test
    void testCreateOrganization() {
        assertThat(organizationDao.create(organization)).isEqualToIgnoringGivenFields(organization,
                "createdDate", "updatedDate");
    }

    @Test
    void testCreateOrganization_missingCreatedDate() {
        organization.setCreatedDate(null);
        organizationDao.create(organization);
        assertNotEquals(organizationDao.findById(organization.getId()).getCreatedDate(), null);
    }

    @Test
    void testCreateOrganization_emptyOrganization() {
        Organization emptyOrganiation = new Organization();
        assertThrows(DbOperationException.class, () -> organizationDao.create(emptyOrganiation));
    }

    @Test
    void testFindOrganization() {
        organizationDao.create(organization);
        assertThat(organizationDao.findById(organization.getId())).isEqualToIgnoringGivenFields(organization,
                "createdDate", "updatedDate");
    }

    @Test
    void testFindOrganization_invalidId() {
        assertThrows(NotFoundException.class, () -> organizationDao.findById(Long.MAX_VALUE));
    }

    @Test
    void testUpdateOrganization() {
        // Creating updateOrganization
        organizationDao.create(organization);
        Organization updatedOrganization = new Organization();
        updatedOrganization.setId(organization.getId());
        updatedOrganization.setName("komunalna");
        updatedOrganization.setAddress("vovchunecka 28A");
        updatedOrganization.setUpdatedDate(LocalDateTime.now());

        // Updating organization
        organizationDao.update(updatedOrganization);

        // Checking if both organization are equal
        assertThat(organizationDao.findById(updatedOrganization.getId())).isEqualToIgnoringGivenFields(updatedOrganization,
                "createdDate", "updatedDate");
    }

    @Test
    void testUpdateOrganization_invalidId() {
        // Creating updateOrganization
        Organization updatedOrganization = new Organization();
        updatedOrganization.setId(Long.MAX_VALUE);
        updatedOrganization.setName("komunalna");
        updatedOrganization.setAddress("vovchunecka 28A");
        updatedOrganization.setUpdatedDate(LocalDateTime.now());

        // Updating organization
        assertThrows(NotFoundException.class, () -> organizationDao.update(updatedOrganization));
    }

    @Test
    void testDeleteOrganization() {
        organizationDao.create(organization);
        // Deleting organization from db
        assertTrue(organizationDao.delete(organization.getId()));
    }

    @Test
    void testDeleteOrganization_invalidId() {
        assertThrows(NotFoundException.class, () -> organizationDao.delete(Long.MAX_VALUE));
    }

    @Test
    void testFindAll() {
        organizationDao.create(organization);
        assertThat(organizationDao.findAll().get(0)).isEqualToIgnoringGivenFields(organization,
                "createdDate", "updatedDate");

    }

    @Test
    public void testAddUserToOrganization() {
        assertTrue(organizationDao.addUserToOrganization(organizationDao.create(organization), userDao.findById(1L)));
    }

    @Test
    public void testRemoveUserFromOrganization() {
        organizationDao.create(organization);
        organizationDao.addUserToOrganization(organization, userDao.findById(1L));

        assertTrue(organizationDao.removeUserFromOrganization(organization, userDao.findById(1L)));
    }

    @Test
    public void testRemoveUserFromOrganization_invalidUserId() {
        organizationDao.create(organization);
        User user = userDao.findById(1L);
        organizationDao.addUserToOrganization(organization, userDao.findById(1L));
        user.setId(Long.MAX_VALUE);

        assertThrows(NotFoundException.class, () -> organizationDao.removeUserFromOrganization(organization, user));
    }

    @Test
    public void testRemoveUserFromOrganization_invalidOrganizationId() {
        organizationDao.create(organization);
        User user = userDao.findById(1L);
        organizationDao.addUserToOrganization(organization, userDao.findById(1L));
        organization.setId(Long.MAX_VALUE);

        assertThrows(NotFoundException.class, () -> organizationDao.removeUserFromOrganization(organization, user));
    }

    @AfterEach
    void cleanOrganization() {
        clearTables("Organizations");
    }

}

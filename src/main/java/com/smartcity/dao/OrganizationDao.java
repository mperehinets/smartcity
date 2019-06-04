package com.smartcity.dao;

import com.smartcity.domain.Organization;
import com.smartcity.domain.User;

import java.util.List;

public interface OrganizationDao {

    Organization create(Organization organization);

    Organization findById(Long id);

    Organization update(Organization organization);

    boolean delete(Long id);

    List<Organization> findAll();

    boolean addUserToOrganization(Organization organization, User user);

    boolean removeUserFromOrganization(Organization organization, User user);
}

package com.smartcity.dao;

import com.smartcity.domain.Organization;

import java.util.List;

public interface OrganizationDao {

    Organization create(Organization organization);

    Organization findById(Long id);

    Organization update(Organization organization);

    boolean delete(Long id);

    List<Organization> findAll();
}

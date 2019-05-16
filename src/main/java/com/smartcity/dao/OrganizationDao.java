package com.smartcity.dao;

import com.smartcity.domain.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDao {

    Organization create(Organization organization);

    Organization get(Long id);

    Organization update(Organization organization);

    boolean delete(Long id);

    List<Organization> getAll();
}

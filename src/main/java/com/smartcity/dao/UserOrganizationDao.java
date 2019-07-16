package com.smartcity.dao;

public interface UserOrganizationDao {

    Long findIdByUserIdAndOrgId(Long userId, Long orgId);

    Long findUserIdById(Long userOrgId);

    Long findOrgIdById(Long userOrgId);
}

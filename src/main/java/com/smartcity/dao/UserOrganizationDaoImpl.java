package com.smartcity.dao;

import com.smartcity.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserOrganizationDaoImpl implements UserOrganizationDao{


    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserOrganizationDaoImpl.class);

    @Autowired
    public UserOrganizationDaoImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long findIdByUserIdAndOrgId(Long userId, Long orgId) {
        try {
            return this.jdbcTemplate.queryForObject(Queries.SQL_GET_USERS_ORG_ID, Long.class, userId, orgId);
        } catch (Exception e) {
            logger.error("FindUserOrgId UserOrgDao method error for UserId: " + userId + "OrgId: " + orgId + e);
            throw new NotFoundException("FindUserOrgId UserOrgDao method error for UserId: " + userId + "OrgId: " + orgId + e);
        }
    }

    @Override
    public Long findUserIdById(Long userOrgId) {
        try {
            return this.jdbcTemplate.queryForObject(Queries.SQL_GET_USER_ID_BY_USERORG_ID, Long.class, userOrgId);
        } catch (Exception e) {
            logger.error("FindUserId UserOrgDao method error for UserOrgId: " + userOrgId + e);
            throw new NotFoundException("FindUserIdUserOrg Dao method error for UserOrgId: " + userOrgId + e);
        }
    }

    @Override
    public Long findOrgIdById(Long userOrgId) {
        try {
            return this.jdbcTemplate.queryForObject(Queries.SQL_GET_ORG_ID_BY_USERORG_ID, Long.class, userOrgId);
        } catch (Exception e) {
            logger.error("FindUserId UserOrgDao method error for UserOrgId: " + userOrgId + e);
            throw new NotFoundException("FindUserId UserOrgDao method error for UserOrgId: " + userOrgId + e);
        }
    }

    class Queries {
        static final String SQL_GET_USERS_ORG_ID = "Select id from Users_organizations where user_id = ? and organization_id = ?;";
        static final String SQL_GET_USER_ID_BY_USERORG_ID = "Select user_id from Users_organizations where id = ?;";
        static final String SQL_GET_ORG_ID_BY_USERORG_ID = "Select organization_id from Users_organizations where id = ?;";
    }
}

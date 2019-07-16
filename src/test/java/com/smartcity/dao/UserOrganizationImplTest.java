package com.smartcity.dao;

import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserOrganizationImplTest extends BaseTest {

    @Autowired
    private UserOrganizationDao userOrganizationDao;

    @Test
    void testFindIdByUserIdOrgId() {
        assertEquals(userOrganizationDao.findIdByUserIdAndOrgId(1L, 1L), 1L);
    }

    @Test
    void testFindIdByUserIdOrgId_InvalidId() {
        assertThrows(NotFoundException.class, () -> userOrganizationDao.findIdByUserIdAndOrgId(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    @Test
    void testFindUserId(){
        assertEquals(userOrganizationDao.findUserIdById(1L), 1L);
    }

    @Test
    void testFindUserId_InvalidId(){
        assertThrows(NotFoundException.class, () -> userOrganizationDao.findUserIdById(Long.MAX_VALUE));
    }

    @Test
    void testFindOrgId(){
        assertEquals(userOrganizationDao.findOrgIdById(1L), 1L);
    }

    @Test
    void testFindOrgId_InvalidId(){
        assertThrows(NotFoundException.class, () -> userOrganizationDao.findOrgIdById(Long.MAX_VALUE));
    }
}

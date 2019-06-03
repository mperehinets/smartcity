package com.smartcity.dao;

import com.smartcity.domain.Role;

import java.util.List;

public interface RoleDao {

    Role create(Role role);

    Role findById(Long id);

    List<Role> findAll();

    Role update(Role role);

    boolean delete(Long id);

    List<Role> getRolesByUserId(Long userId);

    boolean addRoleToUser(Long userId, Long roleId);

    boolean removeRoleFromUser(Long userId, Long roleId);
}

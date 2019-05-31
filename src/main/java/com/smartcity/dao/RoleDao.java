package com.smartcity.dao;

import com.smartcity.domain.Role;

import java.util.List;

public interface RoleDao {

    Role create(Role role);

    Role get(Long id);

    Role update(Role role);

    boolean delete(Long id);

    List<Role> getRolesByUserId(Long userId);
}

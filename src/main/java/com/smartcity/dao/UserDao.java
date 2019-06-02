package com.smartcity.dao;

import com.smartcity.domain.User;

import java.util.List;

public interface UserDao {

    User create(User user);

    User get(Long id);

    List<User> getAll();

    User findByEmail(String email);

    User update(User user);

    boolean delete(Long id);

    boolean updatePassword(Long userId, String newPassword);

}

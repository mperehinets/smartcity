package com.smartcity.service;

import com.smartcity.domain.Role;
import com.smartcity.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    UserDto create(UserDto user);

    UserDto findById(Long id);

    List<UserDto> getAll();

    UserDto findByEmail(String email);

    UserDto update(UserDto user);

    boolean delete(Long id);

    boolean activate(Long id);

    boolean updatePassword(Long userId, String newPassword);

    List<Role> getRoles(Long id);

    boolean setRoles(Long userId, List<Long> newRolesIds);
}

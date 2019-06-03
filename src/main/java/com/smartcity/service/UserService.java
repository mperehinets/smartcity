package com.smartcity.service;

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

    boolean updatePassword(Long userId, String newPassword);
}

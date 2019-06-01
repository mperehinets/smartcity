package com.smartcity.service;

import com.smartcity.dao.RoleDao;
import com.smartcity.dao.UserDao;
import com.smartcity.domain.Role;
import com.smartcity.domain.User;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDtoMapper userDtoMapper;
    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoMapper userDtoMapper, RoleDao roleDao) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
        this.roleDao = roleDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userDtoMapper.convertUserDtoIntoUser(userDto);
        return userDtoMapper.convertUserIntoUserDto(userDao.create(user));
    }

    @Override
    public UserDto get(Long id) {
        return userDtoMapper.convertUserIntoUserDto(userDao.get(id));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userDtoMapper.convertUserIntoUserDto(userDao.findByEmail(email));
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userDtoMapper.convertUserDtoIntoUser(userDto);
        return userDtoMapper.convertUserIntoUserDto(userDao.update(user));
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        List<Role> rolesByUserId = roleDao.getRolesByUserId(user.getId());
        user.setAuthorities(rolesByUserId);
        return user;
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) {
        return userDao.updatePassword(userId, newPassword);
    }
}
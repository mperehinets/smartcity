package com.smartcity.mapperDto;

import com.smartcity.domain.User;
import com.smartcity.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto convertUserIntoUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setActive(user.isActive());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setUpdatedDate(user.getUpdatedDate());
        return userDto;
    }

    public User convertUserDtoIntoUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setActive(userDto.isActive());
        user.setCreatedDate(userDto.getCreatedDate());
        user.setUpdatedDate(userDto.getUpdatedDate());
        return user;
    }
}

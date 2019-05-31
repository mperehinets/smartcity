package com.smartcity.mapperDto;

import com.smartcity.config.ProfileConfig;
import com.smartcity.domain.User;
import com.smartcity.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class UserDtoMapperTest {

    private User user;
    private UserDto userDto;

    @Autowired
    private UserDtoMapper userDtoMapper;


    @BeforeEach
    void setUp() {

        // Initializing user object
        user = new User();
        user.setId(1L);
        user.setPassword("qwerty");
        user.setName("User");
        user.setSurname("Test");
        user.setEmail("email@mail.com");
        user.setPhoneNumber("095105650540");
        user.setActive(false);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        //Initializing userDTO object
        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword("qwerty");
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setUpdatedDate(user.getUpdatedDate());
    }

    @Test
    void convertUserIntoUserDto() {
        UserDto resultUserDto = userDtoMapper.convertUserIntoUserDto(user);

        assertThat(user).isEqualToIgnoringGivenFields(resultUserDto, "password", "authorities");

        // Checking if password is null
        assertNull(resultUserDto.getPassword());
    }

    @Test
    void convertUserDtoIntoUser() {
        User resultUser = userDtoMapper.convertUserDtoIntoUser(userDto);
        assertEquals(user, resultUser);
    }
}
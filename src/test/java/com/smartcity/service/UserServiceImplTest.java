package com.smartcity.service;

import com.smartcity.config.ProfileConfig;
import com.smartcity.dao.RoleDao;
import com.smartcity.dao.UserDao;
import com.smartcity.domain.User;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.UserDtoMapper;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {ProfileConfig.class})
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDtoMapper userDtoMapper;

    private UserDto userDto;

    private User user;

    private RoleDao roleDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDtoMapper = new UserDtoMapper();

        userService = new UserServiceImpl(userDao, userDtoMapper, roleDao);

        userDto = new UserDto();
        userDto.setName("User");
        userDto.setSurname("Test");
        userDto.setEmail("example@gmail.com");

        user = userDtoMapper.convertUserDtoIntoUser(userDto);
    }

    @Test
    void create_successFlow() {
        Mockito.when(userDao.create(user)).then(invocationOnMock -> {
            User user = invocationOnMock.getArgument(0);
            user.setId(1L);
            user.setActive(true);
            return user;
        });

        UserDto resultUserDto = userService.create(userDto);

        // Checking if the id was generated
        assertNotNull(resultUserDto.getId());

        // Checking if the user is active
        assertTrue(resultUserDto.isActive());
    }

    @Test
    void get_successFlow() {
        Mockito.when(userDao.get(1L)).thenReturn(user);

        UserDto resultUserDto = userService.get(1L);

        // Checking if the correct user was returned
        assertThat(userDto).isEqualToIgnoringGivenFields(resultUserDto, "password");
    }

    @Test
    void findByEmail_successFlow() {
        Mockito.when(userDao.findByEmail(userDto.getEmail())).thenReturn(user);

        UserDto resultUserDto = userService.findByEmail(userDto.getEmail());

        // Checking if the correct user was returned
        assertThat(userDto).isEqualToIgnoringGivenFields(resultUserDto, "password");
    }

    @Test
    void update_successFlow() {

        userDto.setName("AnotherUser");

        User updatedUser = userDtoMapper.convertUserDtoIntoUser(userDto);

        Mockito.when(userDao.update(updatedUser)).then(
                invocationOnMock -> (User) invocationOnMock.getArgument(0));

        UserDto resultUserDto = userService.update(userDto);

        // Checking if the correct user was returned
        assertThat(userDto).isEqualToIgnoringGivenFields(resultUserDto, "password");
    }

    @Test
    void delete_successFlow() {
        Mockito.when(userDao.delete(1L)).then(invocationOnMock -> {
            userDto.setActive(false);
            return true;
        });

        boolean result = userService.delete(1L);

        // Checking if true was returned
        assertTrue(result);

        // Checking if the user is not active
        assertFalse(userDto.isActive());
    }

    @Test
    void updatePassword_successFlow() {
        Mockito.when(userDao.updatePassword(1L, "qwerty"))
                .then(invocationOnMock -> true);

        assertTrue(userService.updatePassword(1L, "qwerty"));
    }
}
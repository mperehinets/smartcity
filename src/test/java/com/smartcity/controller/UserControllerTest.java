package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.dto.UserDto;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.UserService;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private UserDto userDto;

    private final Long fakeId = 5L;
    private final String fakeEmail = "";
    private final DbOperationException dbOperationException = new DbOperationException("Can't create transaction");
    private final NotFoundException notFoundException = new NotFoundException("Transaction with id: " + fakeId + " not found");

    @BeforeEach
    void setUp() {
        // Getting instance of mockMvc
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("User");
        userDto.setSurname("Test");
        userDto.setEmail("example@gmail.com");
    }

    @Test
    void getById_failFlow() throws Exception {
        Mockito.when(userService.get(fakeId))
                .thenThrow(notFoundException);

        mockMvc.perform(get("/users/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/users/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void getById_successFlow() throws Exception {
        Mockito.when(userService.get(userDto.getId())).thenReturn(userDto);

        mockMvc.perform(get("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("email").value(userDto.getEmail()));
    }

    @Test
    void getAll_successFlow() throws Exception {
        // Initializing list of UserDto
        List<UserDto> users = new ArrayList<>();

        UserDto user1 = new UserDto();
        user1.setEmail("some@email.com");
        user1.setPassword("qwerty");
        user1.setSurname("Test");
        user1.setName("User");
        user1.setPhoneNumber("06558818");

        UserDto user2 = new UserDto();
        user2.setEmail("another@email.com");
        user2.setPassword("trewq");
        user2.setSurname("tset");
        user2.setName("Resu");
        user2.setPhoneNumber("05811451");

        users.add(user1);
        users.add(user2);

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/users/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()));
    }

    @Test
    void findByEmail_failFlow() throws Exception {
        Mockito.when(userService.findByEmail(fakeEmail))
                .thenThrow(notFoundException);

        mockMvc.perform(get("/users/?=email=" + fakeEmail)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/users/"))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void findByEmail_successFlow() throws Exception {
        Mockito.when(userService.findByEmail(userDto.getEmail())).thenReturn(userDto);

        mockMvc.perform(get("/users/?=email=" + userDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("email").value(userDto.getEmail()));
    }

    @Test
    void updateUser_successFlow() throws Exception {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userDto.getId());
        updatedUserDto.setName("Updated user");
        updatedUserDto.setSurname("Tested user");
        updatedUserDto.setEmail("just_example@gmail.com");

        Mockito.when(userService.update(updatedUserDto)).thenReturn(updatedUserDto);

        // Instantiating object -> json mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Converting DTO object to json
        String requestObjectJson = objectMapper.writeValueAsString(updatedUserDto);

        mockMvc.perform(put("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestObjectJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedUserDto.getId()))
                .andExpect(jsonPath("name").value(updatedUserDto.getName()))
                .andExpect(jsonPath("surname").value(updatedUserDto.getSurname()))
                .andExpect(jsonPath("email").value(updatedUserDto.getEmail()))
                .andDo(print());
    }

    @Test
    void deleteUser_failFlow() throws Exception {
        Mockito.when(userService.delete(fakeId))
                .thenThrow(notFoundException);

        mockMvc.perform(delete("/users/" + fakeId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/users/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
    void deleteUser_successFlow() throws Exception {
        Mockito.when(userService.delete(userDto.getId())).thenReturn(true);

        mockMvc.perform(delete("/users/" + userDto.getId()))
                .andExpect(status().isOk());
    }

}

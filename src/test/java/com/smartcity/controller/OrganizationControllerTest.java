package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.dto.OrganizationDto;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.exceptions.interceptor.ExceptionInterceptor;
import com.smartcity.service.OrganizationServiceImpl;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {
    @Mock
    private OrganizationServiceImpl organizationService;

    @InjectMocks
    private OrganizationController organizationController;

    private MockMvc mockMvc;
    private OrganizationDto organizationDto;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Long fakeId = 2L;
    private final NotFoundException notFoundException = new NotFoundException("Organization with id: " + fakeId + " not found");

    @BeforeEach
     void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(organizationController)
                .setControllerAdvice(ExceptionInterceptor.class)
                .build();
        organizationDto = new OrganizationDto();
        organizationDto.setId(1L);
        organizationDto.setName("komunalna");
        organizationDto.setAddress("saharova 13");

    }

    @Test
     void createOrganization_successFlow() throws Exception {
        Mockito.when(organizationService.create(organizationDto)).thenReturn(organizationDto);
        // Instantiating object -> json mapper

        String requestObjectJson = objectMapper.writeValueAsString(organizationDto);
        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestObjectJson))
                .andExpect(jsonPath("id").value(organizationDto.getId()))
                .andExpect(jsonPath("name").value(organizationDto.getName()))
                .andExpect(jsonPath("address").value(organizationDto.getAddress()));
    }

    @Test
     void findOrganizationById_failFlow() throws Exception {
        Mockito.when(organizationService.findById(fakeId))
                .thenThrow(notFoundException);

        mockMvc.perform(get("/organizations/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/organizations/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
     void getOrganizationById_successFlow() throws Exception {
        Mockito.when(organizationService.findById(organizationDto.getId())).thenReturn(organizationDto);

        mockMvc.perform(get("/organizations/" + organizationDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(organizationDto.getId()))
                .andExpect(jsonPath("name").value(organizationDto.getName()))
                .andExpect(jsonPath("address").value(organizationDto.getAddress()));
    }

    @Test
     void updateOrganization_successFlow() throws Exception {
        OrganizationDto updatedOrganizationDto = new OrganizationDto();
        updatedOrganizationDto.setId(organizationDto.getId());
        updatedOrganizationDto.setName("komunalna");
        updatedOrganizationDto.setAddress("vovchunecka 28A");

        Mockito.when(organizationService.update(updatedOrganizationDto)).thenReturn(updatedOrganizationDto);

        String requestObjectJson = objectMapper.writeValueAsString(updatedOrganizationDto);

        mockMvc.perform(put("/organizations/" + organizationDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestObjectJson))
                .andExpect(jsonPath("id").value(updatedOrganizationDto.getId()))
                .andExpect(jsonPath("name").value(updatedOrganizationDto.getName()))
                .andExpect(jsonPath("address").value(updatedOrganizationDto.getAddress()))
                .andDo(print());
    }

    @Test
     void deleteOrganization_failFlow() throws Exception {
        Mockito.when(organizationService.delete(fakeId))
                .thenThrow(notFoundException);

        mockMvc.perform(delete("/organizations/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("url").value("/organizations/" + fakeId))
                .andExpect(jsonPath("message").value(notFoundException.getLocalizedMessage()));
    }

    @Test
     void deleteOrganization_successFlow() throws Exception {
        Mockito.when(organizationService.delete(organizationDto.getId())).thenReturn(true);

        mockMvc.perform(delete("/organizations/" + organizationDto.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
     void findAllOrganizations_successFlow() throws Exception {
        List<OrganizationDto> organizations = new ArrayList<>();
        organizations.add(organizationDto);
        Mockito.when(organizationService.findAll()).thenReturn(organizations);
        mockMvc.perform(get("/organizations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(organizationDto.getName())))
                .andExpect(jsonPath("$[0].address", is(organizationDto.getAddress())));

    }
}

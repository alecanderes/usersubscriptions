package com.user.subscription.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.subscription.dto.UserDto;
import com.user.subscription.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static com.user.subscription.rest.TestData.SUBSCRIPTION_DTO_1;
import static com.user.subscription.rest.TestData.USER_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final String url = "/api/v1/users";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(USER_DTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_DTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(USER_DTO.name()));

        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void getUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(USER_DTO);

        mockMvc.perform(get(url + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(USER_DTO.name()));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(USER_DTO);

        mockMvc.perform(put(url + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_DTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(USER_DTO.name()));

        verify(userService, times(1)).updateUser(eq(1L), any(UserDto.class));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete(url + "/1"))
            .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void addSubscription() throws Exception {
        doNothing().when(userService).addSubscription(eq(1L), eq(1L));

        mockMvc.perform(post(url + "/1/subscriptions")
                .param("subscriptionId", "1"))
            .andExpect(status().isOk());

        verify(userService, times(1)).addSubscription(eq(1L), eq(1L));
    }

    @Test
    void getSubscriptions() throws Exception {
        when(userService.getUserSubscriptions(1L)).thenReturn(Set.of(SUBSCRIPTION_DTO_1));

        mockMvc.perform(get(url + "/1/subscriptions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value(SUBSCRIPTION_DTO_1.name()));

        verify(userService, times(1)).getUserSubscriptions(1L);
    }

    @Test
    void removeSubscription() throws Exception {
        doNothing().when(userService).removeSubscription(eq(1L), eq(1L));

        mockMvc.perform(delete(url + "/1/subscriptions/1")).andExpect(status().isNoContent());

        verify(userService, times(1)).removeSubscription(eq(1L), eq(1L));
    }
}
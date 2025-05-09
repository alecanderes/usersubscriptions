package com.user.subscription.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.service.SubscriptionService;
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
import static com.user.subscription.rest.TestData.SUBSCRIPTION_DTO_2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private final String url = "/api/v1/subscriptions";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createSubscription() throws Exception {
        when(subscriptionService.createSubscription(any(SubscriptionDto.class)))
            .thenReturn(SUBSCRIPTION_DTO_1);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SUBSCRIPTION_DTO_1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(SUBSCRIPTION_DTO_1.id()))
            .andExpect(jsonPath("$.name").value(SUBSCRIPTION_DTO_1.name()));

        verify(subscriptionService, times(1)).createSubscription(any(SubscriptionDto.class));
    }

    @Test
    void getSubscription() throws Exception {
        when(subscriptionService.getSubscription(1L))
            .thenReturn(SUBSCRIPTION_DTO_1);

        mockMvc.perform(get(url + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(SUBSCRIPTION_DTO_1.id()))
            .andExpect(jsonPath("$.name").value(SUBSCRIPTION_DTO_1.name()));

        verify(subscriptionService, times(1)).getSubscription(1L);
    }

    @Test
    void getAllSubscriptions() throws Exception {
        Set<SubscriptionDto> subscriptions = Set.of(SUBSCRIPTION_DTO_1);
        when(subscriptionService.getAllSubscriptions())
            .thenReturn(subscriptions);

        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(SUBSCRIPTION_DTO_1.id()));

        verify(subscriptionService, times(1)).getAllSubscriptions();
    }

    @Test
    void deleteSubscription() throws Exception {
        mockMvc.perform(delete(url + "/1"))
            .andExpect(status().isNoContent());

        verify(subscriptionService).deleteSubscription(1L);
    }

    @Test
    void getTopSubscriptions() throws Exception {
        Set<SubscriptionDto> topSubscriptions = Set.of(SUBSCRIPTION_DTO_1, SUBSCRIPTION_DTO_2);
        when(subscriptionService.getTopSubscriptions())
            .thenReturn(topSubscriptions);

        mockMvc.perform(get(url + "/top"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));

        verify(subscriptionService).getTopSubscriptions();
    }
}
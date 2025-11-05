package com.worch.tests.controller;

import com.worch.controllers.ExpertProfileController;
import com.worch.model.dto.response.ExpertProfileResponse;
import com.worch.service.ExpertProfileService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@DisplayName("Операции с экспертами")
@WebMvcTest(controllers = ExpertProfileController.class,
        excludeAutoConfiguration = {OAuth2ResourceServerAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class})
public class ExpertProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpertProfileService expertProfileService;

    private ExpertProfileResponse response1;

    @BeforeEach
    void setUp() {
        UUID expertId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174011");

        response1 = new ExpertProfileResponse(
                expertId,
                userId,
                "Ivan Ivanov",
                false,
                1500,
                4.7f
        );
    }

    @Test
    @DisplayName("Получение всех экспертов")
    @WithMockUser
    void getExpertProfiles() throws Exception {
        when(expertProfileService.findAll()).thenReturn(List.of(response1));

        String response = mockMvc.perform(get("/api/experts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("JSON RESPONSE");
        System.out.println(response);

        mockMvc.perform(get("/api/experts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(response1.id().toString()))
                .andExpect(jsonPath("$[0].userId").value(response1.userId().toString()))
                .andExpect(jsonPath("$[0].isIncognito").value(false))
                .andExpect(jsonPath("$[0].price").value(1500))
                .andExpect(jsonPath("$[0].rating").value(4.7));
    }

    @Test
    @DisplayName("Получение пустого списка")
    @WithMockUser
        void getAllExperts_WhenNoExperts() throws Exception {
        when(expertProfileService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/experts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
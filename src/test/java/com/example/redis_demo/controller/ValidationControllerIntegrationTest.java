package com.example.redis_demo.controller;

import com.example.redis_demo.config.TestRedisConfiguration;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationControllerIntegrationTest {

    static final String CONTRACT = "AAAA";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllContracts() throws Exception {
        mockMvc.perform(get("/validations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(2)
    void shouldReturnSpecificContract() throws Exception {
        mockMvc.perform(get("/validations/{id}", CONTRACT)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id.contractNumber").value(CONTRACT));
    }

    @Test
    @Order(1)
    void shouldCreateNewContract() throws Exception {
        CreateContractValidationDto contractDto = new CreateContractValidationDto(CONTRACT, 1, "TestCompany", 3600);

        String jsonRequest = objectMapper.writeValueAsString(contractDto);

        mockMvc.perform(post("/validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))  // Отправляем JSON
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.contractNumber").value(CONTRACT))
                .andExpect(jsonPath("$.id.userId").value(1))
                .andExpect(jsonPath("$.company").value("TestCompany"))
                .andExpect(jsonPath("$.ttl").value(3600));
    }
}
package com.example.redis_demo.controller;

import com.example.redis_demo.config.TestRedisConfiguration;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationControllerIntegrationTest {

    static final String CONTRACT = "AAAA";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(3)
    void shouldReturnAllContracts() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/validations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                           "id":{
                              "contractNumber":"AAAA",
                              "userId":1
                           },
                           "company":"TestCompany",
                           "ttl":3600
                        }]
                        """))
                .andDo(document("validation/get_all", preprocessResponse(prettyPrint())));
    }

    @Test
    @Order(2)
    void shouldReturnSpecificContract() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/validations/{id}", CONTRACT)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id.contractNumber").value(CONTRACT))
                .andDo(document("validation/get_by_id", preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id")
                                        .description("Contract number associated with the validation")
                        ),

                        queryParameters(
                                parameterWithName("userId").description("User identifier associated with the validation")
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .description("Validation record combined key 'contractNumber:userId'")
                                        .type(JSObject.class),
                                fieldWithPath("id.contractNumber")
                                        .description("The contract number which need to be validated")
                                        .type(String.class),
                                fieldWithPath("id.userId")
                                        .description("The user id who wants to validate the contract")
                                        .type(Integer.class),
                                fieldWithPath("company")
                                        .description("The company name that issued the contract")
                                        .type(String.class),
                                fieldWithPath("ttl")
                                        .description("How lon the validation record will exist")
                                        .type(Integer.class)
                        )));
    }

    @Test
    @Order(1)
    void shouldCreateNewContract() throws Exception {
        CreateContractValidationDto contractDto = new CreateContractValidationDto(CONTRACT, 1, "TestCompany", 3600);

        String jsonRequest = objectMapper.writeValueAsString(contractDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/validations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))  // Отправляем JSON
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpectAll(
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id.contractNumber").value(CONTRACT),
                        jsonPath("$.id.userId").value(1),
                        jsonPath("$.company").value("TestCompany"),
                        jsonPath("$.ttl").value(3600)
                )
                .andDo(document("validation/create", preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("contractNumber")
                                        .description("Validated contract number")
                                        .type(String.class),
                                fieldWithPath("userId").description("The user id who validated the contract")
                                        .type(Integer.class),
                                fieldWithPath("company")
                                        .description("The company name that issued the contract")
                                        .type(String.class),
                                fieldWithPath("ttl").description("How many time left before the record will be removed")
                                        .type(Integer.class)
                        )
                ));
    }
}
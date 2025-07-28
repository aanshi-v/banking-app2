package com.code.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.code.dto.ApiResponse;
import com.code.service.CreditInterestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CreditInterestControllerTest {

	private MockMvc mockMvc;

    @Mock
    private CreditInterestService creditInterestService;

    @InjectMocks
    private CreditInterestController creditInterestController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    
    @Test
    void testCalculateInterest_Success() throws Exception {
        // Arrange
        mockMvc = MockMvcBuilders.standaloneSetup(creditInterestController).build();

        ApiResponse<String> mockResponse = new ApiResponse<>("SUCCESS",
                "Interest credited to all accounts", 200, null);

        when(creditInterestService.calculateAndCreditInterest()).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/interest/calculate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Interest credited to all accounts"))
                .andExpect(jsonPath("$.code").value(200));

        verify(creditInterestService, times(1)).calculateAndCreditInterest();
    }
    
    
    
    @Test
    void testCalculateInterest_NoAccountsFound() throws Exception {
        // Arrange
        mockMvc = MockMvcBuilders.standaloneSetup(creditInterestController).build();

        ApiResponse<String> mockResponse = new ApiResponse<>("FAILURE",
                "No accounts found", 404, null);

        when(creditInterestService.calculateAndCreditInterest()).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/interest/calculate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // controller always returns 200 but code in JSON says 404
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.message").value("No accounts found"))
                .andExpect(jsonPath("$.code").value(404));

        verify(creditInterestService, times(1)).calculateAndCreditInterest();
    }
    
}



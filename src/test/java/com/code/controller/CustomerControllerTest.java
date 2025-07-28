//package com.code.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.code.dto.ApiResponse;
//import com.code.dto.CustomerResponseDto;
//import com.code.dto.LoginRequestDto;
//import com.code.exception.GlobalExceptionHandler;
//import com.code.service.CustomerService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@ExtendWith(MockitoExtension.class)
//public class CustomerControllerTest {
//	
//	private MockMvc mockMvc;
//
//    @Mock
//    private CustomerService customerService;
//
//    @InjectMocks
//    private CustomerController customerController;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//    
//    
//    @Test
//    void testLogin_Success() throws Exception {
//        //  Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
//
//        LoginRequestDto request = new LoginRequestDto();
//        request.setCustomerId(1001L);
//        request.setPassword("mypassword");
//
//        CustomerResponseDto responseDto = new CustomerResponseDto();
//        responseDto.setCustomerId(1001L);
//        responseDto.setName("John Doe");
//        responseDto.setEmail("john@example.com");
//        responseDto.setPhone("9876543210");
//
//        ApiResponse<CustomerResponseDto> apiResponse =
//                new ApiResponse<>("SUCCESS", "Login successful", 200, responseDto);
//
//        when(customerService.login(any(LoginRequestDto.class))).thenReturn(apiResponse);
//
//        // Act & Assert
//        mockMvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.message").value("Login successful"))
//                .andExpect(jsonPath("$.data.name").value("John Doe"));
//
//        // Verify service was called once
//        verify(customerService, times(1)).login(any(LoginRequestDto.class));
//    }
//
//    @Test
//    void testLogin_Failure_InvalidCredentials() throws Exception {
//        //  Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
//                .setControllerAdvice(new GlobalExceptionHandler()) // if you have one
//                .build();
//
//        LoginRequestDto request = new LoginRequestDto();
//        request.setCustomerId(9999L);
//        request.setPassword("wrongpass");
//
//        when(customerService.login(any(LoginRequestDto.class)))
//                .thenThrow(new RuntimeException("Invalid customer ID or password"));
//
//        // Act & Assert
//        mockMvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isInternalServerError()) // default if not handled by GlobalExceptionHandler
//                .andExpect(jsonPath("$.message").value("Invalid customer ID or password"));
//
//        verify(customerService, times(1)).login(any(LoginRequestDto.class));
//    }
//
//}

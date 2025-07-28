package com.code.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.code.dto.ApiResponse;
import com.code.dto.CustomerResponseDto;
import com.code.dto.LoginRequestDto;
import com.code.entity.Customer;
import com.code.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService; // Service under test

    
    @Test
    void testLogin_Success() {
        // Arrange (Given)
        LoginRequestDto request = new LoginRequestDto();
        request.setCustomerId(1001L);
        request.setPassword("mypassword");

        Customer customer = new Customer();
        customer.setCustomerId(1001L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("9876543210");

        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setCustomerId(1001L);
        responseDto.setName("John Doe");
        responseDto.setEmail("john@example.com");
        responseDto.setPhone("9876543210");

        //  Mock behavior
        when(customerRepository.findByCustomerIdAndPassword(1001L, "mypassword"))
                .thenReturn(Optional.of(customer));

        when(modelMapper.map(customer, CustomerResponseDto.class))
                .thenReturn(responseDto);

        //  Act (When)
        ApiResponse<CustomerResponseDto> response = customerService.login(request);

        // Assert (Then)
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("Login successful", response.getMessage());
        assertEquals(200, response.getCode());
        assertNotNull(response.getData());
        assertEquals("John Doe", response.getData().getName());

        //  Verify repository & mapper were called
        verify(customerRepository, times(1)).findByCustomerIdAndPassword(1001L, "mypassword");
        verify(modelMapper, times(1)).map(customer, CustomerResponseDto.class);
    }

    @Test
    void testLogin_InvalidCredentials() {
        //  Arrange (Given)
        LoginRequestDto request = new LoginRequestDto();
        request.setCustomerId(9999L);
        request.setPassword("wrongpass");

        //  Mock behavior (no customer found)
        when(customerRepository.findByCustomerIdAndPassword(9999L, "wrongpass"))
                .thenReturn(Optional.empty());

        //  Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.login(request);
        });

        assertEquals("Invalid customer ID or password", exception.getMessage());

        //  Verify repository was called
        verify(customerRepository, times(1)).findByCustomerIdAndPassword(9999L, "wrongpass");
        verify(modelMapper, never()).map(any(), eq(CustomerResponseDto.class)); // mapper should not be called
    }

    
}






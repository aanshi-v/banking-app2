package com.code.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.code.dto.ApiResponse;
import com.code.dto.CustomerResponseDto;
import com.code.dto.LoginRequestDto;
import com.code.entity.Customer;
import com.code.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

	 private final CustomerRepository customerRepository;
	 
	 private final ModelMapper modelMapper;
	 
	 public ApiResponse<CustomerResponseDto> login(LoginRequestDto request) {
		 
		 log.info("Login attempt for customerId: {}", request.getCustomerId());

	        Optional<Customer> customerOpt =
	                customerRepository.findByCustomerIdAndPassword(request.getCustomerId(), request.getPassword());

	        if (!customerOpt.isPresent()) {
	            throw new RuntimeException("Invalid customer ID or password");
	        }

	        CustomerResponseDto responseDto = modelMapper.map(customerOpt.get(), CustomerResponseDto.class);

	        return new ApiResponse<>("SUCCESS", "Login successful", 200, responseDto);
	    }
	 
}

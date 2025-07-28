package com.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.ApiResponse;
import com.code.dto.CustomerResponseDto;
import com.code.dto.LoginRequestDto;
import com.code.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(customerService.login(request));
    }
}





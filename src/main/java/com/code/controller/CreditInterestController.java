package com.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.ApiResponse;
import com.code.service.CreditInterestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
public class CreditInterestController {
	
	 private final CreditInterestService creditInterestService;

	    @PostMapping("/calculate")
	    public ResponseEntity<ApiResponse<String>> calculateInterest() {
	        return ResponseEntity.ok(creditInterestService.calculateAndCreditInterest());
	    }

}

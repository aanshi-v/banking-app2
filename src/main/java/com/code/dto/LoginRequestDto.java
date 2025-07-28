package com.code.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

	private Long customerId;
    private String password;
}

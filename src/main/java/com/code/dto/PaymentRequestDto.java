package com.code.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentRequestDto {
	
	 private Long senderAccountId;
	    private String receiverAccountNumber;
	    private BigDecimal amount;
	    private String remarks;
	    private String paymentMode;

}

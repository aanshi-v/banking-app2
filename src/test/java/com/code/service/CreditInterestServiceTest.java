package com.code.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.code.dto.ApiResponse;
import com.code.entity.Account;
import com.code.entity.CreditInterest;
import com.code.repository.AccountRepository;
import com.code.repository.CreditInterestRepository;

@ExtendWith(MockitoExtension.class)
public class CreditInterestServiceTest {

	@Mock
    private AccountRepository accountRepository;

    @Mock
    private CreditInterestRepository creditInterestRepository;

    @InjectMocks
    private CreditInterestService creditInterestService;
    
    
    @Test
    void testCalculateAndCreditInterest_Success() {
        // Arrange
        Account account1 = new Account();
        account1.setAccountId(101L);
        account1.setBalance(new BigDecimal("1000.00"));

        Account account2 = new Account();
        account2.setAccountId(102L);
        account2.setBalance(new BigDecimal("2000.00"));

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        //  Act
        ApiResponse<String> response = creditInterestService.calculateAndCreditInterest();

        //  Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("Interest credited to all accounts", response.getMessage());
        assertEquals(200, response.getCode());

        //  Verify
        verify(accountRepository, times(1)).findAll();
        verify(accountRepository, times(2)).save(any(Account.class)); // called for both accounts
        verify(creditInterestRepository, times(2)).save(any(CreditInterest.class)); // interest recorded for both
    }
    
    
    
    @Test
    void testCalculateAndCreditInterest_NoAccounts() {
        //  Arrange
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        //  Act
        ApiResponse<String> response = creditInterestService.calculateAndCreditInterest();

        //  Assert
        assertNotNull(response);
        assertEquals("FAILURE", response.getStatus());
        assertEquals("No accounts found", response.getMessage());
        assertEquals(404, response.getCode());

        //  Verify
        verify(accountRepository, times(1)).findAll();
        verify(accountRepository, never()).save(any(Account.class));
        verify(creditInterestRepository, never()).save(any(CreditInterest.class));
    }

}







package com.code.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.code.dto.ApiResponse;
import com.code.entity.Account;
import com.code.entity.CreditInterest;
import com.code.repository.AccountRepository;
import com.code.repository.CreditInterestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Daily Credit Interest = Closing Balance × Interest Rate (3%) ÷ 365
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditInterestService {

	private final AccountRepository accountRepository;
    private final CreditInterestRepository creditInterestRepository;

    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.03"); // 3%

    /**
     * Calculate and credit interest for all accounts.
     */
    public ApiResponse<String> calculateAndCreditInterest() {
    	  log.info("Starting interest calculation for all accounts...");
        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            return new ApiResponse<>("FAILURE", "No accounts found", 404, null);
        }

        for (Account account : accounts) {
            // Daily Interest = Balance × Rate ÷ 365
            BigDecimal dailyInterest = account.getBalance()
                    .multiply(INTEREST_RATE)
                    .divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);

            // Credit interest to account
            account.setBalance(account.getBalance().add(dailyInterest));
            accountRepository.save(account);

            // Save transaction to CreditInterest table
            CreditInterest creditInterest = new CreditInterest();
            creditInterest.setAccount(account);
            creditInterest.setInterestAmount(dailyInterest);
            creditInterest.setInterestDate(LocalDate.now());

            creditInterestRepository.save(creditInterest);
        }
        
        log.info("Interest calculation and crediting completed successfully for {} accounts.", accounts.size());

        return new ApiResponse<>("SUCCESS", "Interest credited to all accounts", 200, null);
    }

}

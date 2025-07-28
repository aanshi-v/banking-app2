package com.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.entity.CreditInterest;

@Repository
public interface CreditInterestRepository extends JpaRepository<CreditInterest, Long> {

}

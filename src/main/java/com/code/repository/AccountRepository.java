package com.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}

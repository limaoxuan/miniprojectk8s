package com.max.account.dao;

import com.max.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findAccountByEmailAndPassword(String email, String pwd);
}

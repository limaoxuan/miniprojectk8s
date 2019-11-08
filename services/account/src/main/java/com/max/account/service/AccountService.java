package com.max.account.service;

import com.max.account.dao.AccountDao;
import com.max.account.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AccountService {
    void save(Account account);

    Account login(Account account);
}

package com.max.account.service.impl;

import com.max.account.dao.AccountDao;
import com.max.account.domain.Account;
import com.max.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public Account login(Account account) {
        return accountDao.findAccountByEmailAndPassword(account.getEmail(), account.getPassword());
    }
}

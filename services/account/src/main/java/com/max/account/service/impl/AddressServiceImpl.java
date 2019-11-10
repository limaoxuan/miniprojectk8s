package com.max.account.service.impl;

import com.max.account.dao.AccountDao;
import com.max.account.dao.AddressDao;
import com.max.account.domain.Address;
import com.max.account.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public void save(Address address) {
        addressDao.save(address);
    }
}

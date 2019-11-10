package com.max.account.dao;

import com.max.account.domain.Account;
import com.max.account.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

}

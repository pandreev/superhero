package com.payworksmobile.dao;

import com.payworksmobile.model.Account;

public interface AccountDao {

    Account  findByName(String name);
}

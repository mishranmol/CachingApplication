package com.Cache.cachingApp.services;

import com.Cache.cachingApp.entities.Employee;
import com.Cache.cachingApp.entities.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}

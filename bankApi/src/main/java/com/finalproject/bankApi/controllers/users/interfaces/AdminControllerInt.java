package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.Admin;

import java.math.BigDecimal;

public interface AdminControllerInt {
    Admin addNewAdmin(Admin admin);
    Account addNewAccount(Account account);
    Account updateAccount(Account account);
    Account updateAccountBalance(BigDecimal balance);
    void deleteAccount(Account account);

}

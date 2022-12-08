package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.Admin;

public interface AccountHolderControllerInt {
    
    Account findAccountById(Long id);

}

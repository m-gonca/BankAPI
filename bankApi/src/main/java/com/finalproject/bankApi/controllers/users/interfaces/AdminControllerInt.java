package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.*;

import java.math.BigDecimal;

public interface AdminControllerInt {
    //User creation
    Admin addNewAdmin(Admin admin);
    Account addNewAccountHolder(AccountHolder accountHolder);
    void deleteAccountHolder(AccountHolder accountHolder);
    Account addNewThirdParty(ThirdParty thirdParty);
    void deleteThirdParty(ThirdParty thirdParty);
    
    //Account management
    Account addNewCheckingAccount(Account account);
    Account addNewSavingsAccount(Account account);
    Account addNewCreditCardAccount(Account account);
    Account updateAccount(Account account);
    Account updateAccountBalance(BigDecimal balance);
    void deleteAccount(Account account);

}

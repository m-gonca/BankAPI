package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.dtos.AccountDTO;
import com.finalproject.bankApi.models.dtos.BalanceDTO;
import com.finalproject.bankApi.models.users.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

public interface AdminControllerInt {

    Admin addNewAdmin(Admin admin);
    AccountHolder addNewAccountHolder(AccountHolder accountHolder);
    ThirdParty addNewThirdParty(ThirdParty thirdParty);
   // void deleteAccountHolder(AccountHolder accountHolder);
   // void deleteThirdParty(ThirdParty thirdParty);
    
    //Account management
    Account addNewCheckingAccount(AccountDTO accountDTO);
    Account addNewSavingsAccount(AccountDTO accountDTO);
    Account addNewCreditCardAccount(AccountDTO accountDTO);
    BigDecimal findAccountBalance(Long id);
    Account findAccountById(Long id);
    Account updateAccountBalanceById(BalanceDTO balanceDTO);
    void deleteAccount(Long id);

}

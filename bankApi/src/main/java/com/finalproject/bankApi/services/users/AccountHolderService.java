package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.repositories.accounts.AccountRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;

    public AccountHolder addAccountHolder(AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

    public Account getAccount(Long id){
       if(accountHolderRepository.findPrimaryOwnerAccountsById(id).isPresent()){
           return accountHolderRepository.findPrimaryOwnerAccountsById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
       }
       else
           return accountHolderRepository.findSecondaryOwnerAccountsById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }
    
    
}

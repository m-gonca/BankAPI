package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/account-holder")
public class AccountHolderController {
    
    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable Long id) {
        return accountHolderService.getAccount(id);
    }
    
    
}

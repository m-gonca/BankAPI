package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.controllers.users.interfaces.AccountHolderControllerInt;
import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.transferences.Transference;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.security.CustomUserDetails;
import com.finalproject.bankApi.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account-holder")
public class AccountHolderController implements AccountHolderControllerInt {
    
    @Autowired
    AccountHolderService accountHolderService;
    //Before Security
    /*  @GetMapping("/{accountId}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable Long accountId, @PathVariable Long ownerId) {
        return accountHolderService.getAccount( accountId, ownerId);
    }

    @GetMapping("/balance/{accountId}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal findAccountBalance(@PathVariable Long accountId, @PathVariable Long ownerId) {
        return accountHolderService.getAccountBalance(accountId, ownerId);
    }*/
    
    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable Long accountId, @AuthenticationPrincipal CustomUserDetails user) {
        return accountHolderService.getAccount(accountId, user);
    }
    @GetMapping("/balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal findAccountBalance(@PathVariable Long accountId, @AuthenticationPrincipal CustomUserDetails user) {
        return accountHolderService.getAccountBalance(accountId, user);
    }
    
    @PostMapping("/transfer-money")
    @ResponseStatus(HttpStatus.CREATED)
    public Transference makeTransference(@RequestBody TransferenceDTO transferenceDTO, @AuthenticationPrincipal UserDetails user){
     return accountHolderService.addTransference(transferenceDTO, user);   
    }
    
}

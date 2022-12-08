package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.controllers.users.interfaces.AccountHolderControllerInt;
import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.actions.Transference;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account-holder")
public class AccountHolderController implements AccountHolderControllerInt {
    
    @Autowired
    AccountHolderService accountHolderService;
    
    @GetMapping("/{accountId}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable Long accountId, Long ownerId) {
        return accountHolderService.getAccount( accountId, ownerId);
    }

    @GetMapping("/balance/{accountId}/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal findAccountBalance(@PathVariable Long accountId, Long ownerId) {
        return accountHolderService.getAccountBalance(accountId, ownerId);
    }
        
    @PostMapping("/transfer-money")
    @ResponseStatus(HttpStatus.CREATED)
    public Transference makeTransference(@RequestBody TransferenceDTO transferenceDTO){
     return accountHolderService.addTransference(transferenceDTO);   
    }
    
}

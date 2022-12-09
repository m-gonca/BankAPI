package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.controllers.users.interfaces.AdminControllerInt;
import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.accounts.CheckingAccount;
import com.finalproject.bankApi.models.dtos.AccountDTO;
import com.finalproject.bankApi.models.dtos.BalanceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.ThirdParty;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.services.users.AccountHolderService;
import com.finalproject.bankApi.services.users.AdminService;
import com.finalproject.bankApi.services.users.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/admin")
public class AdminController implements AdminControllerInt {
    

    @Autowired
    AdminService adminService;
    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/add-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addNewAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    @PostMapping("/add-account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addNewAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderService.addAccountHolder(accountHolder);
    }
    @PostMapping("/add-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addNewThirdParty(@RequestBody ThirdParty thirdParty){
        return thirdPartyService.addThirdParty(thirdParty);
    }
    
    @PostMapping("/add-checking-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewCheckingAccount(@RequestBody AccountDTO accountDTO){
        return adminService.addCheckingAccount(accountDTO);
    }

    @PostMapping("/add-savings-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewSavingsAccount(@RequestBody AccountDTO accountDTO){
        return adminService.addSavingsAccount(accountDTO);
    }
    @PostMapping("/add-credit-card-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewCreditCardAccount(@RequestBody AccountDTO accountDTO){
        return adminService.addCreditCardAccount(accountDTO);
    }
    
    @GetMapping("/client-account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable Long id) {
        return adminService.getAccount(id);
    }
    @GetMapping("/client-account/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal findAccountBalance(@PathVariable Long id) {
        return adminService.getAccountBalance(id);
    }
    
    @PatchMapping("/client-account/update-balance")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccountBalanceById(@RequestBody BalanceDTO balanceDTO){
        return adminService.updateAccountBalance(balanceDTO);
    }
    
    @DeleteMapping("/delete-account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id){
        adminService.deleteAccount(id);
    }
    
    
}

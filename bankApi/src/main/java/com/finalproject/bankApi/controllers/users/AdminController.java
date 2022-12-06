package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.accounts.CheckingAccount;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.repositories.accounts.CheckingAccountRepository;
import com.finalproject.bankApi.repositories.accounts.CreditCardAccountRepository;
import com.finalproject.bankApi.repositories.accounts.SavingsAccountRepository;
import com.finalproject.bankApi.repositories.accounts.StudentAccountRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.services.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    

    @Autowired
    AdminService adminService;
    
    
    @PostMapping("/add-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addNewAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    
    @PostMapping("/add-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addNewAccount(@RequestBody Account account){
        return adminService.addAccount(account);
    }


}

package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.accounts.CheckingAccount;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.repositories.accounts.CheckingAccountRepository;
import com.finalproject.bankApi.repositories.accounts.CreditCardAccountRepository;
import com.finalproject.bankApi.repositories.accounts.SavingsAccountRepository;
import com.finalproject.bankApi.repositories.accounts.StudentAccountRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AdminService {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;
    @Autowired
    AdminRepository adminRepository;

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    
    public Account addAccount(Account account) {
  

        LocalDate birthdate = account.getPrimaryOwner().getBirthDate();
        LocalDate now = LocalDate.now();
        Period period = birthdate. until(now);
        int age = period. getYears();

        if(age <= 24){ return studentAccountRepository.save(account);}
        else{ return checkingAccountRepository.save(account);}

    }
}

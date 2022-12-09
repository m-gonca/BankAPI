package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.accounts.*;
import com.finalproject.bankApi.models.dtos.AccountDTO;
import com.finalproject.bankApi.models.dtos.BalanceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.Role;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.repositories.users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    String encodedPassword;
    Role role;
    Role role2;
    Role role3;

    public Admin addAdmin(Admin admin) {
        encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        admin = adminRepository.save(admin);
        role = roleRepository.save(new Role("ADMIN", admin));
        role2 = roleRepository.save(new Role("ACCOUNT_HOLDER", admin));
        role3 = roleRepository.save(new Role("THIRD_PARTY", admin));
        return admin;
    }

    public Account addCheckingAccount(AccountDTO accountDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
        LocalDate birthdate = primaryOwner.getBirthDate();
        LocalDate now = LocalDate.now();
        Period period = birthdate.until(now);
        int age = period.getYears();

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
        }
        Account newAccount;
        if (age <= 24) {
            newAccount = studentAccountRepository.save(new StudentAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey()));
        } else {
            newAccount = checkingAccountRepository.save(new CheckingAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey()));
        }
        return newAccount;

    }

    public Account addSavingsAccount(AccountDTO accountDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
        }

        SavingsAccount savingsAccount;
        if (accountDTO.getMinBalance() != null && accountDTO.getInterestRate() != null) {
            savingsAccount = new SavingsAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey(), accountDTO.getMinBalance(), accountDTO.getInterestRate());
        } else if (accountDTO.getMinBalance() != null) {
            savingsAccount = new SavingsAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey(), accountDTO.getMinBalance(), new BigDecimal("0.0025"));
        } else if (accountDTO.getInterestRate() != null) {
            savingsAccount = new SavingsAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey(), new BigDecimal("1000"), accountDTO.getInterestRate());
        } else {
            savingsAccount = new SavingsAccount(primaryOwner, secondaryOwner, accountDTO.getSecretKey());
        }
        return savingsAccountRepository.save(savingsAccount);
    }

    public Account addCreditCardAccount(AccountDTO accountDTO) {

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
        }

        CreditCardAccount creditCardAccount;
        if (accountDTO.getCreditLimit() != null && accountDTO.getInterestRate() != null) {
            creditCardAccount = new CreditCardAccount(primaryOwner, secondaryOwner, accountDTO.getCreditLimit(), accountDTO.getInterestRate());
        } else if (accountDTO.getCreditLimit() != null) {
            creditCardAccount = new CreditCardAccount(primaryOwner, secondaryOwner, accountDTO.getCreditLimit(), new BigDecimal("0.20"));
        } else if (accountDTO.getInterestRate() != null) {
            creditCardAccount = new CreditCardAccount(primaryOwner, secondaryOwner, new BigDecimal("100"), accountDTO.getInterestRate());
        } else {
            creditCardAccount = new CreditCardAccount(primaryOwner, secondaryOwner);
        }
        return creditCardAccountRepository.save(creditCardAccount);
    }
    
    public Account getAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if(account instanceof SavingsAccount){
            savingsAccountRepository.findById(id).get().checkInterests();
        }
        else if( account instanceof CreditCardAccount ){
            creditCardAccountRepository.findById(id).get().checkInterests();
        }
        return account;
    }
    
    public BigDecimal getAccountBalance(Long id){
        Account account = accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if(account instanceof SavingsAccount){
            savingsAccountRepository.findById(id).get().checkInterests();
        }
        else if( account instanceof CreditCardAccount ){
            creditCardAccountRepository.findById(id).get().checkInterests();
        }
        return account.getBalance();
    }

    public Account updateAccountBalance(BalanceDTO balanceDTO) {
        Account account = accountRepository.findById(balanceDTO.getAccountId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if(account instanceof SavingsAccount){
            savingsAccountRepository.findById(balanceDTO.getAccountId()).get().checkInterests();
        }
        else if( account instanceof CreditCardAccount ){
            creditCardAccountRepository.findById(balanceDTO.getAccountId()).get().checkInterests();
        }
        BigDecimal newBalance = balanceDTO.getNewBalance();
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }
    
    public void deleteAccount(Long id){
        accountRepository.delete(accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")));
    }
    
}

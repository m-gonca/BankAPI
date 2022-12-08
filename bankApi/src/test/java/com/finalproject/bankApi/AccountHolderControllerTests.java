package com.finalproject.bankApi;

import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class AccountHolderControllerTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;
}

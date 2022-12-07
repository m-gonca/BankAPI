package com.finalproject.bankApi;

import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.*;
import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import com.finalproject.bankApi.models.actions.Transference;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.ThirdParty;
import com.finalproject.bankApi.repositories.users.UserRepository;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.transferences.ThirdPartyTransferenceRepository;
import com.finalproject.bankApi.repositories.transferences.TransferenceRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.repositories.users.ThirdPartyRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RepositoriesTests {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    ThirdPartyTransferenceRepository thirdPartyTransferenceRepository;
    @Autowired
    TransferenceRepository transferenceRepository;

    AccountHolder accountHolder1;
    AccountHolder accountHolder2;

    Account account1;
    Account account2;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        accountHolder1 = accountHolderRepository.save(new AccountHolder("Luis", "123456", LocalDate.of(1982, 5, 20), new Address("Calle 1", 5555L, "Zaragoza", "Spain"), new Address("Calle 2", 7777L, "Valencia", "Spain")));
        accountHolder2 = accountHolderRepository.save(new AccountHolder("Maria", "123456", LocalDate.of(1952, 5, 19), new Address("Calle 3", 5555L, "Toledo", "Spain"), new Address("Calle 4", 7777L, "Madrid", "Spain")));

        account1 = accountRepository.save(new CheckingAccount(accountHolder1, accountHolder2, "123456"));
        account2 = accountRepository.save(new CheckingAccount(accountHolder1, null, "123456"));
    }

    @AfterEach
    void teardown() {
        thirdPartyTransferenceRepository.deleteAll();
        transferenceRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldAddNewAdmin_OK() {
        adminRepository.save(new Admin("Ana", "123456"));
        adminRepository.save(new Admin("Lucia", "123456"));
        assertEquals(2, adminRepository.findAll().size());
    }

    @Test
    void shouldAddNewAccountHolder_OK() {
        assertEquals(2, accountHolderRepository.findAll().size());
    }

    @Test
    void shouldAddNewThirdParty_OK() {
        thirdPartyRepository.save(new ThirdParty("Cafeteria 1"));
        thirdPartyRepository.save(new ThirdParty("Restaurante"));
        assertEquals(2, thirdPartyRepository.findAll().size());
    }

    @Test
    void shouldAddNewCheckingAccount_OK() {
        assertEquals(2, checkingAccountRepository.findAll().size());
    }

    @Test
    void shouldAddNewStudentAccount_OK() {
        accountRepository.save(new StudentAccount(accountHolder1, accountHolder1, "123456"));
        accountRepository.save(new StudentAccount(accountHolder1, null, "123456"));
        assertEquals(2, studentAccountRepository.findAll().size());
    }

    @Test
    void shouldAddNewSavingsAccount_OK() {
        accountRepository.save(new SavingsAccount(accountHolder1, accountHolder2, "123456"));
        accountRepository.save(new SavingsAccount(accountHolder1, accountHolder2, "123456", new BigDecimal(500), new BigDecimal(0.2)));
        assertEquals(2, savingsAccountRepository.findAll().size());
    }

    @Test
    void shouldAddNewSavingsAccount_FAIL() {
        assertThrows(ConstraintViolationException.class, () -> accountRepository.save(new SavingsAccount(accountHolder1, accountHolder2, "123456", new BigDecimal(500), new BigDecimal(0.8))));
        assertThrows(ConstraintViolationException.class, () -> accountRepository.save(new SavingsAccount(accountHolder1, accountHolder2, "123456", new BigDecimal(10), new BigDecimal(0.2))));
    }

    @Test
    void shouldAddNewCreditCardAccount() {
        accountRepository.save(new CreditCardAccount(accountHolder1, accountHolder2));
        accountRepository.save(new CreditCardAccount(accountHolder1, null, new BigDecimal(300), new BigDecimal(0.15)));
        assertEquals(2, creditCardAccountRepository.findAll().size());
    }

    @Test
    void shouldAddNewCreditCardAccount_FAIL() {
        assertThrows(ConstraintViolationException.class, () -> accountRepository.save(new CreditCardAccount(accountHolder1, accountHolder2, new BigDecimal( 500000), new BigDecimal(0.15))));
        assertThrows(ConstraintViolationException.class, () -> accountRepository.save(new CreditCardAccount(accountHolder1, accountHolder2, new BigDecimal(300), new BigDecimal(0.05))));
    }

    @Test
    void shouldAddNewTransference() {
        Account sendingAccount = accountRepository.findById(1L).get();
        Account receivingAccount = accountRepository.findById(2L).get();
        transferenceRepository.save(new Transference(new BigDecimal(1000), sendingAccount, receivingAccount));
        assertEquals(1, transferenceRepository.findAll().size());
    }

    @Test
    void shouldAddNewThirdPartyTransference() {
        ThirdParty thirdParty = thirdPartyRepository.save(new ThirdParty("Cafeteria"));
        Account account = checkingAccountRepository.findById(1L).get();
      /*  Account sendingAccount;
        List<Account> ownerAccounts = accountHolder1.getPrimaryOwnerAccounts();
        for(Account account : ownerAccounts){
            if(account.getId() == 1)
                sendingAccount = account;
        }*/
        thirdPartyTransferenceRepository.save(new ThirdPartyTransference(new BigDecimal(1000), account, thirdParty));
        assertEquals(1, thirdPartyTransferenceRepository.findAll().size());
    }


}

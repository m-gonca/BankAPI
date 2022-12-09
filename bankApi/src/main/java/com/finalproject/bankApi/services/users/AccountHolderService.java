package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.accounts.CheckingAccount;
import com.finalproject.bankApi.models.accounts.CreditCardAccount;
import com.finalproject.bankApi.models.accounts.SavingsAccount;
import com.finalproject.bankApi.models.transferences.Transference;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.Role;
import com.finalproject.bankApi.repositories.accounts.AccountRepository;
import com.finalproject.bankApi.repositories.accounts.CreditCardAccountRepository;
import com.finalproject.bankApi.repositories.accounts.SavingsAccountRepository;
import com.finalproject.bankApi.repositories.transferences.TransferenceRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.RoleRepository;
import com.finalproject.bankApi.repositories.users.UserRepository;
import com.finalproject.bankApi.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;


@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferenceRepository transferenceRepository;
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;
    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    String encodedPassword;
    Role role;
    @Autowired
    private UserRepository userRepository;

    public AccountHolder addAccountHolder(AccountHolder accountHolder) {
        encodedPassword = passwordEncoder.encode(accountHolder.getPassword());
        accountHolder.setPassword(encodedPassword);
        accountHolder = accountHolderRepository.save(accountHolder);
        role = roleRepository.save(new Role("ACCOUNT_HOLDER", accountHolder));
        return accountHolder;
    }

    public Account getAccount(Long accountId, CustomUserDetails user) {
       // Long ownerId = user.getUser().getId();
        Long ownerId = userRepository.findByName(user.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found")).getId();
        Account account;
        //First look if the account is coming from a primary owner
        if (accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).isPresent()) {
            account = accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).get();
            //Check interests in case its a savings or credit card account
            if (account instanceof SavingsAccount) {
                savingsAccountRepository.findById(accountId).get().checkInterests();
            } else if (account instanceof CreditCardAccount) {
                creditCardAccountRepository.findById(accountId).get().checkInterests();
            }
            return accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).get();
        }
        //Second check if the account is coming from a secondary owner
        else if (accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).isPresent()) {
            account = accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).get();
            //Check interests in case its a savings or credit card account
            if (account instanceof SavingsAccount) {
                savingsAccountRepository.findById(accountId).get().checkInterests();
            } else if (account instanceof CreditCardAccount) {
                creditCardAccountRepository.findById(accountId).get().checkInterests();
            }
            return accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found in Account Holder's Accounts");
        }
    }


    public BigDecimal getAccountBalance(Long accountId, CustomUserDetails user) {
      //  Long ownerId = user.getUser().getId();
        Long ownerId = userRepository.findByName(user.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found")).getId();
        Account account;
        //First look if the account is coming from a primary owner
        if (accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).isPresent()) {
            account = accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).get();
            //Check interests in case its a savings or credit card account
            if (account instanceof SavingsAccount) {
                savingsAccountRepository.findById(accountId).get().checkInterests();
            } else if (account instanceof CreditCardAccount) {
                creditCardAccountRepository.findById(accountId).get().checkInterests();
            }
            return accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).get().getBalance();
           }
        //Second check if the account is coming from a secondary owner
        else if (accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).isPresent()) {
            account = accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).get();
            //Check interests in case its a savings or credit card account
            if (account instanceof SavingsAccount) {
                savingsAccountRepository.findById(accountId).get().checkInterests();
            } else if (account instanceof CreditCardAccount) {
                creditCardAccountRepository.findById(accountId).get().checkInterests();
            }
            return accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).get().getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found in Account Holder's Accounts");
        }
    }

    public Transference addTransference(TransferenceDTO transferenceDTO, UserDetails user) {

        //First find the receiving account
        Account receiveAccount = accountRepository.findById(transferenceDTO.getReceiveAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiving Account not found"));

       /* //Second find the account holder of the sending account ----  AFTER SECURITY IMPLEMENTATION WE GET IT FROM USER DETAILS
        AccountHolder owner = accountHolderRepository.findByName(transferenceDTO.getOwnerName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found"));*/

        //Third check if the owner is primary or secondary owner of that account and make transference
        Transference transference;
        BigDecimal balance;
        Account sendAccount;
        Long accountId = transferenceDTO.getSendAccountId();
        Long ownerId = userRepository.findByName(user.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found")).getId();
        if (accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).isPresent()) {
            sendAccount = accountRepository.findByIdAndPrimaryOwnerId(accountId, ownerId).get();
            balance = sendAccount.getBalance();
            //Check if penalty fee has to be applied
            if (sendAccount instanceof CheckingAccount) {
                if (balance.compareTo(((CheckingAccount) sendAccount).getMinBalance()) < 0) {
                    sendAccount.setBalance(sendAccount.getBalance().subtract(sendAccount.getPenaltyFee()));
                    accountRepository.save(sendAccount);
                }
            }
            if (sendAccount instanceof SavingsAccount){
                if (balance.compareTo(((SavingsAccount) sendAccount).getMinBalance()) < 0) {
                    sendAccount.setBalance(sendAccount.getBalance().subtract(sendAccount.getPenaltyFee()));
                    accountRepository.save(sendAccount);
                }
            }
            //Making the transference logic
            if (balance.compareTo(transferenceDTO.getAmount()) == 1) {
                sendAccount.setBalance(sendAccount.getBalance().subtract(transferenceDTO.getAmount()));
                receiveAccount.setBalance(receiveAccount.getBalance().add(transferenceDTO.getAmount()));
                accountRepository.save(sendAccount);
                accountRepository.save(receiveAccount);
                transference = new Transference(transferenceDTO.getAmount(), sendAccount, receiveAccount);
                return transferenceRepository.save(transference);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough funds");
            }
        } else if (accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).isPresent()) {
            sendAccount = accountRepository.findByIdAndSecondaryOwnerId(accountId, ownerId).get();
            balance = sendAccount.getBalance();
            //Check if penalty fee has to be applied
            if (sendAccount instanceof CheckingAccount) {
                if (balance.compareTo(((CheckingAccount) sendAccount).getMinBalance()) < 0) {
                    sendAccount.setBalance(sendAccount.getBalance().subtract(sendAccount.getPenaltyFee()));
                    accountRepository.save(sendAccount);
                }
            }
            if (sendAccount instanceof SavingsAccount){
                if (balance.compareTo(((SavingsAccount) sendAccount).getMinBalance()) < 0) {
                    sendAccount.setBalance(sendAccount.getBalance().subtract(sendAccount.getPenaltyFee()));
                    accountRepository.save(sendAccount);
                }
            }
            //Making the transference logic
            if (balance.compareTo(transferenceDTO.getAmount()) == 1) {
                sendAccount.setBalance(sendAccount.getBalance().subtract(transferenceDTO.getAmount()));
                receiveAccount.setBalance(receiveAccount.getBalance().add(transferenceDTO.getAmount()));
                accountRepository.save(sendAccount);
                accountRepository.save(receiveAccount);
                transference = new Transference(transferenceDTO.getAmount(), sendAccount, receiveAccount);
                return transferenceRepository.save(transference);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough funds");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sending Account not found");
        }
    }
}
        

    
    
    


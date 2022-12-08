package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.accounts.CheckingAccount;
import com.finalproject.bankApi.models.accounts.SavingsAccount;
import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import com.finalproject.bankApi.models.actions.Transference;
import com.finalproject.bankApi.models.dtos.ThirdPartyTransferenceDTO;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.ThirdParty;
import com.finalproject.bankApi.repositories.accounts.AccountRepository;
import com.finalproject.bankApi.repositories.transferences.ThirdPartyTransferenceRepository;
import com.finalproject.bankApi.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    ThirdPartyTransferenceRepository thirdPartyTransferenceRepository;
    @Autowired
    AccountRepository accountRepository;

    public ThirdParty addThirdParty(ThirdParty thirdParty) {
        return thirdPartyRepository.save(thirdParty);
    }


    public ThirdPartyTransference addTransference(String thirdPartyKey, ThirdPartyTransferenceDTO transferenceDTO) {
        Account account;
        //Find the account
        account = accountRepository.findByIdAndSecretKey(transferenceDTO.getAccountId(), transferenceDTO.getSecretKey()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        //Find third Party
        ThirdParty thirdParty = thirdPartyRepository.findByPassword(thirdPartyKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ThirdParty not found"));
        if (transferenceDTO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            //ThirdParty is sending money to account
            //Check if penalty fee has to be applied
            if (account instanceof CheckingAccount) {
                if (account.getBalance().compareTo(((CheckingAccount) account).getMinBalance()) < 0) {
                    account.setBalance(account.getBalance().subtract(account.getPenaltyFee()));
                    accountRepository.save(account);
                }
            }
            if (account instanceof SavingsAccount) {
                if (account.getBalance().compareTo(((SavingsAccount) account).getMinBalance()) < 0) {
                    account.setBalance(account.getBalance().subtract(account.getPenaltyFee()));
                    accountRepository.save(account);
                }
            }
            account.setBalance(account.getBalance().add(transferenceDTO.getAmount()));
            accountRepository.save(account);
            return thirdPartyTransferenceRepository.save(new ThirdPartyTransference(transferenceDTO.getAmount(), account, thirdParty));

        } else if (transferenceDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            //ThirdParty is asking money to account
            if (account instanceof CheckingAccount) {
                if (account.getBalance().compareTo(((CheckingAccount) account).getMinBalance()) < 0) {
                    account.setBalance(account.getBalance().subtract(account.getPenaltyFee()));
                    accountRepository.save(account);
                }
            }
            if (account instanceof SavingsAccount) {
                if (account.getBalance().compareTo(((SavingsAccount) account).getMinBalance()) < 0) {
                    account.setBalance(account.getBalance().subtract(account.getPenaltyFee()));
                    accountRepository.save(account);
                }
            }
            account.setBalance(account.getBalance().subtract(transferenceDTO.getAmount()));
            accountRepository.save(account);
            return thirdPartyTransferenceRepository.save(new ThirdPartyTransference(transferenceDTO.getAmount(), account, thirdParty));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No amount specified");
        }


    }
}

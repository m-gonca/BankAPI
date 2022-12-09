package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.transferences.Transference;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

public interface AccountHolderControllerInt {
    
    Account findAccountById(Long ownerId, CustomUserDetails user);
    BigDecimal findAccountBalance(Long ownerId, CustomUserDetails user);
    Transference makeTransference(TransferenceDTO transferenceDTO, UserDetails user);

}

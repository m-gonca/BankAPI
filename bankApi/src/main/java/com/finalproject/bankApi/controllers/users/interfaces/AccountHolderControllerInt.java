package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.actions.Transference;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

public interface AccountHolderControllerInt {
    
    Account findAccountById(Long ownerId, Long accountId);
    BigDecimal findAccountBalance(Long ownerId, Long accountId);
    Transference makeTransference(TransferenceDTO transferenceDTO);

}

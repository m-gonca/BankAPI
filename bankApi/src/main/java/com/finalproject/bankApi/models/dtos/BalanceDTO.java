package com.finalproject.bankApi.models.dtos;

import java.math.BigDecimal;

public class BalanceDTO {
    private Long accountId;
    private BigDecimal newBalance;

    public BalanceDTO(Long accountId, BigDecimal newBalance) {
        this.accountId = accountId;
        this.newBalance = newBalance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }
}

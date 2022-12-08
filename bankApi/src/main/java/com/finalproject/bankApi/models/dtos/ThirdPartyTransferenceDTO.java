package com.finalproject.bankApi.models.dtos;

import java.math.BigDecimal;

public class ThirdPartyTransferenceDTO {
    private BigDecimal amount;
    private Long accountId;
    private String secretKey;

    public ThirdPartyTransferenceDTO(BigDecimal amount, Long accountId, String secretKey) {
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

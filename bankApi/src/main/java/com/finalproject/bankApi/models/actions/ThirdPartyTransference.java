package com.finalproject.bankApi.models.actions;

import com.finalproject.bankApi.models.accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class ThirdPartyTransference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long accountId;
    @NotNull
    @NotEmpty
    private String accountSecretKey;
    @NotNull
    @NotEmpty
    private String hashKey;

    public ThirdPartyTransference() {
    }

    public ThirdPartyTransference(BigDecimal amount, Long accountId, String accountSecretKey, String hashKey) {
        setAmount(amount);
        setAccountId(accountId);
        setAccountSecretKey(accountSecretKey);
        setHashKey(hashKey);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccountSecretKey() {
        return accountSecretKey;
    }

    public void setAccountSecretKey(String accountSecretKey) {
        this.accountSecretKey = accountSecretKey;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}

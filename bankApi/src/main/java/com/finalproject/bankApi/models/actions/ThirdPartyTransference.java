package com.finalproject.bankApi.models.actions;

import com.finalproject.bankApi.models.accounts.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ThirdPartyTransference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Long accountId;
    private String accountSecretKey;
    private String hashKey;

    public ThirdPartyTransference() {
    }

    public ThirdPartyTransference(Double amount, Long accountId, String accountSecretKey, String hashKey) {
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

    public void setAmount(Double amount) {
        BigDecimal bg = new BigDecimal(amount);
        this.amount = bg;
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

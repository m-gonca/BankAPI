package com.finalproject.bankApi.models.actions;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.AccountHolder;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Transference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
   
    private Long sendAccountId;

    private Long receiveAccountId;
    private String accountSecretKey;

    public Transference() {}

    public Transference(Double amount, Long sendAccountId, Long receiveAccountId, String accountSecretKey) {
        setAmount(amount);
        setSendAccountId(sendAccountId);
        setReceiveAccountId(receiveAccountId);
        setAccountSecretKey(accountSecretKey);
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
    

    public Long getSendAccountId() {
        return sendAccountId;
    }

    public void setSendAccountId(Long sendAccountId) {
        this.sendAccountId = sendAccountId;
    }

    public Long getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(Long receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public String getAccountSecretKey() {
        return accountSecretKey;
    }

    public void setAccountSecretKey(String accountSecretKey) {
        this.accountSecretKey = accountSecretKey;
    }
}

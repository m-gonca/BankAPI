package com.finalproject.bankApi.models.actions;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Transference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long sendAccountId;
    @NotNull
    private Long receiveAccountId;
    @NotNull
    @NotEmpty
    private String accountSecretKey;

    public Transference() {
    }

    public Transference(BigDecimal amount, Long sendAccountId, Long receiveAccountId, String accountSecretKey) {
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

    public void setAmount(BigDecimal amount) { this.amount = amount; }

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

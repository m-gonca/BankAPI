package com.finalproject.bankApi.models.transferences;

import com.finalproject.bankApi.models.accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Transference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "send_account_id")
    private Account sendAccount;
    @ManyToOne
    @JoinColumn(name = "receive_account_id")
    private Account receiveAccount;

    public Transference() {
    }

    public Transference(BigDecimal amount, Account sendAccount, Account receiveAccount) {
        setAmount(amount);
        setSendAccount(sendAccount);
        setReceiveAccount(receiveAccount);
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

    public Account getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(Account sendAccount) {
        this.sendAccount = sendAccount;
    }

    public Account getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(Account receiveAccount) {
        this.receiveAccount = receiveAccount;
    }
    


}

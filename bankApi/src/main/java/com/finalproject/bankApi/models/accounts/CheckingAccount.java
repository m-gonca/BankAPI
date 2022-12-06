package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class CheckingAccount extends Account {
    @NotNull
    private BigDecimal minBalance = new BigDecimal(250);
    @NotNull
    private BigDecimal monthFee = new BigDecimal(12);
    @NotNull
    private LocalDate creationDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public CheckingAccount(){}

    public CheckingAccount(AccountHolder primaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
    }
    
    public CheckingAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        minBalance.setScale(2, RoundingMode.CEILING);
        this.minBalance = minBalance;
    }

    public BigDecimal getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(BigDecimal monthFee) {
        monthFee.setScale(2, RoundingMode.CEILING);
        this.monthFee = monthFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

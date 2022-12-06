package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class SavingsAccount extends Account {
    @NotNull
    @DecimalMin(value = "100", inclusive = true)
    private BigDecimal minBalance = new BigDecimal(1000);
    @NotNull
    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate = new BigDecimal(0.0025);
    @NotNull
    private LocalDate creationDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public SavingsAccount() {}


    public SavingsAccount(AccountHolder primaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public SavingsAccount(AccountHolder primaryOwner, String secretKey, BigDecimal minBalance, BigDecimal interestRate, LocalDate creationDate, Status status) {
        super(primaryOwner);
        setSecretKey(secretKey);
        setMinBalance(minBalance);
        setInterestRate(interestRate);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public SavingsAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public SavingsAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minBalance, BigDecimal interestRate, LocalDate creationDate, Status status) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setMinBalance(minBalance);
        setInterestRate(interestRate);
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        interestRate.setScale(2, RoundingMode.CEILING);
        this.interestRate = interestRate;
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

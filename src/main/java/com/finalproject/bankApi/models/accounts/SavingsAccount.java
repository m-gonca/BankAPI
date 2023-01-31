package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class SavingsAccount extends Account {
  
    @NotNull
    @DecimalMin(value = "100", inclusive = true)
    @DecimalMax(value = "1000", inclusive = true)
    private BigDecimal minBalance = new BigDecimal("1000").setScale(2, RoundingMode.HALF_DOWN);
  
    @NotNull
    @DecimalMin(value = "0", inclusive = true)
    @DecimalMax(value = "0.5", inclusive = true)
    private BigDecimal interestRate = new BigDecimal("0.0025").setScale(4, RoundingMode.HALF_DOWN);

    private final LocalDate creationDate = LocalDate.now();

    private LocalDate lastProfitUpdate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public SavingsAccount() {
    }


    public SavingsAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }

    public SavingsAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minBalance, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setMinBalance(minBalance);
        setInterestRate(interestRate);
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        minBalance.setScale(2, RoundingMode.HALF_DOWN);
        this.minBalance = minBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        interestRate.setScale(2, RoundingMode.HALF_DOWN);
        this.interestRate = interestRate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getLastProfitUpdate() {
        return lastProfitUpdate;
    }

    public void setLastProfitUpdate(LocalDate lastProfitUpdate) {
        this.lastProfitUpdate = lastProfitUpdate;
    }

    public void checkInterests() {
        Period period;
        if (lastProfitUpdate == null) {
            period = Period.between(creationDate, LocalDate.now());
        } 
        else {
            period = Period.between(lastProfitUpdate, LocalDate.now());
        }
        if (period.getYears() == 1) {
            BigDecimal profit = super.getBalance().multiply(interestRate);
            super.setBalance(super.getBalance().add(profit).setScale(2, RoundingMode.HALF_DOWN));
            lastProfitUpdate = LocalDate.now();
        }
    }
}

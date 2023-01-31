package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class CheckingAccount extends Account {
    @NotNull
    private BigDecimal minBalance = new BigDecimal(250).setScale(2, RoundingMode.HALF_DOWN);
    @NotNull
    private BigDecimal monthFee = new BigDecimal(12).setScale(2, RoundingMode.HALF_DOWN);
    private final LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    
    public CheckingAccount(){}
    
    public CheckingAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        minBalance.setScale(2, RoundingMode.HALF_DOWN);
        this.minBalance = minBalance;
    }

    public BigDecimal getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(BigDecimal monthFee) {
        monthFee.setScale(2, RoundingMode.HALF_DOWN);
        this.monthFee = monthFee;
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

}

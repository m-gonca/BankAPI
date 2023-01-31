package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCardAccount extends Account {

    @NotNull
    @DecimalMin(value = "100", inclusive = true)
    @DecimalMax(value = "100000", inclusive = true)
    private BigDecimal creditLimit = new BigDecimal("100").setScale(2, RoundingMode.HALF_DOWN);
 
    @NotNull
    @DecimalMin(value = "0.10", inclusive = true)
    @DecimalMax(value = "0.20", inclusive = true)
    private BigDecimal interestRate = new BigDecimal("0.20").setScale(2, RoundingMode.HALF_DOWN);

    private LocalDate lastProfitUpdate = LocalDate.now();

    public CreditCardAccount() {}

    public CreditCardAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(primaryOwner, secondaryOwner);
    }
    public CreditCardAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal  creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }
    
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        creditLimit.setScale(2, RoundingMode.HALF_DOWN);
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastProfitUpdate() {
        return lastProfitUpdate;
    }

    public void setLastProfitUpdate(LocalDate lastProfitUpdate) {
        this.lastProfitUpdate = lastProfitUpdate;
    }

    public void checkInterests() {
        Period period = Period.between(lastProfitUpdate, LocalDate.now());
        
        if (period.getMonths() == 1) {
            BigDecimal profit = super.getBalance().multiply(interestRate.divide(new BigDecimal(12)));
            super.setBalance(super.getBalance().add(profit).setScale(2, RoundingMode.HALF_DOWN));
            lastProfitUpdate = LocalDate.now();
        }
    }
}

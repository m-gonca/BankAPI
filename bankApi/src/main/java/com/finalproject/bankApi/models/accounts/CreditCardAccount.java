package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class CreditCardAccount extends Account {
    @NotNull
    @DecimalMax(value = "100000", inclusive = true)
    private BigDecimal creditLimit = new BigDecimal(100).setScale(2, RoundingMode.CEILING);
    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal interestRate = new BigDecimal(0.2).setScale(2, RoundingMode.CEILING);

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
        creditLimit.setScale(2, RoundingMode.CEILING);
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}

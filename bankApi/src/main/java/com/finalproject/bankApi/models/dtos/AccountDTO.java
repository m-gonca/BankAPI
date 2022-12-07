package com.finalproject.bankApi.models.dtos;

import java.math.BigDecimal;

public class AccountDTO {
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private BigDecimal minBalance;
    private BigDecimal monthFee;
    private BigDecimal creditLimit;
    private BigDecimal interestRate;
    private String secretKey;

    public AccountDTO(Long primaryOwnerId, Long secondaryOwnerId, BigDecimal minBalance, BigDecimal monthFee, BigDecimal creditLimit, BigDecimal interestRate, String secretKey) {
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minBalance = minBalance;
        this.monthFee = monthFee;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
    }

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    public BigDecimal getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(BigDecimal monthFee) {
        this.monthFee = monthFee;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}

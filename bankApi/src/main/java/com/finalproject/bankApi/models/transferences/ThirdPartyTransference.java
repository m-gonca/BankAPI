package com.finalproject.bankApi.models.transferences;

import com.finalproject.bankApi.models.accounts.Account;
import com.finalproject.bankApi.models.users.ThirdParty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class ThirdPartyTransference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @NotNull
    @ManyToOne
    //@JoinColumn(name = "third_party_id")
    private ThirdParty thirdParty;

    public ThirdPartyTransference() {
    }

    public ThirdPartyTransference(BigDecimal amount, Account account, ThirdParty thirdParty) {
        this.amount = amount;
        this.account = account;
        this.thirdParty = thirdParty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }
}

package com.finalproject.bankApi.models.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import com.finalproject.bankApi.models.actions.Transference;
import com.finalproject.bankApi.models.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance = new BigDecimal(0);
    @NotNull
    @ManyToOne
    @JoinColumn(name = "primaryOwner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondaryOwner_id")
    private AccountHolder secondaryOwner;
    private final BigDecimal penaltyFee = new BigDecimal(40);
    private String secretKey;
    @JsonIgnore
    @OneToMany(mappedBy = "sendAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transference> sentTransference = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "receiveAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transference> receivedTransference = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ThirdPartyTransference> thirdPartyTransference = new ArrayList<>();

    public Account() {
    }

    public Account(AccountHolder primaryOwner) {
        setPrimaryOwner(primaryOwner);
    }

    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        balance.setScale(2, RoundingMode.CEILING);
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<Transference> getSentTransference() {
        return sentTransference;
    }

    public void setSentTransference(List<Transference> sentTransference) {
        this.sentTransference = sentTransference;
    }

    public List<Transference> getReceivedTransference() {
        return receivedTransference;
    }

    public void setReceivedTransference(List<Transference> receivedTransference) {
        this.receivedTransference = receivedTransference;
    }

    public List<ThirdPartyTransference> getThirdPartyTransference() {
        return thirdPartyTransference;
    }

    public void setThirdPartyTransference(List<ThirdPartyTransference> thirdPartyTransference) {
        this.thirdPartyTransference = thirdPartyTransference;
    }
}

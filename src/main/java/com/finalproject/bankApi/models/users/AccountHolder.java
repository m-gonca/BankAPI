package com.finalproject.bankApi.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class AccountHolder extends User {
    @NotNull
    private LocalDate birthDate;
    @NotNull
    @Embedded
    private Address primaryAddress;
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "mailing_address")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country"))
    })
    private Address mailAddress;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primaryOwner")
    private List<Account> primaryOwnerAccount = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secondaryOwner")
    private List<Account> secondaryOwnerAccount = new ArrayList<>();
    
    public AccountHolder() {}

    public AccountHolder(String name, String password, LocalDate birthDate, Address primaryAddress, Address mailAddress) {
        super(name, password);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setMailAddress(mailAddress);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(Address mailAddress) {
        this.mailAddress = mailAddress;
    }

    public List<Account> getPrimaryOwnerAccount() {
        return primaryOwnerAccount;
    }

    public void setPrimaryOwnerAccount(List<Account> primaryOwnerAccount) {
        this.primaryOwnerAccount = primaryOwnerAccount;
    }

    public List<Account> getSecondaryOwnerAccount() {
        return secondaryOwnerAccount;
    }

    public void setSecondaryOwnerAccount(List<Account> secondaryOwnerAccount) {
        this.secondaryOwnerAccount = secondaryOwnerAccount;
    }
}

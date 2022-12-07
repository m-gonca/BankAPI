package com.finalproject.bankApi.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.Account;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class AccountHolder extends User {
    
    private LocalDate birthDate;
    @Embedded
    private Address primaryAddress;
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
    private List<Account> primaryOwnerAccounts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secondaryOwner")
    private List<Account> secondaryOwnerAccounts = new ArrayList<>();
    
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

    public List<Account> getPrimaryOwnerAccounts() {
        return primaryOwnerAccounts;
    }

    public void setPrimaryOwnerAccounts(List<Account> primaryOwnerAccounts) {
        this.primaryOwnerAccounts = primaryOwnerAccounts;
    }

    public List<Account> getSecondaryOwnerAccounts() {
        return secondaryOwnerAccounts;
    }

    public void setSecondaryOwnerAccounts(List<Account> secondaryOwnerAccounts) {
        this.secondaryOwnerAccounts = secondaryOwnerAccounts;
    }
}

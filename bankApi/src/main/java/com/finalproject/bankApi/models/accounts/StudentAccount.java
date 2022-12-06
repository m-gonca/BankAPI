package com.finalproject.bankApi.models.accounts;

import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class StudentAccount extends Account {
    @NotNull
    private LocalDate creationDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentAccount() {}

    public StudentAccount(AccountHolder primaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public StudentAccount(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setCreationDate(creationDate);
        setStatus(status);
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

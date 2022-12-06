package com.finalproject.bankApi.models.users;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class ThirdParty extends User{
    @NotNull
    @NotEmpty
    private String hashKey;

    public ThirdParty() {}

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}

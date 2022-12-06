package com.finalproject.bankApi.models.users;

import jakarta.persistence.Entity;

@Entity
public class ThirdParty extends User{
    private String hashKey;

    public ThirdParty() {}

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}

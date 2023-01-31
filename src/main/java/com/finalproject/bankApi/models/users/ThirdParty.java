package com.finalproject.bankApi.models.users;

import jakarta.persistence.Entity;

@Entity
public class ThirdParty extends User{
    public ThirdParty() {}
    public ThirdParty(String name, String password) {
        super(name, password);
    }
}

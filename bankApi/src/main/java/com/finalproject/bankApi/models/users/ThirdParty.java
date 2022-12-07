package com.finalproject.bankApi.models.users;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class ThirdParty extends User{

    public ThirdParty() {}

    public ThirdParty(String name) {
        super(name);
        super.setPassword("123456");
    }
}

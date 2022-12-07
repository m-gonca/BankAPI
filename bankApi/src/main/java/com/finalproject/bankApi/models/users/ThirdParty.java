package com.finalproject.bankApi.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;


import java.util.ArrayList;
import java.util.List;

@Entity
public class ThirdParty extends User{
  /*  @JsonIgnore
    @OneToMany(mappedBy = "thirdParty", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ThirdPartyTransference> transference = new ArrayList<>();*/
    public ThirdParty() {}
    public ThirdParty(String name) {
        super(name);
        super.setPassword("123456");
    }
/*
    public List<ThirdPartyTransference> getTransference() {
        return transference;
    }

    public void setTransference(List<ThirdPartyTransference> transference) {
        this.transference = transference;
    }*/
}

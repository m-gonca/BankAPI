package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.ThirdParty;
import com.finalproject.bankApi.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty addThirdParty( ThirdParty  thirdParty) {
        return  thirdPartyRepository.save( thirdParty);
    }
}

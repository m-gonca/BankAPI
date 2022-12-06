package com.finalproject.bankApi.services.users;

import com.finalproject.bankApi.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdRepository;
    
}

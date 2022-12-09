package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.transferences.ThirdPartyTransference;
import com.finalproject.bankApi.models.dtos.ThirdPartyTransferenceDTO;

public interface ThirdPartyControllerInt {
    
    ThirdPartyTransference makeThirdPartyTransference(String thirdPartyKey, ThirdPartyTransferenceDTO transferenceDTO);
}

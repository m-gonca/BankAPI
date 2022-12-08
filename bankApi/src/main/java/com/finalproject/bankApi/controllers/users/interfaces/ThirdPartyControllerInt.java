package com.finalproject.bankApi.controllers.users.interfaces;

import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import com.finalproject.bankApi.models.dtos.ThirdPartyTransferenceDTO;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;

public interface ThirdPartyControllerInt {
    
    ThirdPartyTransference makeThirdPartyTransference(String thirdPartyKey, ThirdPartyTransferenceDTO transferenceDTO);
}

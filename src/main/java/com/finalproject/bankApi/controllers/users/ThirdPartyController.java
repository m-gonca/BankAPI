package com.finalproject.bankApi.controllers.users;

import com.finalproject.bankApi.controllers.users.interfaces.ThirdPartyControllerInt;
import com.finalproject.bankApi.models.transferences.ThirdPartyTransference;
import com.finalproject.bankApi.models.dtos.ThirdPartyTransferenceDTO;
import com.finalproject.bankApi.services.users.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-party")
public class ThirdPartyController implements ThirdPartyControllerInt {

    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/transfer-money")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyTransference makeThirdPartyTransference(@RequestHeader String thirdPartyKey, @RequestBody ThirdPartyTransferenceDTO transferenceDTO) {
        return thirdPartyService.addTransference(thirdPartyKey, transferenceDTO);
    }
}

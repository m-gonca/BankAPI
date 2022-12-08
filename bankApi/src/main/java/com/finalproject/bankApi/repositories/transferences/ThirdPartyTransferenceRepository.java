package com.finalproject.bankApi.repositories.transferences;

import com.finalproject.bankApi.models.actions.ThirdPartyTransference;
import com.finalproject.bankApi.models.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyTransferenceRepository extends JpaRepository<ThirdPartyTransference, Long> {

}

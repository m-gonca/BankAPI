package com.finalproject.bankApi.repositories.accounts;

import com.finalproject.bankApi.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndSecretKey(Long accountId, String secretKey);
    Optional<Account> findByIdAndPrimaryOwnerId(Long accountId, Long accountHolderId);
    Optional<Account> findByIdAndSecondaryOwnerId(Long accountId, Long accountHolderId);
}

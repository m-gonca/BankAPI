package com.finalproject.bankApi.repositories.accounts;

import com.finalproject.bankApi.models.accounts.CreditCardAccount;
import com.finalproject.bankApi.models.accounts.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Long> {
}

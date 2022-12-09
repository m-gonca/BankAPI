package com.finalproject.bankApi.repositories.transferences;

import com.finalproject.bankApi.models.transferences.Transference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenceRepository extends JpaRepository<Transference, Long> {
}

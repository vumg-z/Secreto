package com.secret.platform.corporate_contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateContractRepository extends JpaRepository<CorporateContract, Long> {
    Optional<CorporateContract> findByContractNumber(String contractNumber);
}

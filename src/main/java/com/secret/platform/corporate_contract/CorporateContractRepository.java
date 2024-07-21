package com.secret.platform.corporate_contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateContractRepository extends JpaRepository<CorporateContract, Long> {
}

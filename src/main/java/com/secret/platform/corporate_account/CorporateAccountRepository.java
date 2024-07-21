package com.secret.platform.corporate_account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateAccountRepository extends JpaRepository<CorporateAccount, Long> {
    Optional<CorporateAccount> findByCdpId(String cdpId);

}

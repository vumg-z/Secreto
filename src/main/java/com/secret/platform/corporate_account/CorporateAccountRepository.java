package com.secret.platform.corporate_account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateAccountRepository extends JpaRepository<CorporateAccount, Long> {
}

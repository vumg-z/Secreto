package com.secret.platform.corporate_account;

import java.util.List;
import java.util.Optional;

public interface CorporateAccountService {
    List<CorporateAccount> getAllCorporateAccounts();
    Optional<CorporateAccount> getCorporateAccountById(Long id);
    CorporateAccount createCorporateAccount(CorporateAccount corporateAccount);
    CorporateAccount updateCorporateAccount(Long id, CorporateAccount corporateAccount);
    void deleteCorporateAccount(Long id);
}

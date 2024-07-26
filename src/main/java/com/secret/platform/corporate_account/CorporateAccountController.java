package com.secret.platform.corporate_account;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corporate-accounts")
public class CorporateAccountController {

    @Autowired
    private CorporateAccountService corporateAccountService;

    @PostMapping
    public ResponseEntity<CorporateAccount> createCorporateAccount(@RequestBody CorporateAccount corporateAccount) {
        CorporateAccount createdAccount = corporateAccountService.createCorporateAccount(corporateAccount);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorporateAccount> getCorporateAccountById(@PathVariable Long id) {
        CorporateAccount account = corporateAccountService.getCorporateAccountById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateAccount not found for this id :: " + id));
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public List<CorporateAccount> getAllCorporateAccounts() {
        return corporateAccountService.getAllCorporateAccounts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorporateAccount> updateCorporateAccount(@PathVariable Long id,
                                                                   @RequestBody CorporateAccount corporateAccountDetails) {
        CorporateAccount updatedAccount = corporateAccountService.updateCorporateAccount(id, corporateAccountDetails);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorporateAccount(@PathVariable Long id) {
        corporateAccountService.deleteCorporateAccount(id);
        return ResponseEntity.noContent().build();
    }
}

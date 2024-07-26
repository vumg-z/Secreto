package com.secret.platform.corporate_contract;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corporate-contracts")
public class CorporateContractController {

    @Autowired
    private CorporateContractService corporateContractService;

    @PostMapping
    public ResponseEntity<CorporateContract> createCorporateContract(@RequestBody CorporateContract corporateContract) {
        CorporateContract createdCorporateContract = corporateContractService.createCorporateContract(corporateContract);
        return ResponseEntity.ok(createdCorporateContract);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorporateContract> getCorporateContractById(@PathVariable Long id) {
        CorporateContract corporateContract = corporateContractService.getCorporateContractById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateContract not found for this id :: " + id));
        return ResponseEntity.ok(corporateContract);
    }

    @GetMapping
    public List<CorporateContract> getAllCorporateContracts() {
        return corporateContractService.getAllCorporateContracts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorporateContract> updateCorporateContract(@PathVariable Long id,
                                                                     @RequestBody CorporateContract corporateContractDetails) {
        CorporateContract updatedCorporateContract = corporateContractService.updateCorporateContract(id, corporateContractDetails);
        return ResponseEntity.ok(updatedCorporateContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorporateContract(@PathVariable Long id) {
        corporateContractService.deleteCorporateContract(id);
        return ResponseEntity.noContent().build();
    }
}

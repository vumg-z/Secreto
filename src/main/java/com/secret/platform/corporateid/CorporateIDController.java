package com.secret.platform.corporateid;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Deprecated
@RestController
@RequestMapping("/api/corporateid")
public class CorporateIDController {

    @Autowired
    private CorporateIDService corporateIDService;

    @GetMapping
    public List<CorporateID> getAllCorporateIDs() {
        return corporateIDService.getAllCorporateIDs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorporateID> getCorporateIDById(@PathVariable Long id) {
        CorporateID corporateID = corporateIDService.getCorporateIDById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateID not found for this id :: " + id));
        return ResponseEntity.ok().body(corporateID);
    }

    @PostMapping
    public CorporateID createCorporateID(@RequestBody CorporateID corporateID) {
        return corporateIDService.createCorporateID(corporateID);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorporateID> updateCorporateID(@PathVariable Long id, @RequestBody CorporateID corporateIDDetails) {
        CorporateID updatedCorporateID = corporateIDService.updateCorporateID(id, corporateIDDetails);
        return ResponseEntity.ok(updatedCorporateID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorporateID(@PathVariable Long id) {
        corporateIDService.deleteCorporateID(id);
        return ResponseEntity.noContent().build();
    }
}

package com.secret.platform.corporate_contract;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.privilege_code.PrivilegeCodeRepository;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CorporateContractService {

    @Autowired
    private CorporateContractRepository corporateContractRepository;

    @Autowired
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Autowired
    private RateProductRepository rateProductRepository;

    public List<CorporateContract> getAllCorporateContracts() {
        return corporateContractRepository.findAll();
    }

    public Optional<CorporateContract> getCorporateContractById(Long id) {
        return corporateContractRepository.findById(id);
    }

    public CorporateContract createCorporateContract(CorporateContract corporateContract) {
        validateAndSetRelationships(corporateContract);
        corporateContract.setModifiedDate(LocalDate.now());
        return corporateContractRepository.save(corporateContract);
    }

    public CorporateContract updateCorporateContract(Long id, CorporateContract corporateContractDetails) {
        return corporateContractRepository.findById(id)
                .map(existingCorporateContract -> {
                    validateAndSetRelationships(corporateContractDetails);
                    corporateContractDetails.setId(id);
                    corporateContractDetails.setModifiedDate(LocalDate.now());
                    return corporateContractRepository.save(corporateContractDetails);
                })
                .orElseThrow(() -> new ResourceNotFoundException("CorporateContract not found with id " + id));
    }

    public void deleteCorporateContract(Long id) {
        if (corporateContractRepository.existsById(id)) {
            corporateContractRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("CorporateContract not found with id " + id);
        }
    }

    private void validateAndSetRelationships(CorporateContract corporateContract) {
        // Validate and set PrivilegeCodes
        if (corporateContract.getPrivilegeCodes() != null) {
            List<PrivilegeCode> validPrivilegeCodes = corporateContract.getPrivilegeCodes().stream()
                    .map(pc -> privilegeCodeRepository.findById(pc.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("PrivilegeCode not found with id " + pc.getId())))
                    .collect(Collectors.toList());
            corporateContract.setPrivilegeCodes(validPrivilegeCodes);
        }

        // Validate and set RateProduct
        if (corporateContract.getRateProduct() != null && corporateContract.getRateProduct().getId() != null) {
            RateProduct rateProduct = rateProductRepository.findById(corporateContract.getRateProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found with id " + corporateContract.getRateProduct().getId()));
            corporateContract.setRateProduct(rateProduct);
        }
    }
}

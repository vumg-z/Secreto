package com.secret.platform.corporate_contract;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.privilege_code.PrivilegeCodeRepository;
import com.secret.platform.rate_product.RateProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CorporateContractService {

    private static final Logger logger = LoggerFactory.getLogger(CorporateContractService.class);

    @Autowired
    private CorporateContractRepository corporateContractRepository;

    @Autowired
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Autowired
    private RateProductService rateProductService;

    public List<CorporateContract> getAllCorporateContracts() {
        return corporateContractRepository.findAll();
    }

    public Optional<CorporateContract> getCorporateContractById(Long id) {
        return corporateContractRepository.findById(id);
    }
    public Optional<CorporateContract> getCorporateContractByContractNumber(String contractNumber) {
        return corporateContractRepository.findByContractNumber(contractNumber);
    }
    public CorporateContract createCorporateContract(CorporateContract corporateContract) {
        validateAndSetPrivilegeCodes(corporateContract);
        corporateContract.setModifiedDate(LocalDate.now());
        CorporateContract savedContract = corporateContractRepository.save(corporateContract);
        validateAndSetRateProduct(savedContract);
        return corporateContractRepository.save(savedContract);
    }

    public CorporateContract updateCorporateContract(Long id, CorporateContract corporateContractDetails) {
        return corporateContractRepository.findById(id)
                .map(existingCorporateContract -> {
                    validateAndSetPrivilegeCodes(corporateContractDetails);
                    corporateContractDetails.setId(id);
                    corporateContractDetails.setModifiedDate(LocalDate.now());
                    CorporateContract updatedContract = corporateContractRepository.save(corporateContractDetails);
                    validateAndSetRateProduct(updatedContract);
                    return corporateContractRepository.save(updatedContract);
                })
                .orElseThrow(() -> new ResourceNotFoundException("CorporateContract not found with id " + id));
    }

    private void validateAndSetRateProduct(CorporateContract corporateContract) {
        String rateProductName = corporateContract.getRateProduct();
        if (rateProductName != null && !rateProductName.isEmpty()) {
            List<String> foundRateProductNames = rateProductService.findRateProductByName(rateProductName);
            if (foundRateProductNames.isEmpty()) {
                throw new ResourceNotFoundException("RateProduct not found with product name " + rateProductName);
            }
            corporateContract.setRateProduct(foundRateProductNames.get(0));  // Pick the first one or handle appropriately
            logger.info("RateProduct {} has been set for CorporateContract with contract number {}", foundRateProductNames.get(0), corporateContract.getContractNumber());
        } else {
            logger.warn("RateProduct name is null or empty for CorporateContract with contract number {}", corporateContract.getContractNumber());
        }
    }

    private void validateAndSetPrivilegeCodes(CorporateContract corporateContract) {
        if (corporateContract.getPrivilegeCodes() != null) {
            List<PrivilegeCode> validPrivilegeCodes = corporateContract.getPrivilegeCodes().stream()
                    .map(pc -> privilegeCodeRepository.findByCode(pc.getCode())
                            .orElseThrow(() -> new ResourceNotFoundException("PrivilegeCode not found with code " + pc.getCode())))
                    .collect(Collectors.toList());
            corporateContract.setPrivilegeCodes(validPrivilegeCodes);
        }
    }

    public void deleteCorporateContract(Long id) {
        if (corporateContractRepository.existsById(id)) {
            corporateContractRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("CorporateContract not found with id " + id);
        }
    }
}

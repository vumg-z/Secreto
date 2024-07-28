package com.secret.platform.corporate_account;

import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.corporate_contract.CorporateContractService;
import com.secret.platform.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorporateAccountServiceImpl implements CorporateAccountService {

    private static final Logger logger = LoggerFactory.getLogger(CorporateAccountServiceImpl.class);

    @Autowired
    private CorporateAccountRepository corporateAccountRepository;

    @Autowired
    private CorporateContractService corporateContractService;

    @Override
    public List<CorporateAccount> getAllCorporateAccounts() {
        logger.info("Entering getAllCorporateAccounts method.");
        List<CorporateAccount> accounts = corporateAccountRepository.findAll();
        logger.info("Retrieved {} corporate accounts.", accounts.size());
        logger.info("Exiting getAllCorporateAccounts method.");
        return accounts;
    }

    @Override
    public Optional<CorporateAccount> getCorporateAccountById(Long id) {
        logger.info("Entering getCorporateAccountById method with id: {}", id);
        Optional<CorporateAccount> account = corporateAccountRepository.findById(id);
        if (account.isPresent()) {
            logger.info("Corporate account found with id: {}", id);
        } else {
            logger.warn("Corporate account not found with id: {}", id);
        }
        logger.info("Exiting getCorporateAccountById method.");
        return account;
    }

    @Override
    public CorporateAccount createCorporateAccount(CorporateAccount corporateAccount) {
        logger.info("Entering createCorporateAccount method with corporateAccount: {}", corporateAccount);
        saveCorporateContractIfNecessary(corporateAccount.getCorporateContract());
        CorporateAccount savedAccount = corporateAccountRepository.save(corporateAccount);
        logger.info("Corporate account created with id: {}", savedAccount.getId());
        logger.info("Exiting createCorporateAccount method.");
        return savedAccount;
    }

    @Override
    public CorporateAccount updateCorporateAccount(Long id, CorporateAccount corporateAccount) {
        logger.info("Entering updateCorporateAccount method with id: {} and corporateAccount: {}", id, corporateAccount);
        return corporateAccountRepository.findById(id)
                .map(existingAccount -> {
                    saveCorporateContractIfNecessary(corporateAccount.getCorporateContract());
                    corporateAccount.setId(id);
                    CorporateAccount updatedAccount = corporateAccountRepository.save(corporateAccount);
                    logger.info("Corporate account updated with id: {}", updatedAccount.getId());
                    logger.info("Exiting updateCorporateAccount method.");
                    return updatedAccount;
                })
                .orElseThrow(() -> {
                    logger.error("CorporateAccount not found with id: {}", id);
                    return new ResourceNotFoundException("CorporateAccount not found with id " + id);
                });
    }

    @Override
    public void deleteCorporateAccount(Long id) {
        logger.info("Entering deleteCorporateAccount method with id: {}", id);
        if (corporateAccountRepository.existsById(id)) {
            corporateAccountRepository.deleteById(id);
            logger.info("Corporate account deleted with id: {}", id);
        } else {
            logger.error("CorporateAccount not found with id: {}", id);
            throw new ResourceNotFoundException("CorporateAccount not found with id " + id);
        }
        logger.info("Exiting deleteCorporateAccount method.");
    }

    private void saveCorporateContractIfNecessary(CorporateContract corporateContract) {
        if (corporateContract != null) {
            logger.info("Processing corporate contract: {}", corporateContract);

            if (corporateContract.getId() == null && corporateContract.getContractNumber() != null) {
                // Try to find an existing corporate contract by contract number
                Optional<CorporateContract> existingContract = corporateContractService.getCorporateContractByContractNumber(corporateContract.getContractNumber());

                if (existingContract.isPresent()) {
                    // If an existing contract is found, use it
                    logger.info("Existing corporate contract found with contract number: {}", corporateContract.getContractNumber());
                    corporateContract.setId(existingContract.get().getId());
                    logger.info("Corporate contract ID updated to existing ID: {}", corporateContract.getId());
                } else {
                    // If no existing contract is found, save the new contract
                    logger.info("No existing corporate contract found, saving new contract: {}", corporateContract);
                    corporateContractService.createCorporateContract(corporateContract);
                    logger.info("Corporate contract saved with id: {}", corporateContract.getId());
                }
            } else if (corporateContract.getId() != null) {
                // Validate the existing contract ID
                logger.info("Validating existing corporate contract with id: {}", corporateContract.getId());
                corporateContractService.getCorporateContractById(corporateContract.getId())
                        .orElseThrow(() -> {
                            logger.error("CorporateContract not found with id: {}", corporateContract.getId());
                            return new ResourceNotFoundException("CorporateContract not found with id " + corporateContract.getId());
                        });
                logger.info("Corporate contract validated successfully.");
            } else {
                logger.warn("Corporate contract number is null and ID is null. Cannot process corporate contract.");
            }
        } else {
            logger.warn("Corporate contract is null. Skipping processing.");
        }
    }
}

package com.secret.platform.corporate_account;

import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorporateAccountServiceImpl implements CorporateAccountService {

    @Autowired
    private CorporateAccountRepository corporateAccountRepository;

    @Autowired
    private CorporateContractRepository corporateContractRepository;

    @Override
    public List<CorporateAccount> getAllCorporateAccounts() {
        return corporateAccountRepository.findAll();
    }

    @Override
    public Optional<CorporateAccount> getCorporateAccountById(Long id) {
        return corporateAccountRepository.findById(id);
    }

    @Override
    public CorporateAccount createCorporateAccount(CorporateAccount corporateAccount) {
        validateCorporateContract(corporateAccount.getCorporateContract());
        return corporateAccountRepository.save(corporateAccount);
    }

    @Override
    public CorporateAccount updateCorporateAccount(Long id, CorporateAccount corporateAccount) {
        return corporateAccountRepository.findById(id)
                .map(existingAccount -> {
                    validateCorporateContract(corporateAccount.getCorporateContract());
                    corporateAccount.setId(id);
                    return corporateAccountRepository.save(corporateAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException("CorporateAccount not found with id " + id));
    }

    @Override
    public void deleteCorporateAccount(Long id) {
        if (corporateAccountRepository.existsById(id)) {
            corporateAccountRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("CorporateAccount not found with id " + id);
        }
    }

    private void validateCorporateContract(CorporateContract corporateContract) {
        if (corporateContract != null && corporateContract.getContractNumber() != null) {
            corporateContractRepository.findByContractNumber(corporateContract.getContractNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("CorporateContract not found with contract number " + corporateContract.getContractNumber()));
        }
    }
}

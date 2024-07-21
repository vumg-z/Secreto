package com.secret.platform.res_rates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.res_rates.ResRatesDTO;
import com.secret.platform.res_rates.ResRatesResponseDTO;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.res_rates.ResRatesServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResRatesService implements ResRatesServiceInterface {

    private final CorporateAccountRepository corporateAccountRepository;
    private final CorporateContractRepository corporateContractRepository;
    private final RateProductRepository rateProductRepository;
    private final ClassCodeRepository classCodeRepository;

    @Autowired
    public ResRatesService(CorporateAccountRepository corporateAccountRepository,
                           CorporateContractRepository corporateContractRepository,
                           RateProductRepository rateProductRepository,
                           ClassCodeRepository classCodeRepository) {
        this.corporateAccountRepository = corporateAccountRepository;
        this.corporateContractRepository = corporateContractRepository;
        this.rateProductRepository = rateProductRepository;
        this.classCodeRepository = classCodeRepository;
    }

    @Override
    public ResRatesResponseDTO getRates(ResRatesDTO resRatesDTO) {
        // Retrieve the CorporateAccount using the CorpRateID
        String corpRateID = resRatesDTO.getCorpRateID();
        CorporateAccount corporateAccount = corporateAccountRepository.findByCdpId(corpRateID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Corporate Rate ID"));

        // Get the CorporateContract linked to the CorporateAccount
        CorporateContract corporateContract = corporateAccount.getCorporateContract();

        // Retrieve the RateProduct linked to the CorporateContract
        RateProduct rateProduct = corporateContract.getRateProduct();

        // Create response DTO
        ResRatesResponseDTO response = new ResRatesResponseDTO();
        response.setSuccess(true);
        response.setCount(1); // Assuming there's only one rate product to be returned

        ResRatesResponseDTO.Rate rate = new ResRatesResponseDTO.Rate();
        rate.setRateID(rateProduct.getId().toString());
        rate.setAvailability("Available");
        rate.setClassCode(getClassCodes(rateProduct)); // Method to retrieve class codes
        rate.setCurrencyCode("USD");
        rate.setEstimate(calculateEstimate(rateProduct));
        rate.setRateOnlyEstimate(calculateEstimate(rateProduct));
        rate.setDropCharge(createDropCharge());
        rate.setDistance(createDistance());
        rate.setLiability(0);
        rate.setPrePaid(false);
        response.addRate(rate);

        return response;
    }

    private double calculateEstimate(RateProduct rateProduct) {
        // Implement your logic to calculate the estimate
        return 0.0;
    }

    private String getClassCodes(RateProduct rateProduct) {
        // Assuming RateProduct has a list of ClassCode objects
        StringBuilder classCodes = new StringBuilder();
        for (ClassCode classCode : rateProduct.getClassCodes()) {
            if (classCodes.length() > 0) {
                classCodes.append(", ");
            }
            classCodes.append(classCode.getClassCode());
        }
        return classCodes.toString();
    }

    private ResRatesResponseDTO.Rate.DropCharge createDropCharge() {
        ResRatesResponseDTO.Rate.DropCharge dropCharge = new ResRatesResponseDTO.Rate.DropCharge();
        dropCharge.setResponsibility("renter");
        dropCharge.setAmount(0.00);
        return dropCharge;
    }

    private ResRatesResponseDTO.Rate.Distance createDistance() {
        ResRatesResponseDTO.Rate.Distance distance = new ResRatesResponseDTO.Rate.Distance();
        distance.setIncluded("unlimited");
        return distance;
    }
}

package com.secret.platform.resEstimates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.exception.CorporateRateNotFoundException;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductService;
import com.secret.platform.rate_product.RateProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ResEstimatesService implements ResRatesEstimatesServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);


    @Autowired
    private RateProductService rateProductService;

    @Autowired
    private CorporateAccountRepository corporateAccountRepository;

    @Override
    public ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO) {
        ResEstimatesResponseDTO response = new ResEstimatesResponseDTO();

        // Extract necessary information from the request DTO
        String locationCode = resEstimatesDTO.getPickup().getLocationCode();
        String countryCode = resEstimatesDTO.getSource();

        // Retrieve the CorporateAccount using the CorpRateID
        String corpRateID = resEstimatesDTO.getQuotedRate().getCorporateRateID();
        CorporateAccount corporateAccount = corporateAccountRepository.findByCdpId(corpRateID)
                .orElseThrow(() -> new CorporateRateNotFoundException(corpRateID));

        // Get the CorporateContract linked to the CorporateAccount
        CorporateContract corporateContract = corporateAccount.getCorporateContract();

        // Retrieve the RateProduct linked to the CorporateContract
        String rateProductName = corporateContract.getRateProduct();

        // Retrieve the rate product
        Optional<RateProduct> rateProductOpt = rateProductService.getSpecificRateProduct(locationCode, countryCode, rateProductName);

        Map<String, ResEstimatesResponseDTO.Charge> chargeMap = new HashMap<>();

        // Logging and checking if the rates are retrieved correctly
        rateProductOpt.ifPresent(rateProduct -> {
            List<ClassCode> classCodes = rateProduct.getClassCodes();
            logger.info(String.format("Retrieved %d class codes for location %s and country %s", classCodes.size(), locationCode, countryCode));

            for (ClassCode classCode : classCodes) {
                logger.info(String.format("Class Code: %s, Day Rate: %f, Week Rate: %f, Month Rate: %f, XDay Rate: %f, Hour Rate: %f",
                        classCode.getClassCode(), classCode.getDayRate(), classCode.getWeekRate(), classCode.getMonthRate(), classCode.getXDayRate(), classCode.getHourRate()));
            }
        });

        // We can return the response here for now, as we're focusing on logging the rates
        return response;
    }

}

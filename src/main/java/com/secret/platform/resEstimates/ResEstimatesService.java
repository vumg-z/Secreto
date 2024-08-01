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
        LocalDateTime pickupDateTime = resEstimatesDTO.getPickup().getDateTime();
        LocalDateTime returnDateTime = resEstimatesDTO.getReturnInfo().getDateTime();

        logger.info("Processing reservation estimate request...");
        logger.info("Pickup location: {}, Pickup time: {}", locationCode, pickupDateTime);
        logger.info("Return location: {}, Return time: {}", resEstimatesDTO.getReturnInfo().getLocationCode(), returnDateTime);

        // Retrieve the CorporateAccount using the CorpRateID
        String corpRateID = resEstimatesDTO.getQuotedRate().getCorporateRateID();
        CorporateAccount corporateAccount = corporateAccountRepository.findByCdpId(corpRateID)
                .orElseThrow(() -> {
                    logger.error("Corporate rate not found for ID: {}", corpRateID);
                    return new CorporateRateNotFoundException(corpRateID);
                });

        logger.info("Corporate account found: {}", corporateAccount);

        // Get the CorporateContract linked to the CorporateAccount
        CorporateContract corporateContract = corporateAccount.getCorporateContract();
        logger.info("Corporate contract found: {}", corporateContract);

        // Retrieve the RateProduct linked to the CorporateContract
        String rateProductName = corporateContract.getRateProduct();
        logger.info("Rate product name from contract: {}", rateProductName);

        // Retrieve the rate product
        Optional<RateProduct> rateProductOpt = rateProductService.getSpecificRateProduct(locationCode, countryCode, rateProductName);

        rateProductOpt.ifPresentOrElse(rateProduct -> {
            logger.info("Rate product found: {}", rateProduct);

            List<ClassCode> classCodes = rateProduct.getClassCodes();
            logger.info("Retrieved {} class codes for location {} and country {}", classCodes.size(), locationCode, countryCode);

            for (ClassCode classCode : classCodes) {
                logger.info("Class Code: {}, Day Rate: {}, Week Rate: {}, Month Rate: {}, XDay Rate: {}, Hour Rate: {}",
                        classCode.getClassCode(), classCode.getDayRate(), classCode.getWeekRate(), classCode.getMonthRate(), classCode.getXDayRate(), classCode.getHourRate());

                // Calculate the estimate (placeholder for now)
                double estimate = calculateEstimate(classCode, pickupDateTime, returnDateTime);
                logger.info("Estimate for class code {}: {}", classCode.getClassCode(), estimate);
            }
        }, () -> logger.warn("No rate product found for location {} and country {}", locationCode, countryCode));

        // Placeholder: add additional logic for setting response
        return response;
    }

    double calculateEstimate(ClassCode classCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        long totalDays = ChronoUnit.DAYS.between(pickupDateTime, returnDateTime);
        logger.info("Total rental duration: {} days", totalDays);

        // Rates
        double dayRate = classCode.getDayRate();
        double weekRate = classCode.getWeekRate();
        double monthRate = classCode.getMonthRate();
        logger.info("Day rate: {}, Week rate: {}, Month rate: {}", dayRate, weekRate, monthRate);

        // Calculate the number of full months, weeks, and remaining days
        long months = totalDays / 30;
        long remainingDaysAfterMonths = totalDays % 30;
        long weeks = remainingDaysAfterMonths / 7;
        long days = remainingDaysAfterMonths % 7;

        logger.info("Months: {}, Weeks: {}, Days: {}", months, weeks, days);

        // Calculate the total estimate
        double estimate = (months * monthRate) + (weeks * weekRate) + (days * dayRate);
        logger.info("Calculated estimate: {}", estimate);

        return estimate;
    }
}

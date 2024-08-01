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
        String requestedClassCode = resEstimatesDTO.getQuotedRate().getClassCode();

        logger.info("Processing reservation estimate request...");
        logger.info("Pickup location: {}, Pickup time: {}", locationCode, pickupDateTime);
        logger.info("Return location: {}, Return time: {}", resEstimatesDTO.getReturnInfo().getLocationCode(), returnDateTime);
        logger.info("Requested class code: {}", requestedClassCode);

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

            // Use Objects.equals() for safe null checks
            rateProduct.getClassCodes().stream()
                    .filter(classCode -> Objects.equals(requestedClassCode, classCode.getClassCode()))
                    .findFirst()
                    .ifPresentOrElse(classCode -> {
                        logger.info("Matching class code found: {}", classCode);

                        // Calculate the estimate
                        double estimate = calculateEstimate(classCode, pickupDateTime, returnDateTime);
                        logger.info("Estimate for class code {}: {}", classCode.getClassCode(), estimate);

                        // Create the response object
                        createResponse(response, classCode, estimate, pickupDateTime, returnDateTime);
                    }, () -> {
                        logger.warn("No matching class code found for requested class code: {}", requestedClassCode);
                        // Create a response with an empty RenterEstimate indicating failure
                        ResEstimatesResponseDTO.ResEstimate resEstimate = new ResEstimatesResponseDTO.ResEstimate();
                        ResEstimatesResponseDTO.RenterEstimate renterEstimate = new ResEstimatesResponseDTO.RenterEstimate();
                        renterEstimate.setTotal("0.00");
                        renterEstimate.setIncludedDistance("0");
                        renterEstimate.setCurrencyCode("USD");
                        renterEstimate.setCharges(Collections.emptyList());

                        resEstimate.setSuccess(false);
                        resEstimate.setRenterEstimate(renterEstimate);
                        response.setResEstimate(resEstimate);
                    });
        }, () -> {
            logger.warn("No rate product found for location {} and country {}", locationCode, countryCode);
            // Create a response with an empty RenterEstimate indicating failure
            ResEstimatesResponseDTO.ResEstimate resEstimate = new ResEstimatesResponseDTO.ResEstimate();
            ResEstimatesResponseDTO.RenterEstimate renterEstimate = new ResEstimatesResponseDTO.RenterEstimate();
            renterEstimate.setTotal("0.00");
            renterEstimate.setIncludedDistance("0");
            renterEstimate.setCurrencyCode("USD");
            renterEstimate.setCharges(Collections.emptyList());

            resEstimate.setSuccess(false);
            resEstimate.setRenterEstimate(renterEstimate);
            response.setResEstimate(resEstimate);
        });

        // Set response attributes
        response.setRegardingReferenceNumber(resEstimatesDTO.getReferenceNumber());
        response.setVersion(resEstimatesDTO.getVersion());
        response.setWebxgId(UUID.randomUUID().toString());

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

        // Log the detailed estimate breakdown
        logger.info("Estimate for class code {}: {}, with rates - Day Rate: {}, Week Rate: {}, Month Rate: {}. Quantities - Days: {}, Weeks: {}, Months: {}",
                classCode.getClassCode(), estimate, dayRate, weekRate, monthRate, days, weeks, months);

        return estimate;
    }


    private void createResponse(ResEstimatesResponseDTO response, ClassCode classCode, double estimate, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        ResEstimatesResponseDTO.ResEstimate resEstimate = new ResEstimatesResponseDTO.ResEstimate();
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = new ResEstimatesResponseDTO.RenterEstimate();

        // Set currency code, distance, etc. (hardcoded for now)
        renterEstimate.setCurrencyCode("USD");
        renterEstimate.setIncludedDistance("unlimited");

        List<ResEstimatesResponseDTO.Charge> charges = new ArrayList<>();
        long totalDays = ChronoUnit.DAYS.between(pickupDateTime, returnDateTime);

        // Calculate the number of full months, weeks, and remaining days
        long months = totalDays / 30;
        long remainingDaysAfterMonths = totalDays % 30;
        long weeks = remainingDaysAfterMonths / 7;
        long days = remainingDaysAfterMonths % 7;

        // Calculate charges and add them
        if (months > 0) {
            charges.add(new ResEstimatesResponseDTO.Charge("", "MONTHS", String.valueOf(months), String.format("%.2f", months * classCode.getMonthRate())));
        }
        if (weeks > 0) {
            charges.add(new ResEstimatesResponseDTO.Charge("", "WEEKS", String.valueOf(weeks), String.format("%.2f", weeks * classCode.getWeekRate())));
        }
        if (days > 0) {
            charges.add(new ResEstimatesResponseDTO.Charge("", "XDAYS", String.valueOf(days), String.format("%.2f", days * classCode.getDayRate())));
        }


        // Calculate the total estimate
        double totalEstimate = (months * classCode.getMonthRate()) + (weeks * classCode.getWeekRate()) + (days * classCode.getDayRate());
        renterEstimate.setTotal(String.format("%.2f", totalEstimate));

        renterEstimate.setCharges(charges);

        resEstimate.setSuccess(true);
        resEstimate.setRenterEstimate(renterEstimate);

        response.setResEstimate(resEstimate);
        logger.info("Response created: {}", response);
    }


}

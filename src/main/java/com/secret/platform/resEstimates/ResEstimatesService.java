package com.secret.platform.resEstimates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.exception.CorporateRateNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetService;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.privilege_code.PrivilegeCode;
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

    @Autowired
    OptionSetService optionSetService;

    @Autowired
    private OptionsServiceImpl optionsService;

    @Override
    public ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO) {
        logger.info("Processing reservation estimate request...");

        // Extract necessary information from the request DTO
        String locationCode = resEstimatesDTO.getPickup().getLocationCode();
        String countryCode = resEstimatesDTO.getSource();
        LocalDateTime pickupDateTime = resEstimatesDTO.getPickup().getDateTime();
        LocalDateTime returnDateTime = resEstimatesDTO.getReturnInfo().getDateTime();
        String requestedClassCode = resEstimatesDTO.getQuotedRate().getClassCode();

        logRequestDetails(locationCode, pickupDateTime, returnDateTime, requestedClassCode);

        // Initialize list for bundle charge items
        List<ResEstimatesResponseDTO.Charge> bundleChargeItems = new ArrayList<>();

        // Log the options submitted
        if (resEstimatesDTO.getOptions() != null && !resEstimatesDTO.getOptions().isEmpty()) {
            logger.info("Submitted options:");
            for (ResEstimatesDTO.Option option : resEstimatesDTO.getOptions()) {
                logger.info("Option Code: {}", option.getCode());

                // Check if the option is a bundle
                boolean isBundle = checkIfBundle(option.getCode());

                if (isBundle) {
                    logger.info("Option Code: {} is a bundle. Retrieving associated options.", option.getCode());

                    // Retrieve the optSetCode for the current option
                    String optSetCode = getOptSetCodeForOption(option.getCode());

                    if (!optSetCode.isEmpty()) {
                        // Use the new service method to find matching options
                        List<Options> matchingOptions = optionsService.findOptionsByAppendedOptSetCode(optSetCode);

                        if (matchingOptions.isEmpty()) {
                            logger.warn("No associated options found for bundle option code: {}", option.getCode());
                        } else {
                            // Add the bundle option itself as a charge item
                            Options bundleOption = optionsService.findByOptionCode(option.getCode());
                            if (bundleOption != null) {
                                ResEstimatesResponseDTO.Charge bundleCharge = new ResEstimatesResponseDTO.Charge();
                                bundleCharge.setCode(bundleOption.getOptionCode());
                                bundleCharge.setDescription(bundleOption.getLongDesc());
                                bundleCharge.setQuantity("1");
                                bundleCharge.setTotal("0.00"); // You can change this when rates are applied

                                bundleChargeItems.add(bundleCharge);
                                logger.info("Added bundle charge item for option: {}", bundleCharge);
                            }

                            for (Options matchedOption : matchingOptions) {
                                logger.info("Found matching option: {}", matchedOption.getOptionCode());

                                // Create a charge item for each associated option
                                ResEstimatesResponseDTO.Charge charge = new ResEstimatesResponseDTO.Charge();
                                charge.setCode(matchedOption.getOptionCode());
                                charge.setDescription(matchedOption.getLongDesc());
                                charge.setQuantity("1");
                                charge.setTotal("0.00"); // Total is set to 0.00 for bundle items

                                // Add to the list of bundle charge items
                                bundleChargeItems.add(charge);

                                logger.info("Created bundle charge item: {}", charge);
                            }
                            logger.info("Total {} associated options found for bundle option code {}.", matchingOptions.size(), option.getCode());
                        }
                    } else {
                        logger.warn("OptSetCode not found for bundle option code: {}", option.getCode());
                    }
                }
            }
        } else {
            logger.info("No options submitted.");
        }

        CorporateAccount corporateAccount = getCorporateAccount(resEstimatesDTO);
        CorporateContract corporateContract = corporateAccount.getCorporateContract();
        RateProduct rateProduct = getRateProduct(locationCode, countryCode, corporateContract);

        // Collect charge items from privilege codes or rate product
        List<ResEstimatesResponseDTO.Charge> chargeItems = getOptionalItems(corporateContract, rateProduct, optionSetService);

        // Add bundle charge items to the main charge items list
        chargeItems.addAll(bundleChargeItems);

        return createEstimatesResponse(resEstimatesDTO, requestedClassCode, pickupDateTime, returnDateTime, rateProduct, chargeItems);
    }

    private boolean checkIfBundle(String optionCode) {
        Options option = optionsService.findByOptionCode(optionCode);

        if (option != null) {
            logger.info("option {} is bundle {}", option.getOptionCode(), option.isBundle());
            return option.isBundle();
        }

        logger.info("option {} not found", optionCode);

        return false;
    }

    // Method to get the option set code for a given option
    private String getOptSetCodeForOption(String optionCode) {
        Options option = optionsService.findByOptionCode(optionCode);

        if (option != null) {
            logger.info("option {} set code: {} ", option.getOptionCode(), option.getOptSetCode());

            return option.getOptSetCode();
        }

        return ""; // Return an empty string instead of "not found"
    }

    private List<ResEstimatesResponseDTO.Charge> getOptionalItems(CorporateContract corporateContract, RateProduct rateProduct, OptionSetService optionSetService) {
        List<Options> optionalItems = new ArrayList<>();

        // List to store charge items
        List<ResEstimatesResponseDTO.Charge> chargeItems = new ArrayList<>();

        // Check if privilege codes exist
        if (corporateContract.getPrivilegeCodes() != null && !corporateContract.getPrivilegeCodes().isEmpty()) {
            boolean hasOptionSet = false; // Track if any privilege code has an option set

            for (PrivilegeCode privilegeCode : corporateContract.getPrivilegeCodes()) {
                // Check if optionSetCodeString is not null or empty
                if (privilegeCode.getOptionSetCodeString() != null && !privilegeCode.getOptionSetCodeString().isEmpty()) {
                    // Retrieve options using the optionSetCodeString
                    List<Options> options = optionSetService.getOptionsByOptSetCode(privilegeCode.getOptionSetCodeString());

                    if (!options.isEmpty()) {
                        hasOptionSet = true; // Mark that at least one privilege code has an option set
                        // Add options to the optionalItems list
                        optionalItems.addAll(options);
                        logger.info("Privilege code {} includes options: {}", privilegeCode.getCode(), options);

                        // Create charge items for each option
                        for (Options option : options) {
                            ResEstimatesResponseDTO.Charge charge = new ResEstimatesResponseDTO.Charge();
                            charge.setCode(option.getOptionCode());
                            charge.setDescription(option.getLongDesc()); // Assuming Options has a longDesc field
                            charge.setQuantity("1");
                            charge.setTotal("0.00"); // As specified, total is set to 0.00

                            chargeItems.add(charge);

                            logger.info("Created charge item: {}", charge);
                        }

                    } else {
                        logger.info("Privilege code {} has an option set code but no options found.", privilegeCode.getCode());
                    }
                } else {
                    logger.info("Privilege code {} has no option set code.", privilegeCode.getCode());
                }
            }

            // If no privilege code had an option set, check the rate product for option sets
            if (!hasOptionSet) {
                // Check inclOptSet and addonOptSet for options
                addOptionsFromOptionSet(rateProduct.getInclOptSet(), optionalItems, chargeItems);
                addOptionsFromOptionSet(rateProduct.getAddonOptSet(), optionalItems, chargeItems);

                if (optionalItems.isEmpty()) {
                    logger.info("Rate product {} has no options in inclOptSet or addonOptSet.", rateProduct.getProduct());
                }
            }
        } else {
            // If no privilege codes exist, directly check the rate product for option sets
            addOptionsFromOptionSet(rateProduct.getInclOptSet(), optionalItems, chargeItems);
            addOptionsFromOptionSet(rateProduct.getAddonOptSet(), optionalItems, chargeItems);

            if (optionalItems.isEmpty()) {
                logger.info("Rate product {} has no options in inclOptSet or addonOptSet.", rateProduct.getProduct());
            }
        }

        return chargeItems;
    }

    // Helper method to add options from a given OptionSet
    private void addOptionsFromOptionSet(OptionSet optionSet, List<Options> optionalItems, List<ResEstimatesResponseDTO.Charge> chargeItems) {
        if (optionSet != null) {
            List<Options> options = optionSet.getOptions();
            if (!options.isEmpty()) {
                optionalItems.addAll(options);
                logger.info("Option set {} includes options: {}", optionSet.getId(), options);

                for (Options option : options) {
                    ResEstimatesResponseDTO.Charge charge = new ResEstimatesResponseDTO.Charge();
                    charge.setCode(option.getOptionCode());
                    charge.setDescription(option.getLongDesc());
                    charge.setQuantity("1");
                    charge.setTotal("0.00"); // As specified, total is set to 0.00

                    chargeItems.add(charge);

                    logger.info("Created charge item: {}", charge);
                }

            } else {
                logger.info("Option set {} has no options.", optionSet.getId());
            }
        }
    }

    private void logRequestDetails(String locationCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, String requestedClassCode) {
        logger.info("Pickup location: {}, Pickup time: {}", locationCode, pickupDateTime);
        logger.info("Return location: {}, Return time: {}", locationCode, returnDateTime);
        logger.info("Requested class code: {}", requestedClassCode);
    }

    private CorporateAccount getCorporateAccount(ResEstimatesDTO resEstimatesDTO) {
        String corpRateID = resEstimatesDTO.getQuotedRate().getCorporateRateID();
        CorporateAccount corporateAccount = corporateAccountRepository.findByCdpId(corpRateID)
                .orElseThrow(() -> {
                    logger.error("Corporate rate not found for ID: {}", corpRateID);
                    return new CorporateRateNotFoundException(corpRateID);
                });
        logger.info("Corporate account found: {}", corporateAccount);
        return corporateAccount;
    }

    private RateProduct getRateProduct(String locationCode, String countryCode, CorporateContract corporateContract) {
        String rateProductName = corporateContract.getRateProduct();
        logger.info("Rate product name from contract: {}", rateProductName);

        return rateProductService.getSpecificRateProduct(locationCode, countryCode, rateProductName)
                .orElseThrow(() -> {
                    logger.warn("No rate product found for location {} and country {}", locationCode, countryCode);
                    return new CorporateRateNotFoundException("No rate product found");
                });
    }

    private ResEstimatesResponseDTO createEstimatesResponse(ResEstimatesDTO resEstimatesDTO, String requestedClassCode,
                                                            LocalDateTime pickupDateTime, LocalDateTime returnDateTime,
                                                            RateProduct rateProduct, List<ResEstimatesResponseDTO.Charge> chargeItems) {
        ResEstimatesResponseDTO response = new ResEstimatesResponseDTO();

        rateProduct.getClassCodes().stream()
                .filter(classCode -> Objects.equals(requestedClassCode, classCode.getClassCode()))
                .findFirst()
                .ifPresentOrElse(classCode -> {
                    logger.info("Matching class code found: {}", classCode);

                    // Calculate the estimate
                    double estimate = calculateEstimate(classCode, pickupDateTime, returnDateTime);
                    logger.info("Estimate for class code {}: {}", classCode.getClassCode(), estimate);

                    // Create the response object
                    createResponse(response, classCode, estimate, pickupDateTime, returnDateTime, chargeItems);
                }, () -> {
                    logger.warn("No matching class code found for requested class code: {}", requestedClassCode);
                    createEmptyResponse(response);
                });

        setResponseAttributes(response, resEstimatesDTO);

        return response;
    }

    private double calculateEstimate(ClassCode classCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
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

    private void createResponse(ResEstimatesResponseDTO response, ClassCode classCode, double estimate,
                                LocalDateTime pickupDateTime, LocalDateTime returnDateTime,
                                List<ResEstimatesResponseDTO.Charge> chargeItems) {
        ResEstimatesResponseDTO.ResEstimate resEstimate = new ResEstimatesResponseDTO.ResEstimate();
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = new ResEstimatesResponseDTO.RenterEstimate();

        // Set currency code, distance, etc. (hardcoded for now)
        renterEstimate.setCurrencyCode("USD");
        renterEstimate.setIncludedDistance("unlimited");

        // Calculate charges and add them
        List<ResEstimatesResponseDTO.Charge> calculatedCharges = calculateCharges(classCode, pickupDateTime, returnDateTime);
        calculatedCharges.addAll(chargeItems); // Add the optional item charges

        renterEstimate.setTotal(String.format("%.2f", estimate));
        renterEstimate.setCharges(calculatedCharges);

        resEstimate.setSuccess(true);
        resEstimate.setRenterEstimate(renterEstimate);

        response.setResEstimate(resEstimate);
        logger.info("Response created: {}", response);
    }

    private List<ResEstimatesResponseDTO.Charge> calculateCharges(ClassCode classCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
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

        return charges;
    }

    private void createEmptyResponse(ResEstimatesResponseDTO response) {
        ResEstimatesResponseDTO.ResEstimate resEstimate = new ResEstimatesResponseDTO.ResEstimate();
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = new ResEstimatesResponseDTO.RenterEstimate();
        renterEstimate.setTotal("0.00");
        renterEstimate.setIncludedDistance("0");
        renterEstimate.setCurrencyCode("USD");
        renterEstimate.setCharges(Collections.emptyList());

        resEstimate.setSuccess(false);
        resEstimate.setRenterEstimate(renterEstimate);
        response.setResEstimate(resEstimate);
    }

    private void setResponseAttributes(ResEstimatesResponseDTO response, ResEstimatesDTO resEstimatesDTO) {
        response.setRegardingReferenceNumber(resEstimatesDTO.getReferenceNumber());
        response.setVersion(resEstimatesDTO.getVersion());
        response.setWebxgId(UUID.randomUUID().toString());
    }
}
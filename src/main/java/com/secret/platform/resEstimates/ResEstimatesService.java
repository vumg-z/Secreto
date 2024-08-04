package com.secret.platform.resEstimates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeServiceImpl;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.exception.CorporateRateNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetService;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsRates;
import com.secret.platform.options.OptionsRatesService;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.pricing_code.PricingCode;
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
    private OptionSetService optionSetService;

    @Autowired
    private OptionsServiceImpl optionsService;

    @Autowired
    private OptionsRatesService optionsRatesService;

    @Autowired
    private ClassCodeServiceImpl classCodeService;

    @Override
    public ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO) {
        return null;
    }

    @Override
    public ResEstimatesResponseDTO getEstimates(ResEstimatesDTO resEstimatesDTO, String currency) {
        logger.info("Processing reservation estimate request...");

        // Extract necessary information from the request DTO
        String locationCode = resEstimatesDTO.getPickup().getLocationCode();
        String countryCode = resEstimatesDTO.getSource();
        LocalDateTime pickupDateTime = resEstimatesDTO.getPickup().getDateTime();
        LocalDateTime returnDateTime = resEstimatesDTO.getReturnInfo().getDateTime();
        String requestedClassCode = resEstimatesDTO.getQuotedRate().getClassCode();

        logRequestDetails(locationCode, pickupDateTime, returnDateTime, requestedClassCode);

        // Initialize lists for charge items
        List<ResEstimatesResponseDTO.Charge> bundleChargeItems = new ArrayList<>();
        List<ResEstimatesResponseDTO.Charge> optionChargeItems = new ArrayList<>();

        // Calculate rental duration in days
        long totalDays = ChronoUnit.DAYS.between(pickupDateTime, returnDateTime);
        logger.info("Total rental duration: {} days", totalDays);

        // Calculate the base estimate for time-related charges
        double timeRelatedSubtotal = calculateTimeRelatedCharges(resEstimatesDTO, requestedClassCode, pickupDateTime, returnDateTime);

        // Process options submitted for the estimate request
        if (resEstimatesDTO.getOptions() != null && !resEstimatesDTO.getOptions().isEmpty()) {
            logger.info("Processing submitted options for estimate request...");

            for (ResEstimatesDTO.Option option : resEstimatesDTO.getOptions()) {
                logger.info("Processing Option Code: {}", option.getCode());

                // Check if the option is a bundle
                boolean isBundle = checkIfBundle(option.getCode());
                logger.debug("Option Code: {} isBundle: {}", option.getCode(), isBundle);

                if (isBundle) {
                    processBundleOptions(option, totalDays, currency, bundleChargeItems, resEstimatesDTO);
                } else {
                    processSingleOption(option, totalDays, currency, optionChargeItems, resEstimatesDTO);
                }
            }
        } else {
            logger.info("No options submitted for estimate request.");
        }

        // Combine charge items
        List<ResEstimatesResponseDTO.Charge> allChargeItems = new ArrayList<>(bundleChargeItems);
        allChargeItems.addAll(optionChargeItems);

        // Calculate fees based on option charges (excluding time-related charges)
        List<ResEstimatesResponseDTO.Charge> feeChargeItems = calculateFees(allChargeItems, resEstimatesDTO, currency);
        allChargeItems.addAll(feeChargeItems);

        // Build the response DTO
        CorporateAccount corporateAccount = getCorporateAccount(resEstimatesDTO);
        CorporateContract corporateContract = corporateAccount.getCorporateContract();
        RateProduct rateProduct = getRateProduct(locationCode, countryCode, corporateContract);

        List<ResEstimatesResponseDTO.Charge> chargeItems = getOptionalItems(corporateContract, rateProduct, optionSetService);

        // Add all charges to the main charge items list
        chargeItems.addAll(allChargeItems);

        return createEstimatesResponse(resEstimatesDTO, requestedClassCode, pickupDateTime, returnDateTime, rateProduct, chargeItems);
    }

    private List<ResEstimatesResponseDTO.Charge> calculateFees(
            List<ResEstimatesResponseDTO.Charge> chargeItems,
            ResEstimatesDTO resEstimatesDTO,
            String currency) {

        List<ResEstimatesResponseDTO.Charge> feeChargeItems = new ArrayList<>();

        // Calculate the total of non-time-related charges (including specific option charges like FC03)
        double nonTimeRelatedTotal = chargeItems.stream()
                .filter(charge -> !(charge.getCode().equals("WEEKS") || charge.getCode().equals("XDAYS") || charge.getCode().equals("MONTHS")))
                .mapToDouble(charge -> Double.parseDouble(charge.getTotal()))
                .sum();

        logger.info("Total of non-time-related charges for fee calculation: {}", nonTimeRelatedTotal);

        // Use a map to dynamically handle fees and their calculated amounts
        Map<String, Double> feeAmounts = new HashMap<>();

        // List of fee codes to be calculated (exclude TAX for now)
        List<String> applicableFeeCodes = List.of("LOCN", "LRF");

        // Calculate each fee and store in the map
        for (String feeCode : applicableFeeCodes) {
            double feeAmount = calculateFeeForCode(feeCode, nonTimeRelatedTotal, feeChargeItems, resEstimatesDTO, currency);
            feeAmounts.put(feeCode, feeAmount);
        }

        // Calculate TAX based on the accumulated fees and non-time-related charges
        double taxableAmount = nonTimeRelatedTotal + feeAmounts.values().stream().mapToDouble(Double::doubleValue).sum();
        addTaxCharge(taxableAmount, feeChargeItems, resEstimatesDTO, currency);

        return feeChargeItems;
    }

    private double calculateFeeForCode(
            String feeCode,
            double baseAmount,
            List<ResEstimatesResponseDTO.Charge> feeChargeItems,
            ResEstimatesDTO resEstimatesDTO,
            String currency) {

        Options feeOption = optionsService.findByOptionCode(feeCode);
        if (feeOption != null) {
            OptionsRates feeRates = optionsRatesService.findRatesByCriteriaAndCurrency(
                    feeCode,
                    "DF",
                    "DF",
                    "DF"
            ).stream().findFirst().orElse(null);

            if (feeRates != null) {
                double feeRate = feeRates.getPrimaryRate();
                double feeAmount = baseAmount * (feeRate / 100); // Assuming fee is percentage-based
                logger.info("Calculated fee for {}: FeeRate={}%, FeeAmount={}", feeCode, feeRate, feeAmount);

                ResEstimatesResponseDTO.Charge feeCharge = new ResEstimatesResponseDTO.Charge();
                feeCharge.setCode(feeCode);
                feeCharge.setDescription(feeOption.getLongDesc());
                feeCharge.setQuantity("1");
                feeCharge.setTotal(String.format("%.2f", feeAmount));

                feeChargeItems.add(feeCharge);
                logger.info("Added fee charge item for option: {}", feeCharge);

                return feeAmount;
            } else {
                logger.warn("No rates found for fee option: {}", feeCode);
            }
        } else {
            logger.warn("No fee option found for code: {}", feeCode);
        }
        return 0.0;
    }

    private void addTaxCharge(
            double taxableAmount,
            List<ResEstimatesResponseDTO.Charge> feeChargeItems,
            ResEstimatesDTO resEstimatesDTO,
            String currency) {

        String feeCode = "TAX";
        Options feeOption = optionsService.findByOptionCode(feeCode);
        if (feeOption != null) {
            OptionsRates feeRates = optionsRatesService.findRatesByCriteriaAndCurrency(
                    feeCode,
                    "DF",
                    "DF",
                    "DF"
            ).stream().findFirst().orElse(null);

            if (feeRates != null) {
                double feeRate = feeRates.getPrimaryRate();
                double feeAmount = taxableAmount * (feeRate / 100); // Assuming fee is percentage-based
                logger.info("Calculated TAX for {}: FeeRate={}%, FeeAmount={}", feeCode, feeRate, feeAmount);

                ResEstimatesResponseDTO.Charge feeCharge = new ResEstimatesResponseDTO.Charge();
                feeCharge.setCode(feeCode);
                feeCharge.setDescription(feeOption.getLongDesc());
                feeCharge.setQuantity("1");
                feeCharge.setTotal(String.format("%.2f", feeAmount));

                feeChargeItems.add(feeCharge);
                logger.info("Added TAX charge item for option: {}", feeCharge);
            } else {
                logger.warn("No rates found for TAX option: {}", feeCode);
            }
        } else {
            logger.warn("No TAX option found for code: {}", feeCode);
        }
    }

    private void processBundleOptions(ResEstimatesDTO.Option option, long totalDays, String currency, List<ResEstimatesResponseDTO.Charge> bundleChargeItems, ResEstimatesDTO resEstimatesDTO) {
        // Retrieve the optSetCode for the current option
        String optSetCode = getOptSetCodeForOption(option.getCode());
        logger.debug("OptSetCode for Option Code {}: {}", option.getCode(), optSetCode);

        if (!optSetCode.isEmpty()) {
            List<Options> matchingOptions = optionsService.findOptionsByAppendedOptSetCode(optSetCode);
            logger.debug("Found {} matching options for bundle with Option Code: {}", matchingOptions.size(), option.getCode());

            if (matchingOptions.isEmpty()) {
                logger.warn("No associated options found for bundle option code: {}", option.getCode());
            } else {
                // Add the bundle option itself as a charge item
                Options bundleOption = optionsService.findByOptionCode(option.getCode());
                if (bundleOption != null) {
                    addOptionCharge(bundleOption, totalDays, currency, bundleChargeItems, resEstimatesDTO);
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

    private void processSingleOption(ResEstimatesDTO.Option option, long totalDays, String currency, List<ResEstimatesResponseDTO.Charge> optionChargeItems, ResEstimatesDTO resEstimatesDTO) {
        Options optionDetail = optionsService.findByOptionCode(option.getCode());
        if (optionDetail != null) {
            logger.info("Processing non-bundle option charge for: {}", option.getCode());
            addOptionCharge(optionDetail, totalDays, currency, optionChargeItems, resEstimatesDTO);
        } else {
            logger.warn("No option details found for option code: {}", option.getCode());
        }
    }

    private void addOptionCharge(Options optionDetail, long totalDays, String currency, List<ResEstimatesResponseDTO.Charge> chargeItems, ResEstimatesDTO resEstimatesDTO) {
        OptionsRates optionRates = optionsRatesService.findRatesByCriteriaAndCurrency(
                optionDetail.getOptionCode(),
                getPrivilegeCodeFromCorporateRateID(resEstimatesDTO.getQuotedRate().getCorporateRateID()),
                getPricingCodeFromClassCode(resEstimatesDTO.getQuotedRate().getClassCode()),
                currency
        ).stream().findFirst().orElse(null);

        if (optionRates != null) {
            double ratePerDay = optionRates.getPrimaryRate();
            double totalOptionCharge = ratePerDay * totalDays;
            logger.info("Rate per day for option {}: {}, Total charge: {}", optionDetail.getOptionCode(), ratePerDay, totalOptionCharge);

            ResEstimatesResponseDTO.Charge optionCharge = new ResEstimatesResponseDTO.Charge();
            optionCharge.setCode(optionDetail.getOptionCode());
            optionCharge.setDescription(optionDetail.getLongDesc());
            optionCharge.setQuantity(String.valueOf(totalDays));
            optionCharge.setTotal(String.format("%.2f", totalOptionCharge));

            chargeItems.add(optionCharge);
            logger.info("Added option charge item for option: {}", optionCharge);
        } else {
            logger.warn("No rates found for option: {}", optionDetail.getOptionCode());
        }
    }

    private List<ResEstimatesResponseDTO.Charge> calculateFees(List<ResEstimatesResponseDTO.Charge> chargeItems) {
        List<ResEstimatesResponseDTO.Charge> feeChargeItems = new ArrayList<>();

        // Calculate the total of non-time-related charges (including specific option charges like FC03)
        double nonTimeRelatedTotal = chargeItems.stream()
                .filter(charge -> !(charge.getCode().equals("WEEKS") || charge.getCode().equals("XDAYS") || charge.getCode().equals("MONTHS")))
                .mapToDouble(charge -> Double.parseDouble(charge.getTotal()))
                .sum();

        logger.info("Total of non-time-related charges for fee calculation: {}", nonTimeRelatedTotal);

        // Use a map to dynamically handle fees and their calculated amounts
        Map<String, Double> feeAmounts = new HashMap<>();

        // List of fee codes to be calculated
        List<String> applicableFeeCodes = List.of("LOCN", "LRF");

        // Calculate each fee and store in the map
        for (String feeCode : applicableFeeCodes) {
            double feeAmount = calculateFeeForCode(feeCode, nonTimeRelatedTotal, feeChargeItems);
            feeAmounts.put(feeCode, feeAmount);
        }

        // Calculate TAX based on the accumulated fees and specific option charges
        double taxableAmount = nonTimeRelatedTotal + feeAmounts.values().stream().mapToDouble(Double::doubleValue).sum();
        calculateTaxFee(taxableAmount, feeChargeItems);

        return feeChargeItems;
    }

    private double calculateFeeForCode(String feeCode, double baseAmount, List<ResEstimatesResponseDTO.Charge> feeChargeItems) {
        Options feeOption = optionsService.findByOptionCode(feeCode);
        if (feeOption != null) {
            OptionsRates feeRates = optionsRatesService.findRatesByCriteriaAndCurrency(
                    feeCode,
                    "DF", // Replace "DF" with the appropriate code or logic
                    "DF",
                    "DF"
            ).stream().findFirst().orElse(null);

            if (feeRates != null) {
                double feeRate = feeRates.getPrimaryRate();
                double feeAmount = baseAmount * (feeRate / 100); // Assuming fee is percentage-based
                logger.info("Calculated fee for {}: FeeRate={}%, FeeAmount={}", feeCode, feeRate, feeAmount);

                ResEstimatesResponseDTO.Charge feeCharge = new ResEstimatesResponseDTO.Charge();
                feeCharge.setCode(feeCode);
                feeCharge.setDescription(feeOption.getLongDesc());
                feeCharge.setQuantity("1");
                feeCharge.setTotal(String.format("%.2f", feeAmount));

                feeChargeItems.add(feeCharge);
                logger.info("Added fee charge item for option: {}", feeCharge);

                return feeAmount;
            } else {
                logger.warn("No rates found for fee option: {}", feeCode);
            }
        } else {
            logger.warn("No fee option found for code: {}", feeCode);
        }
        return 0.0;
    }

    private void calculateTaxFee(double taxableAmount, List<ResEstimatesResponseDTO.Charge> feeChargeItems) {
        String feeCode = "TAX";
        Options feeOption = optionsService.findByOptionCode(feeCode);
        if (feeOption != null) {
            OptionsRates feeRates = optionsRatesService.findRatesByCriteriaAndCurrency(
                    feeCode,
                    "DF", // Replace "DF" with the appropriate code or logic
                    "DF",
                    "DF"
            ).stream().findFirst().orElse(null);

            if (feeRates != null) {
                double feeRate = feeRates.getPrimaryRate();
                double feeAmount = taxableAmount * (feeRate / 100); // Assuming fee is percentage-based
                logger.info("Calculated TAX for {}: FeeRate={}%, FeeAmount={}", feeCode, feeRate, feeAmount);

                ResEstimatesResponseDTO.Charge feeCharge = new ResEstimatesResponseDTO.Charge();
                feeCharge.setCode(feeCode);
                feeCharge.setDescription(feeOption.getLongDesc());
                feeCharge.setQuantity("1");
                feeCharge.setTotal(String.format("%.2f", feeAmount));

                feeChargeItems.add(feeCharge);
                logger.info("Added TAX charge item for option: {}", feeCharge);
            } else {
                logger.warn("No rates found for TAX option: {}", feeCode);
            }
        } else {
            logger.warn("No TAX option found for code: {}", feeCode);
        }
    }

    private String getPricingCodeFromClassCode(String classCode) {
        logger.debug("Fetching pricing code for classCode: {}", classCode);
        Optional<ClassCode> classCodeEntity = classCodeService.findByClassCode(classCode);
        if (classCodeEntity.isPresent()) {
            PricingCode pricingCode = classCodeEntity.get().getPricingCode();
            if (pricingCode != null) {
                String code = pricingCode.getCode();
                logger.debug("Found pricing code: {}", code);
                return code;
            } else {
                logger.debug("No pricing code associated with classCode: {}", classCode);
            }
        } else {
            logger.debug("No class code found for classCode: {}", classCode);
        }
        return null;
    }

    private String getPrivilegeCodeFromCorporateRateID(String corporateRateID) {
        logger.debug("Fetching privilege code for corporateRateID: {}", corporateRateID);
        Optional<CorporateAccount> corporateAccountOpt = corporateAccountRepository.findByCdpId(corporateRateID);

        if (corporateAccountOpt.isPresent()) {
            CorporateContract corporateContract = corporateAccountOpt.get().getCorporateContract();

            if (corporateContract != null) {
                for (PrivilegeCode privilegeCode : corporateContract.getPrivilegeCodes()) {
                    if (privilegeCode.getOptionSet() == null) {
                        logger.debug("Found privilege code with null optionSet: {}", privilegeCode.getCode());
                        return privilegeCode.getCode();
                    }
                }
                logger.debug("No privilege code found with null optionSet for corporateRateID: {}", corporateRateID);
            } else {
                logger.debug("No corporate contract found for corporateRateID: {}", corporateRateID);
            }
        } else {
            logger.debug("No corporate account found for corporateRateID: {}", corporateRateID);
        }
        return null;
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
        // if its bundle then it should get the property of the option,

        if (option != null) {
            logger.info("option {} set code: {} ", option.getOptionCode(), option.getOptSetCode());

            // PFC03 this will return for xample
            return option.getOptSetCode();
        }

        return "no found";
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
                            charge.setDescription(option.getLongDesc());
                            charge.setQuantity("1");
                            charge.setTotal("0.00");

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
                    charge.setTotal("0.00");

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

    private double calculateTimeRelatedCharges(ResEstimatesDTO resEstimatesDTO, String requestedClassCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        // This method calculates the base time-related charges using the class code
        RateProduct rateProduct = getRateProduct(
                resEstimatesDTO.getPickup().getLocationCode(),
                resEstimatesDTO.getSource(),
                getCorporateAccount(resEstimatesDTO).getCorporateContract()
        );

        double timeRelatedSubtotal = rateProduct.getClassCodes().stream()
                .filter(classCode -> Objects.equals(requestedClassCode, classCode.getClassCode()))
                .mapToDouble(classCode -> calculateEstimate(classCode, pickupDateTime, returnDateTime))
                .sum();

        logger.info("Time-related subtotal: {}", timeRelatedSubtotal);
        return timeRelatedSubtotal;
    }

    private boolean isPercentageBasedOption(Options option) {
        return option.isFee();
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

        // Log calculated charges
        logger.info("Calculated base charges: {}", calculatedCharges);
        logger.info("Additional charge items: {}", chargeItems);

        // Calculate total estimate including bundle charges
        // Exclude base estimate from calculated charges, only sum optional and bundle charges
        double optionalAndBundleCharges = chargeItems.stream().mapToDouble(charge -> {
            double total = Double.parseDouble(charge.getTotal());
            logger.info("Processing charge: Code={}, Desc={}, Quantity={}, Total={}", charge.getCode(), charge.getDescription(), charge.getQuantity(), total);
            return total;
        }).sum();

        double totalEstimate = estimate + optionalAndBundleCharges;

        // Log the estimate breakdown
        logger.info("Base estimate: {}", estimate);
        logger.info("Total charges from items (excluding base estimate): {}", optionalAndBundleCharges);
        logger.info("Final total estimate (base + optional charges): {}", totalEstimate);

        renterEstimate.setTotal(String.format("%.2f", totalEstimate));
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
            logger.info("Adding month-related charge item: {} months at rate {}, Total: {}", months, classCode.getMonthRate(), months * classCode.getMonthRate());
        }
        if (weeks > 0) {
            charges.add(new ResEstimatesResponseDTO.Charge("", "WEEKS", String.valueOf(weeks), String.format("%.2f", weeks * classCode.getWeekRate())));
            logger.info("Adding week-related charge item: {} weeks at rate {}, Total: {}", weeks, classCode.getWeekRate(), weeks * classCode.getWeekRate());
        }
        if (days > 0) {
            charges.add(new ResEstimatesResponseDTO.Charge("", "XDAYS", String.valueOf(days), String.format("%.2f", days * classCode.getDayRate())));
            logger.info("Adding day-related charge item: {} days at rate {}, Total: {}", days, classCode.getDayRate(), days * classCode.getDayRate());
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

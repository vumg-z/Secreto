package com.secret.platform.options;

import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeRepository;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.privilege_code.PrivilegeCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionsRatesService {

    private static final Logger logger = LoggerFactory.getLogger(OptionsRatesService.class);


    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionsRatesRepository optionsRatesRepository;

    @Autowired
    private PricingCodeRepository pricingCodeRepository;

    @Autowired
    private PrivilegeCodeRepository privilegeCodeRepository;

    public OptionsRates addRateToOption(String optionCode, String locationCode, String currency, Double primaryRate, Double weeklyRate, Double monthlyRate, Double xdayRate, String pricingCodeId, String privilegeCodeId) {
        // Validate that the option exists
        if (optionsRepository.findByOptionCode(optionCode).isEmpty()) {
            throw new IllegalArgumentException("Option with code " + optionCode + " does not exist.");
        }

        // Validate that the pricing code exists
        PricingCode pricingCode = pricingCodeRepository.findByCode(pricingCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Pricing code with id " + pricingCodeId + " does not exist."));

        // Validate that the privilege code exists
        PrivilegeCode privilegeCode = privilegeCodeRepository.findByCode(privilegeCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Privilege code with id " + privilegeCodeId + " does not exist."));

        // Create and save the new rate
        OptionsRates newRate = OptionsRates.builder()
                .optionCode(optionCode)
                .locationCode(locationCode)
                .currency(currency)
                .primaryRate(primaryRate)
                .weeklyRate(weeklyRate)
                .monthlyRate(monthlyRate)
                .xdayRate(xdayRate)
                .pricingCode(pricingCode)
                .privilegeCode(privilegeCode)
                .build();

        return optionsRatesRepository.save(newRate);
    }

    public List<OptionsRates> findRatesByCriteria(String optionCode, String privilegeCodeId, String pricingCodeId) {
        logger.debug("Finding rates for optionCode: {}, privilegeCodeId: {}, pricingCodeId: {}", optionCode, privilegeCodeId, pricingCodeId);

        PrivilegeCode privilegeCode = privilegeCodeRepository.findByCode(privilegeCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Privilege code with id " + privilegeCodeId + " does not exist."));
        logger.debug("Found privilege code: {}", privilegeCode);

        PricingCode pricingCode = pricingCodeRepository.findByCode(pricingCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Pricing code with id " + pricingCodeId + " does not exist."));
        logger.debug("Found pricing code: {}", pricingCode);

        List<OptionsRates> rates = optionsRatesRepository.findByOptionCodeAndPrivilegeCodeAndPricingCode(optionCode, privilegeCode, pricingCode);
        logger.debug("Found {} rates for optionCode: {}, privilegeCode: {}, pricingCode: {}", rates.size(), optionCode, privilegeCode, pricingCode);
        return rates;
    }

    public List<OptionsRates> findRatesByCriteriaAndCurrency(String optionCode, String privilegeCodeId, String pricingCodeId, String currency) {
        logger.debug("Finding rates for optionCode: {}, privilegeCodeId: {}, pricingCodeId: {}, currency: {}", optionCode, privilegeCodeId, pricingCodeId, currency);

        PrivilegeCode privilegeCode = privilegeCodeRepository.findByCode(privilegeCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Privilege code with id " + privilegeCodeId + " does not exist."));
        logger.debug("Found privilege code: {}", privilegeCode);

        PricingCode pricingCode = pricingCodeRepository.findByCode(pricingCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Pricing code with id " + pricingCodeId + " does not exist."));
        logger.debug("Found pricing code: {}", pricingCode);

        List<OptionsRates> rates = optionsRatesRepository.findByOptionCodeAndPrivilegeCodeAndPricingCodeAndCurrency(optionCode, privilegeCode, pricingCode, currency);
        logger.debug("Found {} rates for optionCode: {}, privilegeCode: {}, pricingCode: {}, currency: {}", rates.size(), optionCode, privilegeCode, pricingCode, currency);
        return rates;
    }
}

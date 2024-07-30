package com.secret.platform.resOptions;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeServiceImpl;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsRates;
import com.secret.platform.options.OptionsRatesService;
import com.secret.platform.options.OptionsServiceInterface;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.resOptions.ResOptionsDTO;
import com.secret.platform.resOptions.ResOptionsResponseDTO;
import com.secret.platform.resOptions.ResOptionsServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResOptionsService implements ResOptionsServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ResOptionsService.class);


    @Autowired
    private OptionsServiceInterface optionsService;

    @Autowired
    private OptionsRatesService optionsRatesService;

    @Autowired
    private ClassCodeServiceImpl classCodeService;

    @Autowired
    private CorporateAccountRepository corporateAccountRepository;

    @Override
    public ResOptionsResponseDTO getOptions(ResOptionsDTO resOptionsDTO) {
        logger.debug("Received request for options: {}", resOptionsDTO);

        // Extract classCode and corporateRateID from the request
        String classCode = resOptionsDTO.getQuotedRate().getClassCode();
        String corporateRateID = resOptionsDTO.getQuotedRate().getCorporateRateID();
        logger.debug("Extracted classCode: {} and corporateRateID: {}", classCode, corporateRateID);

        // Fetch all options that are visible to the ResOptions API
        List<Options> allRelevantOptions = optionsService.resOptionsGetAll();
        logger.debug("Fetched {} relevant options based on webResVisible", allRelevantOptions.size());

        // Fetch pricingCode and privilegeCode
        String pricingCode = getPricingCodeFromClassCode(classCode); // Placeholder method
        String privilegeCode = getPrivilegeCodeFromCorporateRateID(corporateRateID); // Placeholder method
        logger.debug("Determined pricingCode: {} and privilegeCode: {}", pricingCode, privilegeCode);

        // Fetch rates for each option
        List<ResOptionsResponseDTO.Option> optionDTOList = allRelevantOptions.stream().map(option -> {
            logger.debug("Fetching rates for option code: {}", option.getOptionCode());
            OptionsRates rates = optionsRatesService.findRatesByCriteria(
                    option.getOptionCode(),
                    privilegeCode,
                    pricingCode
            ).stream().findFirst().orElse(null); // Fetching the first match for simplicity

            logger.debug("Rates found for option code {}: {}", option.getOptionCode(), rates);
            return transformOption(option, rates);
        }).collect(Collectors.toList());


        logger.debug("Transformed options list size: {}", optionDTOList.size());

        // Build response
        ResOptionsResponseDTO response = new ResOptionsResponseDTO();
        response.setRegardingReferenceNumber("12345");
        response.setVersion("1.0");
        response.setWebxgId("webxg_001");

        ResOptionsResponseDTO.ResOptions resOptions = new ResOptionsResponseDTO.ResOptions();
        resOptions.setSuccess(true);
        resOptions.setOptions(optionDTOList);

        response.setResOptions(resOptions);
        logger.debug("Built response: {}", response);

        return response;
    }


    private ResOptionsResponseDTO.Option transformOption(Options option, OptionsRates rates) {
        ResOptionsResponseDTO.Option optionDTO = new ResOptionsResponseDTO.Option();
        optionDTO.setCode(option.getOptionCode());
        optionDTO.setDesc(option.getShortDesc());
        optionDTO.setRate(rates != null ? rates.getPrimaryRate() : 0);
        optionDTO.setWeekRate(rates != null ? rates.getWeeklyRate() : 0);
        optionDTO.setMonthRate(rates != null ? rates.getMonthlyRate() : 0);
        optionDTO.setXdayRate(rates != null ? rates.getXdayRate() : 0);
        optionDTO.setRateType("Daily");
        optionDTO.setAvailable(true); // Assuming all options are available
        optionDTO.setLiability(50000.0); // Just a placeholder, replace with actual logic
        return optionDTO;
    }

    public String getPricingCodeFromClassCode(String classCode) {
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

}

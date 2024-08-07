package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.option_set.OptionSetService;
import com.secret.platform.rate_product.RateProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionsServiceImpl implements OptionsServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);
    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionSetRepository optionSetRepository;

    @Autowired
    private OptionSetService optionSetService;  // Add this



    @Override
    public List<Options> getAllOptions() {
        return optionsRepository.findAll();
    }

    @Override
    public List<Options> resOptionsGetAll() {
        List<String> visibilityFlags = Arrays.asList("Y", "O", "H");
        return optionsRepository.findByWebResVisibleIn(visibilityFlags);
    }

    @Override
    public List<Options> findOptionsByAppendedOptSetCode(String optSetCode) {
        return optionsRepository.findOptionsByOptSetCodeAppended(optSetCode);
    }
    @Override
    public Optional<Options> getOptionById(Long id) {
        return optionsRepository.findById(id);
    }

    @Override
    public Options createOption(Options option) {
        // Check if the option already exists
        Optional<Options> existingOptionOptional = optionsRepository.findByOptionCode(option.getOptionCode());

        if (existingOptionOptional.isPresent()) {
            Options existingOption = existingOptionOptional.get();

            // If optSetCode is different, append it to the optSetCodeAppended list
            String newOptSetCode = option.getOptSetCode();
            if (newOptSetCode != null && !newOptSetCode.equals(existingOption.getOptSetCode()) && !existingOption.getOptSetCodeAppended().contains(newOptSetCode)) {
                existingOption.getOptSetCodeAppended().add(newOptSetCode);

                // Save the updated existing option
                Options updatedOption = optionsRepository.save(existingOption);

                // Handle OptionSet logic
                OptionSet optionSet = optionSetService.findOrCreateOptionSetByCode(newOptSetCode);
                optionSet.getOptions().add(updatedOption);
                optionSetRepository.save(optionSet);

                return updatedOption;
            } else {
                return existingOption;
            }
        } else {
            // Save the new option
            Options savedOption = optionsRepository.save(option);

            // Handle OptionSet logic
            if (option.getOptSetCode() != null) {
                OptionSet optionSet = optionSetService.findOrCreateOptionSetByCode(option.getOptSetCode());
                optionSet.getOptions().add(savedOption);
                optionSetRepository.save(optionSet);
            }

            return savedOption;
        }
    }


    @Override
    public Options updateOption(Long id, Options optionDetails) {
        Options option = optionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this id :: " + id));

        option.setOptionCode(optionDetails.getOptionCode());
        option.setShortDesc(optionDetails.getShortDesc());
        option.setLongDesc(optionDetails.getLongDesc());
        option.setTypeFlag(optionDetails.getTypeFlag());
        option.setGlAccount(optionDetails.getGlAccount());
        option.setEcho(optionDetails.getEcho());
        option.setAllowQty(optionDetails.getAllowQty());
        option.setInsOnly(optionDetails.getInsOnly());
        option.setPassThru(optionDetails.getPassThru());
        option.setRptAsRev(optionDetails.getRptAsRev());
        option.setWebResVisible(optionDetails.getWebResVisible());
        option.setDueReport(optionDetails.getDueReport());
        option.setDuePenalty(optionDetails.getDuePenalty());
        option.setExpireDate(optionDetails.getExpireDate());
        option.setInsInvPysCls(optionDetails.getInsInvPysCls());
        option.setAssetByUnit(optionDetails.getAssetByUnit());
        option.setEffBlkRmvTyp(optionDetails.getEffBlkRmvTyp());
        option.setStartDate(optionDetails.getStartDate());
        option.setLinkedOpt(optionDetails.getLinkedOpt());
        option.setRestEbdsAuthOpt(optionDetails.getRestEbdsAuthOpt());
        option.setBlk1wyMilesSeq(optionDetails.getBlk1wyMilesSeq());
        option.setModifiedDate(optionDetails.getModifiedDate());
        option.setModifiedTime(optionDetails.getModifiedTime());
        option.setModifiedEmployee(optionDetails.getModifiedEmployee());
        option.setDayOfWeekPricing(optionDetails.getDayOfWeekPricing());
        option.setUseGoldOptSetQtyParts(optionDetails.getUseGoldOptSetQtyParts());
        option.setEstAsgOptPriAgtRls(optionDetails.getEstAsgOptPriAgtRls());
        option.setOptSetCode(optionDetails.getOptSetCode());

        return optionsRepository.save(option);
    }
    @Override
    public void deleteOption(Long id) {
        Options option = optionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this id :: " + id));

        optionsRepository.delete(option);
    }

    @Override
    public Options findByOptionCode(String optionCode) {
        return optionsRepository.findByOptionCode(optionCode)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this option code :: " + optionCode));
    }

    public void deleteOptionByCode(String optionCode) {
        Options option = optionsRepository.findByOptionCode(optionCode)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this option code :: " + optionCode));

        optionsRepository.delete(option);
    }

    @Override
    public List<Options> findByOptSetCode(String optSetCode) {
        return optionsRepository.findByOptSetCode(optSetCode);
    }

    public List<Options> searchByFeesAppliedToOptCode(String optionCode) {
        logger.info("Searching for fee-based options applicable to option code: {}", optionCode);

        // Log all options before filtering
        List<Options> allOptions = optionsRepository.findAll();
        logger.info("Total options loaded for evaluation: {}", allOptions.size());
        allOptions.forEach(option ->
                logger.info("Loaded option: {}, isFee: {}, applicableOptionsCodes: {}",
                        option.getOptionCode(), option.isFee(), option.getApplicableOptionsCodes()));

        List<Options> feeOptions = optionsRepository.findFeeOptionsByApplicableCode(optionCode);

        if (feeOptions.isEmpty()) {
            logger.warn("No fee-based options found for option code: {}", optionCode);
        } else {
            logger.info("Found {} fee-based options applicable to option code: {}", feeOptions.size(), optionCode);
            feeOptions.forEach(feeOption ->
                    logger.info("Fee option found: {}, Description: {}", feeOption.getOptionCode(), feeOption.getLongDesc()));
        }
        return feeOptions;
    }





}

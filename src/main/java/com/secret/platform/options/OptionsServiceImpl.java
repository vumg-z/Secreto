package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.option_set.OptionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionsServiceImpl implements OptionsServiceInterface {

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
    public Optional<Options> getOptionById(Long id) {
        return optionsRepository.findById(id);
    }

    @Override
    public Options createOption(Options option) {
        Options savedOption = optionsRepository.save(option);
        if (option.getOptSetCode() != null) {
            OptionSet optionSet = optionSetService.findOrCreateOptionSetByCode(option.getOptSetCode());
            optionSet.getOptions().add(savedOption);
            optionSetRepository.save(optionSet);
        }
        return savedOption;
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
}

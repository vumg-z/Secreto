package com.secret.platform.option_set;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OptionSetService {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionSetRepository optionSetRepository;

    public OptionSet createOptionSet(OptionSet optionSet) {
        return optionSetRepository.save(optionSet);
    }

    public List<OptionSet> getAllOptionSets() {
        return optionSetRepository.findAll();
    }

    public OptionSet getOptionSetById(Long id) {
        return optionSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OptionSet not found for this id :: " + id));
    }

    public OptionSet updateOptionSet(Long id, OptionSet optionSetDetails) {
        OptionSet optionSet = optionSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OptionSet not found for this id :: " + id));

        optionSet.setCode(optionSetDetails.getCode());
        optionSet.setEffDate(optionSetDetails.getEffDate());
        optionSet.setTermDate(optionSetDetails.getTermDate());
        optionSet.setCrDateEmpl(optionSetDetails.getCrDateEmpl());
        optionSet.setModDateEmpl(optionSetDetails.getModDateEmpl());
        optionSet.setOptions(optionSetDetails.getOptions());

        return optionSetRepository.save(optionSet);
    }

    public void deleteOptionSet(Long id) {
        OptionSet optionSet = optionSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OptionSet not found for this id :: " + id));
        optionSetRepository.delete(optionSet);
    }

    public List<Options> getOptionsByOptSetCode(String optSetCode) {
        return optionsRepository.findByOptSetCode(optSetCode);
    }

    public OptionSet addOptionsToOptionSet(Long id, List<Options> options) {
        OptionSet optionSet = getOptionSetById(id);
        optionSet.getOptions().addAll(options);
        return optionSetRepository.save(optionSet);
    }

    public OptionSet findOrCreateOptionSetByCode(String optSetCode) {
        return optionSetRepository.findByCode(optSetCode)
                .orElseGet(() -> {
                    OptionSet newOptionSet = OptionSet.builder()
                            .code(optSetCode)
                            .effDate(new Date())
                            .build();
                    return optionSetRepository.save(newOptionSet);
                });
    }
}

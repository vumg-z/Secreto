package com.secret.platform.options;

import java.util.List;
import java.util.Optional;

public interface OptionsServiceInterface {
    List<Options> getAllOptions();
    Optional<Options> getOptionById(Long id);
    Options createOption(Options option);
    Options updateOption(Long id, Options optionDetails);
    void deleteOption(Long id);
    Options findByOptionCode(String optionCode);

    List<Options> findByOptSetCode(String optSetCode);
}

package com.secret.platform.rate_product;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RateProductServiceImpl implements RateProductService {

    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);

    @Autowired
    private RateProductRepository rateProductRepository;

    @Autowired
    private OptionsServiceImpl optionsService;

    @Autowired
    private OptionSetRepository optionSetRepository;

    @Override
    public List<RateProduct> getAllRateProducts() {
        return null;
    }

    @Override
    public Optional<RateProduct> getRateProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public RateProduct createRateProduct(RateProduct rateProduct, Map<String, Boolean> coverages) {
        logger.debug("Creating RateProduct with coverages: {}", coverages);

        Set<Options> includedOptionsSet = new HashSet<>();

        updateIncludedOptions(rateProduct, coverages, includedOptionsSet);

        if (rateProduct.getInclOptSet() != null) {
            logger.debug("Fetching OptionSet with ID: {}", rateProduct.getInclOptSet().getId());
            OptionSet inclOptionSet = optionSetRepository.findById(rateProduct.getInclOptSet().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("OptionSet not found for this id :: " + rateProduct.getInclOptSet().getId()));
            logger.debug("Adding options from OptionSet to RateProduct: {}", inclOptionSet.getOptions());
            includedOptionsSet.addAll(inclOptionSet.getOptions());
        }

        rateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));

        logger.debug("Final included options before saving: {}", rateProduct.getIncludedOptions());
        RateProduct savedRateProduct = rateProductRepository.save(rateProduct);
        logger.debug("Created RateProduct: {}", savedRateProduct);
        return savedRateProduct;
    }

    @Override
    public RateProduct updateRateProduct(Long id, RateProduct rateProductDetails, Map<String, Boolean> coverages) throws IllegalAccessException {
        return null;
    }

    @Override
    public void deleteRateProduct(Long id) {

    }

    private void updateIncludedOptions(RateProduct rateProduct, Map<String, Boolean> coverages, Set<Options> includedOptionsSet) {
        logger.debug("Updating included options for RateProduct with coverages: {}", coverages);

        for (Map.Entry<String, Boolean> entry : coverages.entrySet()) {
            if (entry.getValue() != null && entry.getValue()) {
                Options option = optionsService.findByOptionCode(entry.getKey());
                if (option != null) {
                    logger.debug("Adding option to included options: {}", option);
                    includedOptionsSet.add(option);
                }
            }
        }
        logger.debug("Final included options for RateProduct after coverages: {}", includedOptionsSet);
    }
}

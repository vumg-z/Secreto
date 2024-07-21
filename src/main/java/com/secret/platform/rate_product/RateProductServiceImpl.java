package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
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

    @Autowired
    private ClassCodeRepository classCodeRepository;

    @Autowired
    private RateSetRepository rateSetRepository;

    @Override
    public List<RateProduct> getAllRateProducts() {
        return rateProductRepository.findAll();
    }

    @Override
    public Optional<RateProduct> getRateProductById(Long id) {
        return rateProductRepository.findById(id);
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
        RateProduct existingRateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        // Set all fields from rateProductDetails to existingRateProduct
        existingRateProduct.setRateSet(rateProductDetails.getRateSet());
        existingRateProduct.setProduct(rateProductDetails.getProduct());
        existingRateProduct.setEffPkupDate(rateProductDetails.getEffPkupDate());
        existingRateProduct.setEffPkupTime(rateProductDetails.getEffPkupTime());
        existingRateProduct.setMustPkupBefore(rateProductDetails.getMustPkupBefore());
        existingRateProduct.setComment(rateProductDetails.getComment());
        existingRateProduct.setRateType(rateProductDetails.getRateType());

        existingRateProduct.setInclCvg1(rateProductDetails.isInclCvg1());
        existingRateProduct.setInclCvg2(rateProductDetails.isInclCvg2());
        existingRateProduct.setInclCvg3(rateProductDetails.isInclCvg3());
        existingRateProduct.setInclCvg4(rateProductDetails.isInclCvg4());

        existingRateProduct.setUnused(rateProductDetails.getUnused());
        existingRateProduct.setMilesMeth(rateProductDetails.getMilesMeth());
        existingRateProduct.setWeek(rateProductDetails.getWeek());
        existingRateProduct.setExtraWeek(rateProductDetails.getExtraWeek());
        existingRateProduct.setFreeMilesHour(rateProductDetails.getFreeMilesHour());
        existingRateProduct.setGraceMinutes(rateProductDetails.getGraceMinutes());

        existingRateProduct.setChargeForGrace(rateProductDetails.isChargeForGrace());
        existingRateProduct.setDiscountable(rateProductDetails.isDiscountable());
        existingRateProduct.setEditable(rateProductDetails.isEditable());

        existingRateProduct.setMinDaysForWeek(rateProductDetails.getMinDaysForWeek());
        existingRateProduct.setPeriodMaxDays(rateProductDetails.getPeriodMaxDays());
        existingRateProduct.setDaysPerMonth(rateProductDetails.getDaysPerMonth());
        existingRateProduct.setCommYn(rateProductDetails.getCommYn());
        existingRateProduct.setCommCat(rateProductDetails.getCommCat());
        existingRateProduct.setInclOptSet(rateProductDetails.getInclOptSet());
        existingRateProduct.setCurrency(rateProductDetails.getCurrency());
        existingRateProduct.setPaidFreeDay(rateProductDetails.getPaidFreeDay());

        // Set audit fields
        existingRateProduct.setModDate(new Date());
        existingRateProduct.setModTime(new Date().getTime() / 1000.0f);
        existingRateProduct.setModEmpl("MOD_USER");
        existingRateProduct.setEmpl(rateProductDetails.getEmpl());

        // Update included options based on coverages
        Set<Options> includedOptionsSet = new HashSet<>();
        for (Map.Entry<String, Boolean> entry : coverages.entrySet()) {
            if (entry.getValue() != null && entry.getValue()) {
                Options option = optionsService.findByOptionCode(entry.getKey());
                if (option != null) {
                    includedOptionsSet.add(option);
                }
            }
        }
        existingRateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));

        // Load all class codes for the default location
        Location defaultLocation = new Location();
        defaultLocation.setLocationNumber("DEFAULT");
        List<ClassCode> classCodes = classCodeRepository.findAllByLocation(defaultLocation);
        existingRateProduct.setClassCodes(classCodes);

        // Save the updated rate product
        RateProduct updatedRateProduct = rateProductRepository.save(existingRateProduct);
        return updatedRateProduct;
    }

    @Override
    public void deleteRateProduct(Long id) {
        rateProductRepository.deleteById(id);
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




    public List<ClassCode> getAllClassesWithRatesByLocation(Location location) {
        List<ClassCode> classCodes = classCodeRepository.findAllByLocation(location);
        for (ClassCode classCode : classCodes) {
            List<RateProduct> rateProducts = rateProductRepository.findAllByRateSet(location.getRateSet());
            for (RateProduct rateProduct : rateProducts) {
                if (classCode.getRateProduct().equals(rateProduct)) {
                    classCode.setDayRate(rateProduct.getDayRate());
                    classCode.setWeekRate(rateProduct.getWeekRate());
                    classCode.setMonthRate(rateProduct.getMonthRate());
                    classCode.setXDayRate(rateProduct.getXDayRate());
                    classCode.setHourRate(rateProduct.getHourRate());
                }
            }
        }
        return classCodes;
    }

}


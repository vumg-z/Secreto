package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeDTO;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import com.secret.platform.type_code.ValidTypeCode;
import com.secret.platform.type_code.ValidTypeCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ValidTypeCodeRepository validTypeCodeRepository;

    // coverage codes
    private static final String CVG1_CODE = "LDW";
    private static final String CVG2_CODE = "PAI";
    private static final String CVG3_CODE = "XYZ";
    private static final String CVG4_CODE = "ABC";

    @Override
    public List<RateProduct> getAllRateProducts() {
        return rateProductRepository.findAll();
    }

    @Override
    public Optional<RateProduct> getRateProductById(Long id) {
        return rateProductRepository.findById(id);
    }

    @Override
    @Transactional
    public RateProduct createRateProduct(RateProduct rateProduct, Map<String, Boolean> coverages) {
        logger.debug("Creating RateProduct with coverages: {}", coverages);

        // Find or create RateSet by rateSetCode
        RateSet rateSet = findOrCreateRateSet(rateProduct.getRateSet().getRateSetCode());
        rateProduct.setRateSet(rateSet);

        // Find or create OptionSet by code
        OptionSet inclOptSet = findOrCreateOptionSet(rateProduct.getInclOptSet().getCode());
        rateProduct.setInclOptSet(inclOptSet);

        // Process coverages
        processCoverages(rateProduct, coverages);

        Set<Options> includedOptionsSet = new HashSet<>(rateProduct.getIncludedOptions());
        updateIncludedOptions(coverages, includedOptionsSet);

        if (rateProduct.getInclOptSet() != null) {
            logger.debug("Fetching OptionSet with code: {}", rateProduct.getInclOptSet().getCode());
            logger.debug("Adding options from OptionSet to RateProduct: {}", inclOptSet.getOptions());
            includedOptionsSet.addAll(inclOptSet.getOptions());
        }

        rateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));

        // Attach ValidTypeCode if defltRaType is provided
        if (rateProduct.getDefltRaType() != null) {
            ValidTypeCode validTypeCode = validTypeCodeRepository.findByTypeCode(rateProduct.getDefltRaType().getTypeCode())
                    .orElseThrow(() -> new ResourceNotFoundException("ValidTypeCode not found for code: " + rateProduct.getDefltRaType().getTypeCode()));
            rateProduct.setDefltRaType(validTypeCode);
        }

        logger.debug("Final included options before saving: {}", rateProduct.getIncludedOptions());
        RateProduct savedRateProduct = rateProductRepository.save(rateProduct);
        logger.debug("Created RateProduct: {}", savedRateProduct);
        return savedRateProduct;
    }

    private RateSet findOrCreateRateSet(String rateSetCode) {
        return rateSetRepository.findByRateSetCode(rateSetCode).orElseGet(() -> {
            RateSet newRateSet = new RateSet();
            newRateSet.setRateSetCode(rateSetCode);
            newRateSet.setDescription("Default description"); // Set a default or appropriate description
            return rateSetRepository.save(newRateSet);
        });
    }

    private OptionSet findOrCreateOptionSet(String code) {
        return optionSetRepository.findByCode(code).orElseGet(() -> {
            OptionSet newOptionSet = new OptionSet();
            newOptionSet.setCode(code);
            newOptionSet.setEffDate(new Date()); // Set default effective date
            newOptionSet.setTermDate(new Date()); // Set default termination date
            return optionSetRepository.save(newOptionSet);
        });
    }

    @Override
    @Transactional
    public RateProduct updateRateProduct(Long id, RateProduct rateProductDetails, Map<String, Boolean> coverages) throws IllegalAccessException {
        RateProduct existingRateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        setRateProductDetails(existingRateProduct, rateProductDetails);

        // Process coverages
        processCoverages(existingRateProduct, coverages);

        Set<Options> includedOptionsSet = new HashSet<>(existingRateProduct.getIncludedOptions());
        updateIncludedOptions(coverages, includedOptionsSet);
        existingRateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));

        // Attach ValidTypeCode if defltRaType is provided
        if (rateProductDetails.getDefltRaType() != null) {
            ValidTypeCode validTypeCode = validTypeCodeRepository.findByTypeCode(rateProductDetails.getDefltRaType().getTypeCode())
                    .orElseThrow(() -> new ResourceNotFoundException("ValidTypeCode not found for code: " + rateProductDetails.getDefltRaType().getTypeCode()));
            existingRateProduct.setDefltRaType(validTypeCode);
        }

        // Load all class codes for the default location
        Location defaultLocation = new Location();
        defaultLocation.setLocationNumber("DEFAULT");
        List<ClassCode> classCodes = classCodeRepository.findAllByLocation(defaultLocation);
        existingRateProduct.setClassCodes(classCodes);

        // Save the updated rate product
        RateProduct updatedRateProduct = rateProductRepository.save(existingRateProduct);
        logger.debug("Updated RateProduct: {}", updatedRateProduct);
        return updatedRateProduct;
    }

    private void setRateProductDetails(RateProduct existingRateProduct, RateProduct rateProductDetails) {
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
    }

    @Override
    @Transactional
    public void deleteRateProduct(Long id) {
        rateProductRepository.deleteById(id);
    }

    private void processCoverages(RateProduct rateProduct, Map<String, Boolean> coverages) {
        // Process each coverage
        rateProduct.setInclCvg1(coverages.getOrDefault("CVG1", false));
        rateProduct.setInclCvg2(coverages.getOrDefault("CVG2", false));
        rateProduct.setInclCvg3(coverages.getOrDefault("CVG3", false));
        rateProduct.setInclCvg4(coverages.getOrDefault("CVG4", false));

        // Add corresponding coverage products to included options
        Set<Options> includedOptionsSet = new HashSet<>(rateProduct.getIncludedOptions());
        if (rateProduct.getInclCvg1()) {
            includedOptionsSet.add(optionsService.findByOptionCode(CVG1_CODE));
        }
        if (rateProduct.getInclCvg2()) {
            includedOptionsSet.add(optionsService.findByOptionCode(CVG2_CODE));
        }
        if (rateProduct.getInclCvg3()) {
            includedOptionsSet.add(optionsService.findByOptionCode(CVG3_CODE));
        }
        if (rateProduct.getInclCvg4()) {
            includedOptionsSet.add(optionsService.findByOptionCode(CVG4_CODE));
        }

        rateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));
    }

    private void updateIncludedOptions(Map<String, Boolean> coverages, Set<Options> includedOptionsSet) {
        logger.debug("Updating included options with coverages: {}", coverages);

        for (Map.Entry<String, Boolean> entry : coverages.entrySet()) {
            if (entry.getValue() != null && entry.getValue()) {
                Options option = optionsService.findByOptionCode(getOptionCode(entry.getKey()));
                if (option != null) {
                    logger.debug("Adding option to included options: {}", option);
                    includedOptionsSet.add(option);
                }
            }
        }
        logger.debug("Final included options after coverages: {}", includedOptionsSet);
    }

    private String getOptionCode(String coverageKey) {
        switch (coverageKey) {
            case "CVG1":
                return CVG1_CODE;
            case "CVG2":
                return CVG2_CODE;
            case "CVG3":
                return CVG3_CODE;
            case "CVG4":
                return CVG4_CODE;
            default:
                return coverageKey;
        }
    }

    @Override
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

    @Override
    public RateProduct addClassesToRateProduct(List<ClassCodeDTO> classCodeDTOs) {
        if (classCodeDTOs.isEmpty()) {
            throw new IllegalArgumentException("Class code list cannot be empty.");
        }

        // Get the RateProduct using the rateProductNumber from the first DTO (assuming all have the same rateProductNumber)
        String rateProductNumber = classCodeDTOs.get(0).getRateProductNumber();
        RateProduct rateProduct = rateProductRepository.findByProduct(rateProductNumber)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found with product number " + rateProductNumber));

        for (ClassCodeDTO classCodeDTO : classCodeDTOs) {
            ClassCode classCode = classCodeRepository.findByClassCode(classCodeDTO.getClassCode())
                    .orElseThrow(() -> new ResourceNotFoundException("ClassCode not found with class code " + classCodeDTO.getClassCode()));

            classCode.setRateProduct(rateProduct);
            classCode.setDayRate(classCodeDTO.getDayRate());
            classCode.setWeekRate(classCodeDTO.getWeekRate());
            classCode.setMonthRate(classCodeDTO.getMonthRate());
            classCode.setXDayRate(classCodeDTO.getXDayRate());
            classCode.setHourRate(classCodeDTO.getHourRate());
            classCode.setMileRate(classCodeDTO.getMileRate());

            classCodeRepository.save(classCode);
        }

        rateProduct.getClassCodes().addAll(classCodeRepository.findByRateProduct(rateProduct));
        return rateProductRepository.save(rateProduct);
    }
}

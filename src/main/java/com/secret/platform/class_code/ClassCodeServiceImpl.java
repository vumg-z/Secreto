package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.location.LocationRepository;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassCodeServiceImpl implements ClassCodeService {

    @Autowired
    private ClassCodeRepository classCodeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PricingCodeRepository pricingCodeRepository;

    @Override
    public List<ClassCode> getAllClassCodes() {
        return classCodeRepository.findAll();
    }

    public Optional<ClassCode> findByClassCode(String classCode) {
        return classCodeRepository.findByClassCode(classCode);
    }

    @Override
    public Optional<ClassCode> getClassCodeById(Long id) {
        return classCodeRepository.findById(id);
    }

    @Override
    public ClassCode createClassCode(ClassCode classCode) {
        // Validate Location
        classCode.setLocation(validateLocation(classCode.getLocation().getLocationNumber()));

        // Validate PricingCode if it is not null
        if (classCode.getPricingCode() != null) {
            classCode.setPricingCode(validatePricingCode(classCode.getPricingCode().getCode()));
        }

        return classCodeRepository.save(classCode);
    }


    @Override
    public ClassCode updateClassCode(Long id, ClassCode classCode) {
        return classCodeRepository.findById(id)
                .map(existingClassCode -> {
                    // Set the ID and validate relationships
                    classCode.setId(id);
                    classCode.setLocation(validateLocation(classCode.getLocation().getLocationNumber()));
                    classCode.setPricingCode(validatePricingCode(classCode.getPricingCode().getCode()));
                    return classCodeRepository.save(classCode);
                })
                .orElseThrow(() -> new ResourceNotFoundException("ClassCode not found with id " + id));
    }

    @Override
    public void deleteClassCode(Long id) {
        if (classCodeRepository.existsById(id)) {
            classCodeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("ClassCode not found with id " + id);
        }
    }

    private Location validateLocation(String locationNumber) {
        return locationRepository.findByLocationNumber(locationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with locationNumber " + locationNumber));
    }

    private PricingCode validatePricingCode(String code) {
        return pricingCodeRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("PricingCode not found with code " + code));
    }
}

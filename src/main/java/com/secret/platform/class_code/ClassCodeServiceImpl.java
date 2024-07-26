package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.pricing_code.PricingCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassCodeServiceImpl implements ClassCodeService {

    @Autowired
    private ClassCodeRepository classCodeRepository;

    @Override
    public List<ClassCode> getAllClassCodes() {
        return classCodeRepository.findAll();
    }

    @Override
    public Optional<ClassCode> getClassCodeById(Long id) {
        return classCodeRepository.findById(id);
    }

    @Override
    public ClassCode createClassCode(ClassCode classCode) {
        // Validate Location and PricingCode
        validateLocation(classCode.getLocation());
        validatePricingCode(classCode.getPricingCode());

        return classCodeRepository.save(classCode);
    }

    @Override
    public ClassCode updateClassCode(Long id, ClassCode classCode) {
        return classCodeRepository.findById(id)
                .map(existingClassCode -> {
                    // Set the ID and validate relationships
                    classCode.setId(id);
                    validateLocation(classCode.getLocation());
                    validatePricingCode(classCode.getPricingCode());
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

    private void validateLocation(Location location) {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Valid Location is required");
        }
    }

    private void validatePricingCode(PricingCode pricingCode) {
        if (pricingCode == null || pricingCode.getId() == null) {
            throw new IllegalArgumentException("Valid PricingCode is required");
        }
    }
}

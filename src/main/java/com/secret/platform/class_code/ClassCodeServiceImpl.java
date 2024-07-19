package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
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
        return classCodeRepository.save(classCode);
    }

    @Override
    public ClassCode updateClassCode(Long id, ClassCode classCode) {
        return classCodeRepository.findById(id)
                .map(existingClassCode -> {
                    classCode.setId(id);
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
}

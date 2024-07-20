package com.secret.platform.type_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValidTypeCodeService {

    @Autowired
    private ValidTypeCodeRepository validTypeCodeRepository;

    public List<ValidTypeCode> getAllValidTypeCodes() {
        return validTypeCodeRepository.findAll();
    }

    public Optional<ValidTypeCode> getValidTypeCodeById(Long id) {
        return validTypeCodeRepository.findById(id);
    }

    public ValidTypeCode createValidTypeCode(ValidTypeCode validTypeCode) {
        return validTypeCodeRepository.save(validTypeCode);
    }

    public ValidTypeCode updateValidTypeCode(Long id, ValidTypeCode validTypeCodeDetails) {
        ValidTypeCode validTypeCode = validTypeCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ValidTypeCode not found for this id :: " + id));

        validTypeCode.setTypeCode(validTypeCodeDetails.getTypeCode());
        validTypeCode.setDescription(validTypeCodeDetails.getDescription());
        validTypeCode.setNote1(validTypeCodeDetails.getNote1());
        validTypeCode.setNote2(validTypeCodeDetails.getNote2());
        validTypeCode.setNote3(validTypeCodeDetails.getNote3());
        validTypeCode.setNote4(validTypeCodeDetails.getNote4());
        validTypeCode.setPostingCode(validTypeCodeDetails.getPostingCode());
        validTypeCode.setChauffeured(validTypeCodeDetails.getChauffeured());
        validTypeCode.setReqIns(validTypeCodeDetails.getReqIns());
        validTypeCode.setRaPrintLibraryNumber(validTypeCodeDetails.getRaPrintLibraryNumber());

        return validTypeCodeRepository.save(validTypeCode);
    }

    public void deleteValidTypeCode(Long id) {
        ValidTypeCode validTypeCode = validTypeCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ValidTypeCode not found for this id :: " + id));

        validTypeCodeRepository.delete(validTypeCode);
    }
}

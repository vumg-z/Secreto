package com.secret.platform.class_code;

import java.util.List;
import java.util.Optional;

public interface ClassCodeService {
    List<ClassCode> getAllClassCodes();
    Optional<ClassCode> getClassCodeById(Long id);
    ClassCode createClassCode(ClassCode classCode);
    ClassCode updateClassCode(Long id, ClassCode classCode);
    void deleteClassCode(Long id);
}

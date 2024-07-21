package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassCodeServiceImplTest {

    @Mock
    private ClassCodeRepository classCodeRepository;

    @InjectMocks
    private ClassCodeServiceImpl classCodeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClassCodes() {
        ClassCode classCode1 = new ClassCode();
        ClassCode classCode2 = new ClassCode();
        List<ClassCode> classCodeList = Arrays.asList(classCode1, classCode2);

        when(classCodeRepository.findAll()).thenReturn(classCodeList);

        List<ClassCode> result = classCodeService.getAllClassCodes();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetClassCodeById() {
        ClassCode classCode = new ClassCode();
        classCode.setId(1L);

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(classCode));

        Optional<ClassCode> result = classCodeService.getClassCodeById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testCreateClassCode() {
        ClassCode classCode = new ClassCode();
        when(classCodeRepository.save(classCode)).thenReturn(classCode);

        ClassCode result = classCodeService.createClassCode(classCode);
        assertNotNull(result);
    }

    @Test
    public void testUpdateClassCode() {
        ClassCode existingClassCode = new ClassCode();
        existingClassCode.setId(1L);

        ClassCode updatedClassCode = new ClassCode();
        updatedClassCode.setId(1L);
        updatedClassCode.setClassCode("NEW_CODE");

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(existingClassCode));
        when(classCodeRepository.save(updatedClassCode)).thenReturn(updatedClassCode);

        ClassCode result = classCodeService.updateClassCode(1L, updatedClassCode);
        assertEquals("NEW_CODE", result.getClassCode());
    }

    @Test
    public void testUpdateClassCode_NotFound() {
        ClassCode updatedClassCode = new ClassCode();
        updatedClassCode.setId(1L);

        when(classCodeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            classCodeService.updateClassCode(1L, updatedClassCode);
        });
    }

    @Test
    public void testDeleteClassCode() {
        when(classCodeRepository.existsById(1L)).thenReturn(true);

        classCodeService.deleteClassCode(1L);
        verify(classCodeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteClassCode_NotFound() {
        when(classCodeRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            classCodeService.deleteClassCode(1L);
        });
    }
}

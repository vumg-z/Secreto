package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.pricing_code.PricingCode;
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

    private ClassCode classCode;
    private Location location;
    private PricingCode pricingCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        location = Location.builder().id(1L).build();
        pricingCode = PricingCode.builder().id(1L).build();

        classCode = ClassCode.builder()
                .location(location)
                .classCode("A1")
                .description("Compact")
                .pricingCode(pricingCode)
                .build();
    }

    @Test
    public void testGetAllClassCodes() {
        ClassCode classCode1 = ClassCode.builder().id(1L).location(location).pricingCode(pricingCode).build();
        ClassCode classCode2 = ClassCode.builder().id(2L).location(location).pricingCode(pricingCode).build();
        List<ClassCode> classCodeList = Arrays.asList(classCode1, classCode2);

        when(classCodeRepository.findAll()).thenReturn(classCodeList);

        List<ClassCode> result = classCodeService.getAllClassCodes();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetClassCodeById() {
        classCode.setId(1L);

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(classCode));

        Optional<ClassCode> result = classCodeService.getClassCodeById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testCreateClassCode() {
        when(classCodeRepository.save(classCode)).thenReturn(classCode);

        ClassCode result = classCodeService.createClassCode(classCode);
        assertNotNull(result);
        verify(classCodeRepository, times(1)).save(classCode);
    }

    @Test
    public void testCreateClassCode_InvalidLocation() {
        classCode.setLocation(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classCodeService.createClassCode(classCode);
        });

        String expectedMessage = "Valid Location is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).save(any(ClassCode.class));
    }

    @Test
    public void testCreateClassCode_InvalidPricingCode() {
        classCode.setPricingCode(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classCodeService.createClassCode(classCode);
        });

        String expectedMessage = "Valid PricingCode is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).save(any(ClassCode.class));
    }

    @Test
    public void testUpdateClassCode() {
        ClassCode existingClassCode = ClassCode.builder().id(1L).location(location).pricingCode(pricingCode).build();
        ClassCode updatedClassCode = ClassCode.builder().id(1L).classCode("NEW_CODE").location(location).pricingCode(pricingCode).build();

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(existingClassCode));
        when(classCodeRepository.save(updatedClassCode)).thenReturn(updatedClassCode);

        ClassCode result = classCodeService.updateClassCode(1L, updatedClassCode);
        assertEquals("NEW_CODE", result.getClassCode());
        verify(classCodeRepository, times(1)).save(updatedClassCode);
    }

    @Test
    public void testUpdateClassCode_InvalidLocation() {
        ClassCode updatedClassCode = ClassCode.builder().id(1L).classCode("NEW_CODE").location(null).pricingCode(pricingCode).build();

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(classCode));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classCodeService.updateClassCode(1L, updatedClassCode);
        });

        String expectedMessage = "Valid Location is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).save(any(ClassCode.class));
    }

    @Test
    public void testUpdateClassCode_InvalidPricingCode() {
        ClassCode updatedClassCode = ClassCode.builder().id(1L).classCode("NEW_CODE").location(location).pricingCode(null).build();

        when(classCodeRepository.findById(1L)).thenReturn(Optional.of(classCode));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classCodeService.updateClassCode(1L, updatedClassCode);
        });

        String expectedMessage = "Valid PricingCode is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).save(any(ClassCode.class));
    }

    @Test
    public void testUpdateClassCode_NotFound() {
        ClassCode updatedClassCode = ClassCode.builder().id(1L).classCode("NEW_CODE").location(location).pricingCode(pricingCode).build();

        when(classCodeRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            classCodeService.updateClassCode(1L, updatedClassCode);
        });

        String expectedMessage = "ClassCode not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).save(any(ClassCode.class));
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

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            classCodeService.deleteClassCode(1L);
        });

        String expectedMessage = "ClassCode not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classCodeRepository, times(0)).deleteById(anyLong());
    }
}

package com.secret.platform.type_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ValidTypeCodeServiceTest {

    @InjectMocks
    private ValidTypeCodeService validTypeCodeService;

    @Mock
    private ValidTypeCodeRepository validTypeCodeRepository;

    private ValidTypeCode validTypeCode;
    private ValidTypeCode validTypeCodeCC;
    private ValidTypeCode validTypeCodeCO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validTypeCode = ValidTypeCode.builder()
                .typeCode("FC")
                .description("FULLY COMP INSURED")
                .note1("Note 1")
                .note2("Note 2")
                .note3("Note 3")
                .note4("Note 4")
                .postingCode("A")
                .chauffeured("N")
                .reqIns("N")
                .raPrintLibraryNumber("97")
                .build();

        validTypeCodeCC = ValidTypeCode.builder()
                .typeCode("CC")
                .description("CREDIT CARD INSURED")
                .note1("Note 1")
                .note2("Note 2")
                .note3("Note 3")
                .note4("Note 4")
                .postingCode("B")
                .chauffeured("N")
                .reqIns("Y")
                .raPrintLibraryNumber("98")
                .build();

        validTypeCodeCO = ValidTypeCode.builder()
                .typeCode("CO")
                .description("COMPANY OWN INSURANCE")
                .note1("Note 1")
                .note2("Note 2")
                .note3("Note 3")
                .note4("Note 4")
                .postingCode("C")
                .chauffeured("Y")
                .reqIns("N")
                .raPrintLibraryNumber("99")
                .build();
    }

    @Test
    void testCreateValidTypeCode() {
        when(validTypeCodeRepository.save(any(ValidTypeCode.class))).thenReturn(validTypeCode);

        ValidTypeCode result = validTypeCodeService.createValidTypeCode(validTypeCode);
        assertEquals(validTypeCode, result);
    }

    @Test
    void testGetValidTypeCodeByCode() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.of(validTypeCode));

        Optional<ValidTypeCode> result = validTypeCodeService.getValidTypeCodeByCode("FC");
        assertTrue(result.isPresent());
        assertEquals(validTypeCode, result.get());
    }

    @Test
    void testUpdateValidTypeCodeByCode() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.of(validTypeCode));
        when(validTypeCodeRepository.save(any(ValidTypeCode.class))).thenReturn(validTypeCode);

        ValidTypeCode updatedValidTypeCode = ValidTypeCode.builder()
                .typeCode("FC")
                .description("Updated Description")
                .note1("Updated Note 1")
                .note2("Updated Note 2")
                .note3("Updated Note 3")
                .note4("Updated Note 4")
                .postingCode("B")
                .chauffeured("Y")
                .reqIns("Y")
                .raPrintLibraryNumber("98")
                .build();

        ValidTypeCode result = validTypeCodeService.updateValidTypeCodeByCode("FC", updatedValidTypeCode);
        assertEquals(updatedValidTypeCode.getDescription(), result.getDescription());
        assertEquals(updatedValidTypeCode.getNote1(), result.getNote1());
    }

    @Test
    void testDeleteValidTypeCodeByCode() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.of(validTypeCode));

        validTypeCodeService.deleteValidTypeCodeByCode("FC");

        verify(validTypeCodeRepository, times(1)).delete(validTypeCode);
    }

    @Test
    void testGetValidTypeCodeByCode_NotFound() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.empty());

        Optional<ValidTypeCode> result = validTypeCodeService.getValidTypeCodeByCode("FC");
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteValidTypeCodeByCode_NotFound() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            validTypeCodeService.deleteValidTypeCodeByCode("FC");
        });
    }

    @Test
    void testCreateValidTypeCodeCC() {
        when(validTypeCodeRepository.save(any(ValidTypeCode.class))).thenReturn(validTypeCodeCC);

        ValidTypeCode result = validTypeCodeService.createValidTypeCode(validTypeCodeCC);
        assertEquals(validTypeCodeCC, result);
    }

    @Test
    void testCreateValidTypeCodeCO() {
        when(validTypeCodeRepository.save(any(ValidTypeCode.class))).thenReturn(validTypeCodeCO);

        ValidTypeCode result = validTypeCodeService.createValidTypeCode(validTypeCodeCO);
        assertEquals(validTypeCodeCO, result);
    }

    @Test
    void testGetValidTypeCodeCCByCode() {
        when(validTypeCodeRepository.findByTypeCode("CC")).thenReturn(Optional.of(validTypeCodeCC));

        Optional<ValidTypeCode> result = validTypeCodeService.getValidTypeCodeByCode("CC");
        assertTrue(result.isPresent());
        assertEquals(validTypeCodeCC, result.get());
    }

    @Test
    void testGetValidTypeCodeCOByCode() {
        when(validTypeCodeRepository.findByTypeCode("CO")).thenReturn(Optional.of(validTypeCodeCO));

        Optional<ValidTypeCode> result = validTypeCodeService.getValidTypeCodeByCode("CO");
        assertTrue(result.isPresent());
        assertEquals(validTypeCodeCO, result.get());
    }

}

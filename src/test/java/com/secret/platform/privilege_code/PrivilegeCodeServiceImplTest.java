package com.secret.platform.privilege_code;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
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

public class PrivilegeCodeServiceImplTest {

    @Mock
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Mock
    private OptionSetRepository optionSetRepository;

    @InjectMocks
    private PrivilegeCodeServiceImpl privilegeCodeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPrivilegeCodes() {
        PrivilegeCode privilegeCode1 = new PrivilegeCode();
        PrivilegeCode privilegeCode2 = new PrivilegeCode();
        List<PrivilegeCode> privilegeCodeList = Arrays.asList(privilegeCode1, privilegeCode2);

        when(privilegeCodeRepository.findAll()).thenReturn(privilegeCodeList);

        List<PrivilegeCode> result = privilegeCodeService.getAllPrivilegeCodes();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPrivilegeCodeById() {
        PrivilegeCode privilegeCode = new PrivilegeCode();
        privilegeCode.setId(1L);

        when(privilegeCodeRepository.findById(1L)).thenReturn(Optional.of(privilegeCode));

        Optional<PrivilegeCode> result = privilegeCodeService.getPrivilegeCodeById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testCreatePrivilegeCode() {
        PrivilegeCode privilegeCode = new PrivilegeCode();
        OptionSet optionSet = new OptionSet();
        privilegeCode.setOptionSet(optionSet);

        when(optionSetRepository.save(optionSet)).thenReturn(optionSet);
        when(privilegeCodeRepository.save(privilegeCode)).thenReturn(privilegeCode);

        PrivilegeCode result = privilegeCodeService.createPrivilegeCode(privilegeCode);
        assertNotNull(result);
    }

    @Test
    public void testUpdatePrivilegeCode() {
        PrivilegeCode existingPrivilegeCode = new PrivilegeCode();
        existingPrivilegeCode.setId(1L);

        PrivilegeCode updatedPrivilegeCode = new PrivilegeCode();
        updatedPrivilegeCode.setId(1L);
        updatedPrivilegeCode.setCode("NEW_CODE");

        OptionSet optionSet = new OptionSet();
        updatedPrivilegeCode.setOptionSet(optionSet);

        when(privilegeCodeRepository.findById(1L)).thenReturn(Optional.of(existingPrivilegeCode));
        when(privilegeCodeRepository.save(updatedPrivilegeCode)).thenReturn(updatedPrivilegeCode);

        PrivilegeCode result = privilegeCodeService.updatePrivilegeCode(1L, updatedPrivilegeCode);
        assertEquals("NEW_CODE", result.getCode());
    }

    @Test
    public void testUpdatePrivilegeCode_NotFound() {
        PrivilegeCode updatedPrivilegeCode = new PrivilegeCode();
        updatedPrivilegeCode.setId(1L);

        when(privilegeCodeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            privilegeCodeService.updatePrivilegeCode(1L, updatedPrivilegeCode);
        });
    }

    @Test
    public void testDeletePrivilegeCode() {
        when(privilegeCodeRepository.existsById(1L)).thenReturn(true);

        privilegeCodeService.deletePrivilegeCode(1L);
        verify(privilegeCodeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePrivilegeCode_NotFound() {
        when(privilegeCodeRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            privilegeCodeService.deletePrivilegeCode(1L);
        });
    }

    @Test
    public void testGetAllProductsAssociatedWithThisPrivilegeCode(){
        // the idea is to get all products related
    }
}

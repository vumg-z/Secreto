package com.secret.platform.corporate_contract;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.privilege_code.PrivilegeCodeRepository;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorporateContractServiceTest {

    @InjectMocks
    private CorporateContractService corporateContractService;

    @Mock
    private CorporateContractRepository corporateContractRepository;

    @Mock
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Mock
    private RateProductRepository rateProductRepository;

    private CorporateContract corporateContract;
    private PrivilegeCode privilegeCode;
    private RateProduct rateProduct;

    @BeforeEach
    void setUp() {
        privilegeCode = new PrivilegeCode();
        privilegeCode.setId(1L);

        rateProduct = new RateProduct();
        rateProduct.setId(1L);

        corporateContract = CorporateContract.builder()
                .id(1L)
                .contractNumber("12345")
                .privilegeCodes(Arrays.asList(privilegeCode))
                .rateProduct(rateProduct)
                .modifiedDate(LocalDate.now())
                .build();
    }

    @Test
    void testCreateCorporateContract() {
        when(privilegeCodeRepository.findById(anyLong())).thenReturn(Optional.of(privilegeCode));
        when(rateProductRepository.findById(anyLong())).thenReturn(Optional.of(rateProduct));
        when(corporateContractRepository.save(any(CorporateContract.class))).thenReturn(corporateContract);

        CorporateContract result = corporateContractService.createCorporateContract(corporateContract);

        assertNotNull(result);
        assertEquals(corporateContract.getContractNumber(), result.getContractNumber());
        verify(corporateContractRepository, times(1)).save(any(CorporateContract.class));
    }

    @Test
    void testUpdateCorporateContract() {
        when(corporateContractRepository.findById(anyLong())).thenReturn(Optional.of(corporateContract));
        when(privilegeCodeRepository.findById(anyLong())).thenReturn(Optional.of(privilegeCode));
        when(rateProductRepository.findById(anyLong())).thenReturn(Optional.of(rateProduct));
        when(corporateContractRepository.save(any(CorporateContract.class))).thenReturn(corporateContract);

        CorporateContract result = corporateContractService.updateCorporateContract(1L, corporateContract);

        assertNotNull(result);
        assertEquals(corporateContract.getContractNumber(), result.getContractNumber());
        verify(corporateContractRepository, times(1)).save(any(CorporateContract.class));
    }

    @Test
    void testUpdateCorporateContract_NotFound() {
        when(corporateContractRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            corporateContractService.updateCorporateContract(1L, corporateContract);
        });
    }

    @Test
    void testGetCorporateContractById() {
        when(corporateContractRepository.findById(anyLong())).thenReturn(Optional.of(corporateContract));

        Optional<CorporateContract> result = corporateContractService.getCorporateContractById(1L);

        assertTrue(result.isPresent());
        assertEquals(corporateContract.getContractNumber(), result.get().getContractNumber());
        verify(corporateContractRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllCorporateContracts() {
        when(corporateContractRepository.findAll()).thenReturn(Arrays.asList(corporateContract));

        List<CorporateContract> result = corporateContractService.getAllCorporateContracts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(corporateContractRepository, times(1)).findAll();
    }

    @Test
    void testDeleteCorporateContract() {
        when(corporateContractRepository.existsById(anyLong())).thenReturn(true);

        corporateContractService.deleteCorporateContract(1L);

        verify(corporateContractRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteCorporateContract_NotFound() {
        when(corporateContractRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            corporateContractService.deleteCorporateContract(1L);
        });
    }
}

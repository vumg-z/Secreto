package com.secret.platform.corporate_contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.rate_product.RateProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CorporateContractController.class)
public class CorporateContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorporateContractService corporateContractService;

    private CorporateContract corporateContract;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        PrivilegeCode privilegeCode = PrivilegeCode.builder()
                .id(1L)
                .code("PRIV1")
                .description("Privilege 1")
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .id(1L)
                .product("Rate Product 1")
                .build();

        corporateContract = CorporateContract.builder()
                .id(1L)
                .contractNumber("12345")
                .brandId("Brand1")
                .rateProduct(rateProduct)
                .privilegeCodes(Arrays.asList(privilegeCode))
                .modifiedDate(LocalDate.now())
                .build();
    }

    @Test
    void testCreateCorporateContract() throws Exception {
        when(corporateContractService.createCorporateContract(any(CorporateContract.class))).thenReturn(corporateContract);

        //mockMvc.perform(post("/api/corporate-contracts")
             //           .contentType(MediaType.APPLICATION_JSON)
               //         .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(corporateContract)))
//                .andExpect(status().isOk())
  //              .andExpect(jsonPath("$.contractNumber").value("12345"));

//        verify(corporateContractService, times(1)).createCorporateContract(any(CorporateContract.class));
    }

    @Test
    void testGetCorporateContractById_NotFound() throws Exception {
        when(corporateContractService.getCorporateContractById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/corporate-contracts/1"))
                .andExpect(status().isNotFound());

        verify(corporateContractService, times(1)).getCorporateContractById(anyLong());
    }

    @Test
    void testGetCorporateContractById() throws Exception {
        when(corporateContractService.getCorporateContractById(anyLong())).thenReturn(Optional.of(corporateContract));

        mockMvc.perform(get("/api/corporate-contracts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractNumber").value("12345"));

        verify(corporateContractService, times(1)).getCorporateContractById(anyLong());
    }

    @Test
    void testGetAllCorporateContracts() throws Exception {
        when(corporateContractService.getAllCorporateContracts()).thenReturn(Arrays.asList(corporateContract));

        mockMvc.perform(get("/api/corporate-contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contractNumber").value("12345"));

        verify(corporateContractService, times(1)).getAllCorporateContracts();
    }

    @Test
    void testUpdateCorporateContract() throws Exception {
        when(corporateContractService.updateCorporateContract(anyLong(), any(CorporateContract.class))).thenReturn(corporateContract);

    //    mockMvc.perform(put("/api/corporate-contracts/1")
  //                      .contentType(MediaType.APPLICATION_JSON)
      //                  .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(corporateContract)))
//                .andExpect(status().isOk())
          //      .andExpect(jsonPath("$.contractNumber").value("12345"));

        //verify(corporateContractService, times(1)).updateCorporateContract(anyLong(), any(CorporateContract.class));
    }

    @Test
    void testDeleteCorporateContract() throws Exception {
        doNothing().when(corporateContractService).deleteCorporateContract(anyLong());

        mockMvc.perform(delete("/api/corporate-contracts/1"))
                .andExpect(status().isNoContent());

        verify(corporateContractService, times(1)).deleteCorporateContract(anyLong());
    }
}

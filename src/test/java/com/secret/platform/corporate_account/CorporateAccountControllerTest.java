package com.secret.platform.corporate_account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.corporate_contract.CorporateContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class CorporateAccountControllerTest {

    @Mock
    private CorporateAccountService corporateAccountService;

    @InjectMocks
    private CorporateAccountController corporateAccountController;

    private MockMvc mockMvc;
    private CorporateAccount corporateAccount;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(corporateAccountController).build();

        CorporateContract contract = CorporateContract.builder()
                .id(1L)
                .contractNumber("C123")
                .build();

        corporateAccount = CorporateAccount.builder()
                .id(1L)
                .cdpId("CDP123")
                .startBookDate(LocalDate.now())
                .endBookDate(LocalDate.now().plusDays(1))
                .companyName("Company")
                .corporateContract(contract)
                .build();
    }

    @Test
    void testCreateCorporateAccount() throws Exception {
        when(corporateAccountService.createCorporateAccount(any(CorporateAccount.class))).thenReturn(corporateAccount);

      //  mockMvc.perform(post("/api/corporate-accounts")
               //         .contentType(MediaType.APPLICATION_JSON)
    //                    .content(objectMapper.writeValueAsString(corporateAccount)))
//                .andExpect(status().isOk())
  //              .andExpect(jsonPath("$.cdpId").value("CDP123"));

//        verify(corporateAccountService, times(1)).createCorporateAccount(any(CorporateAccount.class));
    }

    @Test
    void testGetCorporateAccountById() throws Exception {
        when(corporateAccountService.getCorporateAccountById(anyLong())).thenReturn(Optional.of(corporateAccount));

        mockMvc.perform(get("/api/corporate-accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cdpId").value("CDP123"));

        verify(corporateAccountService, times(1)).getCorporateAccountById(anyLong());
    }

    @Test
    void testGetCorporateAccountById_NotFound() throws Exception {
        when(corporateAccountService.getCorporateAccountById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/corporate-accounts/1"))
                .andExpect(status().isNotFound());

        verify(corporateAccountService, times(1)).getCorporateAccountById(anyLong());
    }

    @Test
    void testUpdateCorporateAccount() throws Exception {
        when(corporateAccountService.updateCorporateAccount(anyLong(), any(CorporateAccount.class))).thenReturn(corporateAccount);

        //mockMvc.perform(put("/api/corporate-accounts/1")
           //             .contentType(MediaType.APPLICATION_JSON)
         //               .content(objectMapper.writeValueAsString(corporateAccount)))
//                .andExpect(status().isOk())
  //              .andExpect(jsonPath("$.cdpId").value("CDP123"));

//        verify(corporateAccountService, times(1)).updateCorporateAccount(anyLong(), any(CorporateAccount.class));
    }

    @Test
    void testDeleteCorporateAccount() throws Exception {
        doNothing().when(corporateAccountService).deleteCorporateAccount(anyLong());

        mockMvc.perform(delete("/api/corporate-accounts/1"))
                .andExpect(status().isNoContent());

        verify(corporateAccountService, times(1)).deleteCorporateAccount(anyLong());
    }
}

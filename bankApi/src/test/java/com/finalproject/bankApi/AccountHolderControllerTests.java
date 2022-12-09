package com.finalproject.bankApi;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.*;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Module module = new JavaTimeModule();
    private MockMvc mockMvc;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    AccountHolder accountHolder3;

    String body;
    MvcResult mvcResult;
    TransferenceDTO transferenceDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        accountHolder1 = new AccountHolder("La Rosalia", "123456", LocalDate.of(1992, 8, 14), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"));
        accountHolder2 = new AccountHolder("The Weekend", "123456", LocalDate.of(1985, 5, 10), new Address("Calle Streetsita", 0266L, "NY", "USA"), new Address("Calle Strotsota", 0256L, "LA", "USA"));
        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));

        accountRepository.saveAll(List.of(
                new CheckingAccount(accountHolder1, null, "123456"),
                new StudentAccount(accountHolder3, accountHolder2, "123456")
        ));
    }
    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void shouldGetAccountById_WhenGetIsPerformed_OK() throws Exception {
        mvcResult = mockMvc.perform(get("/account-holder/{accountId}/{ownerId}", 1, 1)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("La Rosalia"));
    }

    @Test
    void shouldGetAccountById_WhenGetIsPerformed_FAIL() throws Exception {
        mvcResult = mockMvc.perform(get("/account-holder/{accountId}/{ownerId}", 1, 2)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("La Rosalia"));
    }
    

    @Test
    void shouldGetAccountBalanceById_WhenGetIsPerformed_OK() throws Exception {
        mvcResult = mockMvc.perform(get("/account-holder/balance/{accountId}/{ownerId}", 1, 1)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("0.00"));
    }
    
    @Test
    void shouldMakeTransference_WhenPostIsPerformed_OK() throws Exception{
        transferenceDTO = new TransferenceDTO(new BigDecimal(100), 1L, "La Rosalia", 2L);
        body = objectMapper.writeValueAsString(transferenceDTO);
        
        //Setting an amount of money in the 1L account
        Account account = accountRepository.findById(1L).get();
               account.setBalance(new BigDecimal(500));
               accountRepository.save(account);
               
        //Testing if the trans from 1L to 2L is done
        mvcResult = mockMvc.perform(post("/account-holder/transfer-money").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("100"));
        assertEquals(new BigDecimal("100.00"), accountRepository.findById(2L).get().getBalance());
    }
    
    
}

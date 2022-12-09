package com.finalproject.bankApi;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.bankApi.controllers.users.AdminController;
import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.*;
import com.finalproject.bankApi.models.dtos.AccountDTO;
import com.finalproject.bankApi.models.dtos.BalanceDTO;
import com.finalproject.bankApi.models.users.*;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.repositories.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class AdminControllerTests {
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Module module = new JavaTimeModule();
    private MockMvc mockMvc;
    
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    AccountHolder accountHolder3;
    
    AccountDTO accountDTO;
    BalanceDTO balanceDTO;
    String body;
    MvcResult mvcResult;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        accountHolder1 = new AccountHolder("La Rosalia", "123456", LocalDate.of(1992, 8,14), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"));
        accountHolder2 = new AccountHolder("The Weekend", "123456", LocalDate.of(1985, 5,10), new Address("Calle Streetsita", 0266L, "NY", "USA"), new Address("Calle Strotsota", 0256L, "LA", "USA"));
        accountHolder3 = new AccountHolder("Billie Eilish", "123456", LocalDate.of(2001, 5,10), new Address("Calle Streetsita", 0266L, "NY", "USA"), new Address("Calle Strotsota", 0256L, "LA", "USA"));
        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3));
        
        accountRepository.saveAll(List.of(
                new CheckingAccount(accountHolder1, null, "123456"),
                new StudentAccount(accountHolder3, accountHolder2, "123456"),
                new SavingsAccount(accountHolder1, null, "123456"),
                new CreditCardAccount(accountHolder1, accountHolder2)
        ));
    }
    
    @AfterEach
    void tearDown(){
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
    
    @Test
    void shouldAddNewAdmin_whenPostIsPerformed_OK() throws Exception {
        Admin admin = new Admin("Damon Albarn", "123456");
        body = objectMapper.writeValueAsString(admin);
        mvcResult = mockMvc.perform(post("/admin/add-admin").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Damon Albarn"));
    }

    @Test
    void shouldAddNewAccountHolder_whenPostIsPerformed_OK() throws Exception {
        AccountHolder accountHolder = new AccountHolder("Neville Longbottom", "123456", LocalDate.of(1990,03,02), new Address("Calle Whatever", 01256L, "London", "UK"), new Address("Calle Whatever", 01256L, "London", "UK"));
        objectMapper.registerModule(module);
        body = objectMapper.writeValueAsString(accountHolder);
        mvcResult = mockMvc.perform(post("/admin/add-account-holder").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Neville Longbottom"));
    }

    @Test
    void shouldAddNewThirdParty_whenPostIsPerformed_OK() throws Exception {
        ThirdParty thirdParty = new ThirdParty("Cafeteria", "123456");
        body = objectMapper.writeValueAsString(thirdParty);
        mvcResult = mockMvc.perform(post("/admin/add-third-party").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Cafeteria"));
    }
    
    @Test
    void shouldAddNewCheckingAccount_whenPostIsPerformed_OK() throws Exception{
        accountDTO = new AccountDTO(accountHolder1.getId(), accountHolder2.getId(), null, null, null, null, "123456");
        body = objectMapper.writeValueAsString(accountDTO);
        mvcResult = mockMvc.perform(post("/admin/add-checking-account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("La Rosalia"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("The Weekend"));
    }

    @Test
    void shouldAddNewSavingsAccount_whenPostIsPerformed_OK() throws Exception{
        accountDTO = new AccountDTO(accountHolder1.getId(), accountHolder2.getId(), null, null, null, null, "123456");
        body = objectMapper.writeValueAsString(accountDTO);
        mvcResult = mockMvc.perform(post("/admin/add-savings-account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.err.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("0.0025"));
    }

    @Test
    void shouldAddNewCreditCardAccount_whenPostIsPerformed_OK() throws Exception{
        accountDTO = new AccountDTO(accountHolder1.getId(), accountHolder2.getId(), null, null, null, null, "123456");
        body = objectMapper.writeValueAsString(accountDTO);
        mvcResult = mockMvc.perform(post("/admin/add-credit-card-account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("100"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("0.2"));
    }

    @Test
    void shouldShowAccount_whenGetIsPerformed_OK() throws Exception{
        Long id = 1L;
        mvcResult = mockMvc.perform(get("/admin/client-account/{id}", id)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("La Rosalia"));
    }

    @Test
    void shouldUpdateAccountBalance_whenPatchIsPerformed_OK() throws Exception{
        balanceDTO = new BalanceDTO(1L, new BigDecimal(20000));
        body = objectMapper.writeValueAsString(balanceDTO);
        mvcResult = mockMvc.perform(patch("/admin/client-account/update-balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("20000"));
    }
    
    @Test
    void shouldDeleteAccount_whenDeleteIsPerformed_OK() throws Exception{
        Long id = 1L;
        mvcResult = mockMvc.perform(delete("/admin/delete-account/{id}", id)).andExpect(status().isOk()).andReturn();
        assertFalse(accountRepository.findById(1L).isPresent());
    }
}

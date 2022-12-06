package com.finalproject.bankApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.repositories.accounts.CheckingAccountRepository;
import com.finalproject.bankApi.repositories.accounts.CreditCardAccountRepository;
import com.finalproject.bankApi.repositories.accounts.SavingsAccountRepository;
import com.finalproject.bankApi.repositories.accounts.StudentAccountRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class AdminTests {
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
    
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @AfterEach
    void tearDown(){
        adminRepository.deleteAll();
    }
    
    @Test
    void shouldAddNewAdmin_whenPostIsPerformed_OK() throws Exception {
        Admin admin = new Admin("Damon Albarn", "123456");
        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/admin/add-admin").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Damon Albarn"));
    }
}

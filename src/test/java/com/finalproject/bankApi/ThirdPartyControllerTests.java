package com.finalproject.bankApi;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.bankApi.embedded.Address;
import com.finalproject.bankApi.models.accounts.*;
import com.finalproject.bankApi.models.dtos.AccountDTO;
import com.finalproject.bankApi.models.dtos.BalanceDTO;
import com.finalproject.bankApi.models.dtos.ThirdPartyTransferenceDTO;
import com.finalproject.bankApi.models.dtos.TransferenceDTO;
import com.finalproject.bankApi.models.users.AccountHolder;
import com.finalproject.bankApi.models.users.ThirdParty;
import com.finalproject.bankApi.repositories.accounts.*;
import com.finalproject.bankApi.repositories.transferences.ThirdPartyTransferenceRepository;
import com.finalproject.bankApi.repositories.users.AccountHolderRepository;
import com.finalproject.bankApi.repositories.users.ThirdPartyRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyControllerTests {
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
    ThirdParty thirdParty;
    ThirdPartyTransferenceDTO thirdPartyTransferenceDTO;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private ThirdPartyTransferenceRepository thirdPartyTransferenceRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        accountHolder1 = new AccountHolder("La Rosalia", "123456", LocalDate.of(1992, 8, 14), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"), new Address("Calle Palasio de Gaudi", 04256L, "Barcelona", "Spain"));
        accountHolder2 = new AccountHolder("The Weekend", "123456", LocalDate.of(1985, 5, 10), new Address("Calle Streetsita", 0266L, "NY", "USA"), new Address("Calle Strotsota", 0256L, "LA", "USA"));
        accountHolder3 = new AccountHolder("Billie Eilish", "123456", LocalDate.of(2001, 5, 10), new Address("Calle Streetsita", 0266L, "NY", "USA"), new Address("Calle Strotsota", 0256L, "LA", "USA"));
        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3));

        accountRepository.saveAll(List.of(
                new CheckingAccount(accountHolder1, null, "123456"),
                new StudentAccount(accountHolder3, accountHolder2, "123456"),
                new SavingsAccount(accountHolder1, null, "123456"),
                new CreditCardAccount(accountHolder1, accountHolder2)
        ));
        thirdParty = new ThirdParty("Cajero", "123456");
        thirdPartyRepository.save(thirdParty);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
    
    @Test
    void shouldMakeThirdPartyTransferenceAddingMoney_WhenPostIsPerformed_OK() throws Exception{
        thirdPartyTransferenceDTO = new ThirdPartyTransferenceDTO(new BigDecimal(100), 1L, "123456");
        body = objectMapper.writeValueAsString(thirdPartyTransferenceDTO);
        //Setting an amount of money in the 1L account
        Account account = accountRepository.findById(1L).get();
        account.setBalance(new BigDecimal(500));
        accountRepository.save(account);

        //Testing if the trans from thirdParty to 1L is done
        mvcResult = mockMvc.perform(post("/third-party/transfer-money").header("thirdPartyKey", "123456").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        
        
        assertTrue(mvcResult.getResponse().getContentAsString().contains("100"));
        assertEquals(1, thirdPartyTransferenceRepository.findAll().size());
        assertEquals(new BigDecimal("600.00"), accountRepository.findById(1L).get().getBalance());
        
    }

    @Test
    void shouldMakeThirdPartyTransferenceSubtractingMoney_WhenPostIsPerformed_OK() throws Exception{
        thirdPartyTransferenceDTO = new ThirdPartyTransferenceDTO(new BigDecimal(-100), 1L, "123456");
        body = objectMapper.writeValueAsString(thirdPartyTransferenceDTO);
        //Setting an amount of money in the 1L account
        Account account = accountRepository.findById(1L).get();
        account.setBalance(new BigDecimal(500));
        accountRepository.save(account);

        //Testing if the trans from thirdParty to 1L is done
        mvcResult = mockMvc.perform(post("/third-party/transfer-money").header("thirdPartyKey", "123456").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();


        assertTrue(mvcResult.getResponse().getContentAsString().contains("-100"));
        assertEquals(1, thirdPartyTransferenceRepository.findAll().size());
        assertEquals(new BigDecimal("400.00"), accountRepository.findById(1L).get().getBalance());

    }
}

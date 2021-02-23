package com.gourav.tech.service;

import com.gourav.tech.entity.User;
import com.gourav.tech.pojo.RegistrationRequest;
import com.gourav.tech.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

/**
 * Mockito Testing
 */

//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRegistrationService {


    @Mock
    RegistrationRepository registrationRepository;

    //    @Autowired
    @InjectMocks
    RegistrationService registrationService;

    RegistrationRequest registrationRequest = null;

    @BeforeAll
    void setup() {
        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setDob("2002-02-22");
        registrationRequest.setPaymentCardNumber(312345789123456789L);
    }

    @Test
    public void testSaveCreated() {
        org.mockito.Mockito.when(registrationRepository.findByName(org.mockito.Mockito.anyString())).thenReturn(null);
        HttpStatus response = registrationService.save(registrationRequest);
        org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response);

    }

    @Test
    public void testSaveConflict() {
        User user = new User(registrationRequest.getUsername().trim()
                , registrationRequest.getPassword()
                , java.sql.Date.valueOf(LocalDate.parse(registrationRequest.getDob()))
                , registrationRequest.getPaymentCardNumber());

        org.mockito.Mockito.when(registrationRepository.findByName(org.mockito.Mockito.anyString())).thenReturn(user);
        HttpStatus response = registrationService.save(registrationRequest);
        org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CONFLICT, response);
    }


}

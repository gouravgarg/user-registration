package com.gourav.tech.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gourav.tech.pojo.RegistrationRequest;
import com.gourav.tech.service.RegistrationService;
import com.gourav.tech.validation.ValidateRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring Boot testing
 *
 *
 * @author gouravgarg
 */


@WebMvcTest(RegistrationController.class)
@Import({ValidateRegisterRequest.class})
@TestPropertySource(properties = "iin.blockedSet=123456,234567")
public class TestRegistrationController {

//    @MockBean
//    ValidateRegisterRequest validateRegisterRequest;

    @MockBean
    RegistrationService registrationService;

    @Autowired
    MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBadRequest() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        mvc.perform(
                MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void testInvalidUserName() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gourav-garg");
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void testInvalidPassword() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("12");
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void testInvalidDOB() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setPaymentCardNumber(312345789123456789L);
        registrationRequest.setDob("2004-02-22");
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    public void testInvalidPaymentCardNumber() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setDob("2002-02-22");

        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void testBlockedPaymentCard() throws Exception {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setDob("2002-02-22");
        registrationRequest.setPaymentCardNumber(123456789123456789L);
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));

    }

    @Test
    public void testConflictRegistration() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setDob("2002-02-22");
        registrationRequest.setPaymentCardNumber(312345789123456789L);
        org.mockito.Mockito.when(registrationService.save(org.mockito.Mockito.any(RegistrationRequest.class))).thenReturn(HttpStatus.CONFLICT);
        //org.mockito.Mockito.when(registrationService.save(registrationRequest)).thenReturn(HttpStatus.CONFLICT);
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.CONFLICT.value()));

    }


    @Test
    public void testValidRegistration() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("gouravgarg19");
        registrationRequest.setPassword("gaRg1234");
        registrationRequest.setDob("2002-02-22");
        registrationRequest.setPaymentCardNumber(312345789123456789L);
        org.mockito.Mockito.when(registrationService.save(org.mockito.Mockito.any(RegistrationRequest.class))).thenReturn(HttpStatus.CREATED);
        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequest)))
                .andExpect(status().is(HttpStatus.CREATED.value()));

    }

}

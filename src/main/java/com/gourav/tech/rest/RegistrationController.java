package com.gourav.tech.rest;

import com.gourav.tech.pojo.RegistrationRequest;
import com.gourav.tech.service.RegistrationService;
import com.gourav.tech.validation.ValidateRegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RegistrationController {

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    ValidateRegisterRequest validateRegisterRequest;
    RegistrationService registrationService;

    @Autowired
    public RegistrationController(ValidateRegisterRequest validateRegisterRequest, RegistrationService registrationService) {
        this.validateRegisterRequest = validateRegisterRequest;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequest registrationRequest) {
        //    logger.info(registrationRequest.toString());

        if (isBadRequest(registrationRequest)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!validateRegisterRequest.isAgeMoreThan18(registrationRequest.getDob())) {
            logger.error("Age validation failed");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (validateRegisterRequest.isBlockedPaymentCardNumber(registrationRequest.getPaymentCardNumber())) {
            logger.error("Blocked payment card number");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        HttpStatus response = registrationService.save(registrationRequest);
        return new ResponseEntity<>(response);
    }

    @GetMapping("/registerGetAll")
    public List<RegistrationRequest> register() {
        return registrationService.getAll();
    }


    public boolean isBadRequest(RegistrationRequest registrationRequest) {
        if (!validateRegisterRequest.isAlphaNumericUsername(registrationRequest.getUsername())) {
            logger.error("username is not alphanumeric");
            return true;
        }

        if (!validateRegisterRequest.isValidPassword(registrationRequest.getPassword())) {
            logger.error("Failed password validation");
            return true;
        }

        if (!validateRegisterRequest.isValidPaymentCardNumber(registrationRequest.getPaymentCardNumber())) {
            logger.error("Invalid payment card number");
            return true;
        }

        if (null == registrationRequest.getDob()) {
            logger.error("DOB is null");
            return true;
        }
        return false;
    }


}

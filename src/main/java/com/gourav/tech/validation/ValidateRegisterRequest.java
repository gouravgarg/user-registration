package com.gourav.tech.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
public class ValidateRegisterRequest {
    Logger logger = LoggerFactory.getLogger(ValidateRegisterRequest.class);

    private Optional<Set<String>> blockedSet;
    private Pattern userNamePatter = Pattern.compile("^[a-zA-Z0-9]*$");
    private Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[A-Z]).{4,}$");
    private Pattern paymentCardNumberPattern = Pattern.compile("\\d{15,19}");

    public ValidateRegisterRequest(@Value("${iin.blockedSet:#{null}}") Optional<Set<String>> blockedSet) {
        this.blockedSet = blockedSet;
    }

    public boolean isAlphaNumericUsername(String username) {
        return null == username ? false : userNamePatter.matcher(username).find();
    }

    public boolean isValidPassword(String password) {
        return null == password ? false : passwordPattern.matcher(password).find();
    }

    public boolean isAgeMoreThan18(String inputDOB) {
        LocalDate date = LocalDate.parse(inputDOB);
        LocalDate now = LocalDate.now();
        Period age = Period.between(date, now);
        return age.getYears() >= 18;
    }

    public boolean isValidPaymentCardNumber(long inputCardNumber) {
        return paymentCardNumberPattern.matcher(String.valueOf(inputCardNumber)).find();
    }

    public boolean isBlockedPaymentCardNumber(long inputCardNumber) {
        String firstSiXChar = String.valueOf(inputCardNumber).substring(0, 6);
        return blockedSet.isPresent() ? blockedSet.get().contains(firstSiXChar) : false;
    }

    @PostConstruct
    void printSet() {
        logger.info("blockedSet=" + blockedSet);
    }
}

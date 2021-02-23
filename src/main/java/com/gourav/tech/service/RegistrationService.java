package com.gourav.tech.service;

import com.gourav.tech.repository.RegistrationRepository;
import com.gourav.tech.pojo.RegistrationRequest;
import com.gourav.tech.entity.User;
import com.gourav.tech.rest.RegistrationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**

 *
 * @author gouravgarg
 */
@Service
public class  RegistrationService {

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public HttpStatus save(RegistrationRequest registrationRequest) {
        User existingUser = getUser(registrationRequest.getUsername());
        if (null != existingUser) {
            logger.error("Exist username");
            return HttpStatus.CONFLICT;
        }
        User user = new User(registrationRequest.getUsername().trim()
                , registrationRequest.getPassword()
                , java.sql.Date.valueOf(LocalDate.parse(registrationRequest.getDob()))
                , registrationRequest.getPaymentCardNumber());
        logger.error("Username has been registered");
        registrationRepository.save(user);
        return HttpStatus.CREATED;
    }

    public User getUser(String userName) {
        return registrationRepository.findByName(userName.trim());
    }

    public List<RegistrationRequest> getAll(){
        Iterable<User> users =  registrationRepository.findAll();
        List<RegistrationRequest> registrationRequestList=new ArrayList<>();
        for (User user: users) {
            registrationRequestList.add(new RegistrationRequest(user.getUsername(),user.getDateOfBirth().toString(),user.getPaymentCardNumber()));
        }

        return registrationRequestList;
    }

}

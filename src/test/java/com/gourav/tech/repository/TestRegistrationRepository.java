package com.gourav.tech.repository;

import com.gourav.tech.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Spring Boot testing
 */
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestRegistrationRepository {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    public void testSave() {
        User user = new User("nivea1500"
                , "nivea1500"
                , new java.sql.Date(new java.util.Date().getTime())
                , 1235468975445L);

        entityManager.persist(user);
        entityManager.flush();
        User response = registrationRepository.findById(1L).orElseThrow();
        org.junit.jupiter.api.Assertions.assertTrue(response.getUsername().equals(user.getUsername()));
    }


    @Test
    public void testFindByUsername() {
        User user = new User("nivea1500"
                , "nivea1500"
                , new java.sql.Date(new java.util.Date().getTime())
                , 1235468975445L);
        entityManager.persist(user);
        entityManager.flush();
        User response = registrationRepository.findByName(user.getUsername());
        org.junit.jupiter.api.Assertions.assertTrue(response.getUsername().equals(user.getUsername()));
    }


}

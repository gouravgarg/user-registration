package com.gourav.tech.repository;

import com.gourav.tech.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE p.username = :username")
    User findByName(String username);
}

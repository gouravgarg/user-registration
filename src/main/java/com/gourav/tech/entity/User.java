package com.gourav.tech.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private  java.sql.Date dateOfBirth;
    private long paymentCardNumber;

    public User() {
    }

    public User(String username, String password, Date dateOfBirth, long paymentCardNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.paymentCardNumber = paymentCardNumber;
    }


    public String getUsername() {
        return username;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public long getPaymentCardNumber() {
        return paymentCardNumber;
    }

    /**
     * Consider username only due to im memory map
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

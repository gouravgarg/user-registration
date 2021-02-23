package com.gourav.tech.pojo;


import java.util.Objects;

public class RegistrationRequest {

    private String username;
    private String password;
    private String dob;
    private long paymentCardNumber;

    public RegistrationRequest(String username, String dob, long paymentCardNumber) {
        this.username = username;
        this.dob = dob;
        this.paymentCardNumber = paymentCardNumber;
    }

    public RegistrationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(long paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationRequest that = (RegistrationRequest) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "username='" + username + '\'' +
                ", dob='" + dob + '\'' +
                ", paymentCardNumber=" + paymentCardNumber +
                '}';
    }
}

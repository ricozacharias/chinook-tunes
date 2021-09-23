package com.experis.de.ChinookTunes.Models;

public class Customer {
    private Integer customerId = -1;
    private String firstName;
    private String lastName;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    public Customer() {  }

    public Customer(Integer customerId, String firstName, String lastName, String country, String postalCode, String phone, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String country, String postalCode, String phone, String email) {
        this(-1, firstName, lastName, country, postalCode, phone, email);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomerId: ").append(customerId).append("<br>");
        sb.append("Firstname: ").append(firstName).append("<br>");
        sb.append("Lastname: ").append(lastName).append("<br>");

        return sb.toString();
    }
}

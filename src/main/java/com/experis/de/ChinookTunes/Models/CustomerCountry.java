package com.experis.de.ChinookTunes.Models;

public class CustomerCountry {
    private String country;
    private Integer customerCount;

    public CustomerCountry(String country, Integer customerCount) {
        this.country = country;
        this.customerCount = customerCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country: ").append(country).append("<br>");
        sb.append("CustomerCount: ").append(customerCount).append("<br>");

        return sb.toString();
    }
}

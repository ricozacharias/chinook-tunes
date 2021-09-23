package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Models.Customer;
import com.experis.de.ChinookTunes.Models.CustomerCountry;
import com.experis.de.ChinookTunes.Models.CustomerGenre;
import com.experis.de.ChinookTunes.Models.CustomerSpender;

import java.util.ArrayList;
import java.util.List;

public interface CustomerRepository {
    List<Customer> getAllCustomers();
    List<Customer> getAllCustomers(Integer limit, Integer offset, String orderBy);
    Customer getCustomerById(Integer customerId);
    Customer getCustomerByName(String customerName);
    Integer addCustomer(Customer customer);
    Boolean updateCustomer(Customer customer);
    List<CustomerCountry> getCountryCounts();
    List<CustomerSpender> getCustomerSpenders();
    List<CustomerGenre> getMostPopularGenresByCustomerId(Integer customerId);
}

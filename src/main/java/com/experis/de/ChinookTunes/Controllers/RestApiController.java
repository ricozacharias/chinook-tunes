package com.experis.de.ChinookTunes.Controllers;

import com.experis.de.ChinookTunes.Controllers.Exceptions.NotCreatedException;
import com.experis.de.ChinookTunes.DataAccess.*;
import com.experis.de.ChinookTunes.Logging.Logger;
import com.experis.de.ChinookTunes.Models.Customer;
import com.experis.de.ChinookTunes.Models.CustomerCountry;
import com.experis.de.ChinookTunes.Models.CustomerGenre;
import com.experis.de.ChinookTunes.Models.CustomerSpender;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RestApiController {

    private final CustomerRepository customerRepository;
    private final Logger logger;

    public RestApiController(CustomerRepository customerRepository, Logger logger) {
        this.customerRepository = customerRepository;
        this.logger = logger;
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @GetMapping("/api/customers/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return customerRepository.getCustomerById(id);
    }

    @GetMapping("/api/customers/search/{name}")
    public Customer getCustomersByName(@PathVariable String name) {
        Customer customer = customerRepository.getCustomerByName(name);
        return customer;
    }

    @GetMapping("/api/customers/country/count")
    public List<CustomerCountry> getCustomersCountryCount() {
        List<CustomerCountry> customerCountries = customerRepository.getCountryCounts();
        return customerCountries;
    }

    @GetMapping("/api/customers/highest/spenders")
    public List<CustomerSpender> getCustomersSpenders() {
        List<CustomerSpender> customerSpenders = customerRepository.getCustomerSpenders();
        return customerSpenders;
    }

    @GetMapping("/api/customers/{customerid}/popular/genre")
    public List<CustomerGenre> getCustomersPopularGenres(@PathVariable int customerid) {
        var genres = customerRepository.getMostPopularGenresByCustomerId(customerid);

        if (genres.size() > 1) {
            Integer mostGenreCount = genres.get(0).getGenreCount();
            genres = genres.stream().filter(x -> x.getGenreCount() == mostGenreCount).toList(); // filter for most popular genres
        }

        return genres;
    }

    @PostMapping(
            path = "/api/customers/",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Customer createCustomer(@RequestBody Customer customer) { //
        Integer customerId = customerRepository.addCustomer(customer);

        if (customerId > 0) {
            customer = customerRepository.getCustomerById(customerId);

            return customer;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer could not be created.");
        }
    }

    @PutMapping(
            path = "/api/customers/",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Customer updateCustomer(@RequestBody Customer customer) { //

        Boolean updated = customerRepository.updateCustomer(customer);

        if (updated) {
            customer = customerRepository.getCustomerById(customer.getCustomerId());

            return customer;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer could not be updated.");
        }
    }
}

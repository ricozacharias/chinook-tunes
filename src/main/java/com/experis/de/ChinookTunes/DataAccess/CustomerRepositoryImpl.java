package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Logging.LogToConsole;
import com.experis.de.ChinookTunes.Logging.Logger;
import com.experis.de.ChinookTunes.Models.Customer;
import com.experis.de.ChinookTunes.Models.CustomerCountry;
import com.experis.de.ChinookTunes.Models.CustomerGenre;
import com.experis.de.ChinookTunes.Models.CustomerSpender;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    enum DBOperation {
        INSERT,
        UPDATE
    }

    private final Logger logger;
    private SqlLiteHelper db;

    public CustomerRepositoryImpl(Logger logger) {
        this.logger = logger;
        this.db = new SqlLiteHelper(logger);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return getAllCustomers(-1, -1, null);
    }

    @Override
    public ArrayList<Customer> getAllCustomers(Integer limit, Integer offset, String orderBy) {
        ArrayList<Customer> customers = null;

        try {
            String sql = db.prepareSql("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer",
                                            limit, offset, orderBy);

            ResultSet resultSet = db.executeQuery(sql);

            // Process Results
            customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(resultSetToCustomer(resultSet));
            }
        }
        catch (Exception ex){
             logger.log("getAllCustomers() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customers;
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        Customer customer = null;

        try {
            ResultSet resultSet = db.executeQuery("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer WHERE CustomerId = ?", customerId);

            // Process Results
            customer = resultSetToCustomer(resultSet);
        }
        catch (Exception ex){
             logger.log("getCustomerById("+customerId+") failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customer;
    }

    @Override
    public Customer getCustomerByName(String name) {
        Customer customer = null;

        try {
            String sql = db.prepareSql("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer WHERE LastName LIKE ?", 1, 0);

            ResultSet resultSet = db.executeQuery(sql, "%"+name+"%");

            // Process Results
            customer = resultSetToCustomer(resultSet);
        }
        catch (Exception ex){
             logger.log("getCustomerByName('"+name+"') failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customer;
    }

    @Override
    public Boolean updateCustomer(Customer customer) {

        Integer result = -1;

        try {
            result = db.executeUpdate("Update Customer set Firstname = ?, Lastname = ? "
                                    + ",Country = ?, PostalCode = ?, Phone = ?, Email = ? "
                                    + " where CustomerId = ?",
                            getParamsForCustomer(customer, DBOperation.UPDATE));

        }
        catch (Exception ex){
            logger.log("updateCustomer() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return (result == 1);
    }

    @Override
    public Integer addCustomer(Customer customer) {

        Integer customerId = -1;

        try {
            Integer result = db.executeUpdate("Insert into Customer (Firstname, Lastname, Country, PostalCode, Phone, Email) " +
                                " VALUES (?, ?, ?, ?, ?, ?)",
                                getParamsForCustomer(customer, DBOperation.INSERT));

            ResultSet resultSet = db.executeQuery("select last_insert_rowid()");

            if (resultSet != null) {
                customerId = resultSet.getInt(1);
            }

        }
        catch (Exception ex){
            logger.log("addCustomer() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customerId;
    }

    @Override
    public List<CustomerCountry> getCountryCounts() {
        String sql = "SELECT Country, Count(*) As CustomerCount from Customer " +
                     "GROUP BY Country " +
                     "ORDER BY CustomerCount DESC ";

        ArrayList<CustomerCountry> countryCounts = null;

        try {
            ResultSet resultSet = db.executeQuery(sql, null);

            // Process Results
            countryCounts = new ArrayList<>();
            while (resultSet.next()) {
                countryCounts.add(new CustomerCountry(
                        resultSet.getString("Country"),
                        resultSet.getInt("CustomerCount")));
            }
        }
        catch (Exception ex){
             logger.log("getCountryCounts() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return countryCounts;
    }

    @Override
    public List<CustomerSpender> getCustomerSpenders() {
        String sql = "select Customer.CustomerId as CustomerId, Customer.LastName as CustomerLastName, Customer.FirstName as CustomerFirstName, SUM(Invoice.Total) as InvoiceTotal " +
                "from Invoice " +
                "inner join Customer on Customer.CustomerId = Invoice.CustomerId " +
                "group by " +
                "Customer.CustomerId " +
                "order by " +
                "InvoiceTotal DESC";

        ArrayList<CustomerSpender> customerSpenders = null;

        try {
            ResultSet resultSet = db.executeQuery(sql, null);

            // Process Results
            customerSpenders = new ArrayList<>();
            while (resultSet.next()) {
                customerSpenders.add(new CustomerSpender(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("CustomerFirstName"),
                        resultSet.getString("CustomerLastName"),
                        resultSet.getInt("InvoiceTotal")));
            }
        }
        catch (Exception ex){
             logger.log("getCustomerSpenders() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customerSpenders;
    }

    @Override
    public List<CustomerGenre> getMostPopularGenresByCustomerId(Integer customerId) {
        String sql = "select Genre.GenreId, Genre.Name as GenreName, COUNT(*) as GenreCount " +
                "from Customer " +
                "inner join Invoice on Invoice.CustomerId = Customer.CustomerId " +
                "inner join InvoiceLine on InvoiceLine.InvoiceId = Invoice.InvoiceId " +
                "inner join Track on Track.TrackId = InvoiceLine.TrackId " +
                "inner join Genre on Genre.GenreId = Track.GenreId " +
                "WHERE Customer.CustomerId = ? " +
                "GROUP BY Genre.GenreId " +
                "ORDER BY GenreCount DESC";

        ArrayList<CustomerGenre> customerGenres = null;

        try {
            ResultSet resultSet = db.executeQuery(sql, customerId);

            // Process Results
            customerGenres = new ArrayList<>();
            while (resultSet.next()) {
                customerGenres.add(new CustomerGenre(
                        resultSet.getInt("GenreId"),
                        resultSet.getString("GenreName"),
                        resultSet.getInt("GenreCount")));
            }
        }
        catch (Exception ex){
             logger.log("getMostPopularGenresByCustomerId() failed: "+ex);
        }
        finally {
            db.closeConnection();
        }

        return customerGenres;

    }


    private Object[] getParamsForCustomer(Customer customer, DBOperation operation) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(customer.getFirstName());
        params.add(customer.getLastName());
        params.add(customer.getCountry());
        params.add(customer.getPostalCode());
        params.add(customer.getPhone());
        params.add(customer.getEmail());

        if (operation == DBOperation.UPDATE) {
            params.add(customer.getCustomerId());
        }

        return params.toArray();
    }

    private Customer resultSetToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = null;

        if (resultSet != null) {
            customer = new Customer(
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("Firstname"),
                    resultSet.getString("Lastname"),
                    resultSet.getString("Country"),
                    resultSet.getString("PostalCode"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Email")
            );
        }
        return customer;
    }

}

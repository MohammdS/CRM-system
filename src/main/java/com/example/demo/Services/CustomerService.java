package com.example.demo.Services;

import com.example.demo.Model.Customer;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {


    // Utility method to validate email format
    private boolean isValidEmail(String email) {
        if (email == null) return false;

        // Basic regex for email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        if (!email.matches(emailRegex)) return false;

        // Split email into parts
        String[] parts = email.split("@");
        if (parts.length != 2) return false;

        // Check first part length
        if (parts[0].length() <= 5) return false;

        // Check allowed domains
        String[] allowedDomains = {"gmail", "yahoo", "outlook", "hotmail", "icloud", "example"};
        String domainPart = parts[1].toLowerCase();
        boolean validDomain = false;
        for (String domain : allowedDomains) {
            if (domainPart.startsWith(domain + ".") && domainPart.endsWith(".com")) {
                validDomain = true;
                break;
            }
        }
        return validDomain;
    }

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAllByOrderByNameAsc();
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Customer addCustomer(Customer customer) {
        if (!isValidEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return repository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (repository.existsById(id)) {
            customer.setId(id);
            return repository.save(customer);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllCustomers() {
        repository.deleteAll();
    }

    public List<Customer> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Customer> searchByEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        else 
            return repository.findByEmailContainingIgnoreCase(email);

    }

    public List<Customer> saveAll(List<Customer> customers) {
        for (Customer customer : customers) {
            if (!isValidEmail(customer.getEmail())) {
                throw new IllegalArgumentException("Invalid email format for customer: " + customer.getName());
            }
        }
        return repository.saveAll(customers);
    }
}



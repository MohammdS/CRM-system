package com.example.demo.Controllers;

import com.example.demo.Services.CustomerService;
import com.example.demo.Model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Constructor Injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // READ - Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        try{
            return customerService.getAllCustomers();
        } catch (Exception e) {
            System.out.println("Error fetching customers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // READ - Get customer by ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        
        if (id == null || id <= 0){
            System.out.println("Invalid ID provided: " + id);
            return new Customer();
        }   
        else try{
            return customerService.getCustomerById(id);
        }
        catch(IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return new Customer();
        }
    }

    // CREATE - Add new customer
    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        try{
            customerService.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
        return "Customer added successfully";
    }

    // CREATE - Add multiple customers
    @PostMapping("/batch")
    public List<Customer> createCustomers(@RequestBody List<Customer> customers) {
        return customerService.saveAll(customers);
    }


    // UPDATE - Update customer info (whole object)
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try{
            customerService.updateCustomer(id, customer);
                return "Customer updated successfully";

        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Delete by ID
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        try{
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
        catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
        catch (Exception e) {
            return "Error deleting customer: " + e.getMessage();
        }
    }

    // DELETE - Delete all customers
    @DeleteMapping("/all")
    public String deleteAllCustomers() {
        try{
            customerService.deleteAllCustomers();
            return "All customers deleted successfully";
        } catch (Exception e) {
            return "Error deleting all customers: " + e.getMessage();
        }
    }

    // SEARCH - Search by name using query param
    @GetMapping("/searchbyname")
    public List<Customer> searchCustomers(@RequestParam String name) {
        try{
            List<Customer> list = customerService.searchByName(name);
            if (list.isEmpty()) {
                System.out.println("No customers found with name: " + name);
                return new ArrayList<>();
            }
            if (list.size() > 1) {
                System.out.println("Found " + list.size() + " customers with name: " + name);
                return list; 
            } else {
                System.out.println("Found 1 customer with name: " + name);
                return list;
            }
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/searchbyemail")
    public List<Customer> searchCustomersByEmail(@RequestParam String email) {
        try{
            List<Customer> list = customerService.searchByEmail(email);
            if (list.isEmpty()) {
                System.out.println("No customers found with email: " + email);
                return new ArrayList<>();
            }
            else {
                System.out.println("Found a customer with email: " + email);
                return list;
            }
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.Customer;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public List<Customer> findAllByOrderByNameAsc();
    public List<Customer> findByNameContainingIgnoreCase(String name);
    public List<Customer> findByEmailContainingIgnoreCase(String email);
}

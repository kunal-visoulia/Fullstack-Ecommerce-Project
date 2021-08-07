package com.kunal.ecommerce.dao;

import com.kunal.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// the reason we are using Customer here because customer has a collection of orders, so when a purchase comes across we will grab the customer,assemble it accordingly, and then we'll save the actual customer using this repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

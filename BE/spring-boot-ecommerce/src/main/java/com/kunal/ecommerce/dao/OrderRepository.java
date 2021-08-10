package com.kunal.ecommerce.dao;

import com.kunal.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    //search for orders via customer id(orders have reference to customer)
    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);
    // Select * from orders LEFT OUTER JOIN customer ON orders.customer_id=customer.id WHERE ucstomer.email=:email
}

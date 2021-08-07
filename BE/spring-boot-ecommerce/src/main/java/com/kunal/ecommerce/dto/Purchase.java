package com.kunal.ecommerce.dto;

import com.kunal.ecommerce.entity.Address;
import com.kunal.ecommerce.entity.Customer;
import com.kunal.ecommerce.entity.Order;
import com.kunal.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    // REMEMBER: All these are references to actual data in tables cuz PURCHASE has relationship to below tables
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems; // you will say we recieve JSON Array, but here we have a Set; doesn't matter both are Collections. Json will take care of it

}

package com.kunal.ecommerce.service;

import com.kunal.ecommerce.dao.CustomerRepository;
import com.kunal.ecommerce.dto.Purchase;
import com.kunal.ecommerce.dto.PurchaseResponse;
import com.kunal.ecommerce.entity.Customer;
import com.kunal.ecommerce.entity.Order;
import com.kunal.ecommerce.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service // this is the service implementation; and so that springboot can pick this up during component scanning
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired //optional annotation since we have only one constructor
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    } // constructor to inject the Customer Repository

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();// this purchase we got from FE???
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID(Universally unique Identifier) number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
        // generated uuids have collison percentage of 1 in billion, but if you want 0collision, just search you DB if generated id is being already used and regenerate till you get unique id
    }
}










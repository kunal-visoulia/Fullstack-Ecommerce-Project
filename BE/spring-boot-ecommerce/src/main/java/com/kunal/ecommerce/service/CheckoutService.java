package com.kunal.ecommerce.service;

import com.kunal.ecommerce.dto.Purchase;
import com.kunal.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase); //Purchase DTO as param and send back the PurchaseResponse
}

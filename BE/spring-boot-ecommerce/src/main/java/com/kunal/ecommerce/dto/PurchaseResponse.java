package com.kunal.ecommerce.dto;

import lombok.Data;

@Data
// use this class to send back Java Object as JSON
public class PurchaseResponse {

    private final String orderTrackingNumber; // final because lombok @data will generate constructor only  for final fields
    // or without final keyword
//    @NonNull
//    private String orderTrackingNumber;

}

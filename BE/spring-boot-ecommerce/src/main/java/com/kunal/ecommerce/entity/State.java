package com.kunal.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="state")
@Data
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne //many states are mapped to one country
    @JoinColumn(name="country_id") //actual column that we are joining on in the state table and so that country_id maps back to the actual country table as a foreigh ket relationship
    private com.kunal.ecommerce.entity.Country country;

}













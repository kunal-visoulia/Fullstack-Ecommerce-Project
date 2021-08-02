package com.kunal.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "country") //one country has many states
    @JsonIgnore // will ignore the nested states(there will be no states key). without this, if you goto /api/countires you will get a nested state object, with all associated states for a country, but we don't want countries data to contain all associated states, we will retireve states letter
    private List<State> states; //List of states that are mapped by country

}











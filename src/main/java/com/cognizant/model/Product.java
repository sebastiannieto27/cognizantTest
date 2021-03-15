package com.cognizant.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public  class Product {

    private int id;
    private String name;
    private CoffeeOffer coffeeOffer;
    private OtherOffer otherOffer;
    private ExtraOffer extraOffer;
    private double price;


    public Product(int id, String name, double price, CoffeeOffer coffeeOffer, ExtraOffer extraOffer) {
        this(id,name, price);
        this.coffeeOffer = coffeeOffer;
        this.extraOffer = extraOffer;
    }

    public Product(int id, String name, double price, OtherOffer otherOffer) {
        this(id,name, price);
        this.otherOffer = otherOffer;
    }
    public Product(int id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }


}

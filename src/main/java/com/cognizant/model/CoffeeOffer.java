package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeOffer {

    CoffeeSmall("Small","beverage",2.50),
    CoffeeMedium("Medium", "beverage",3.00),
    CoffeeLarge("Large", "beverage", 3.50);

    private final String label;
    private final  String promotionType;
    private final double value;
}

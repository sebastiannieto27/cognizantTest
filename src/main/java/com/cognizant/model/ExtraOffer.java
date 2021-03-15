package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtraOffer {
    NoExtra("NoExtra", 0.0),
    ExtraMilk("Extra Milk",0.30),
    FoamedMilk("Foamed Milk",0.50),
    SpecialCoffee("Special Coffee",0.90);

    private final String label;
    private final double value;
}

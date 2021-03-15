package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OtherOffer {
    OrangeJuice("OrangeJuice", "beverage" ,3.95),
    BaconRoll("BaconRoll", "snack",4.50);

    private final String label;
    private final  String promotionType;
    private final double value;
}

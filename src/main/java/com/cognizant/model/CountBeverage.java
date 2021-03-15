package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CountBeverage {

    Five(5),
    Ten(10),
    Fifteen(15),
    Twenty(20),
    TwentyFive(25),
    Thirty(30);

    private final int rangeOfBeverage;

}

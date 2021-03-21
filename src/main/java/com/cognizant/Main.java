package com.cognizant;

import com.cognizant.core.CoffeeCorner;
import com.cognizant.core.CoffeeCornerImpl;
import com.cognizant.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Product> products = List.of(new Product(1, "coffee large with milk", CoffeeOffer.CoffeeLarge.getValue()+ExtraOffer.ExtraMilk.getValue(),
                        CoffeeOffer.CoffeeLarge,ExtraOffer.ExtraMilk),
                new Product(2, "coffee small with foamed milk", CoffeeOffer.CoffeeSmall.getValue()+ExtraOffer.FoamedMilk.getValue(),
                        CoffeeOffer.CoffeeSmall, ExtraOffer.FoamedMilk),
                new Product(3, "Bacon", OtherOffer.BaconRoll.getValue(), OtherOffer.BaconRoll),
                new Product(4, "Orange Juice", OtherOffer.OrangeJuice.getValue(), OtherOffer.OrangeJuice),
                new Product(5, "coffee medium with special", CoffeeOffer.CoffeeMedium.getValue()+ExtraOffer.SpecialCoffee.getValue(),
                        CoffeeOffer.CoffeeMedium, ExtraOffer.SpecialCoffee),
                new Product(6, "Orange Juice", OtherOffer.OrangeJuice.getValue(),
                        OtherOffer.OrangeJuice));
        CoffeeCorner coffeeCorner = new CoffeeCornerImpl();
        Receipt receipt = coffeeCorner.applyDiscount(products);
        System.out.println(receipt.toString());
    }
}

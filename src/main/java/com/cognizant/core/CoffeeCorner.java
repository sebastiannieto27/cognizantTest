package com.cognizant.core;

import com.cognizant.model.Product;
import com.cognizant.model.Receipt;

import java.util.List;

public interface CoffeeCorner {

    Receipt applyDiscount(List<Product> products);

}

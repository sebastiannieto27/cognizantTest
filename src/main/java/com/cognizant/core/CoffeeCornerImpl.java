package com.cognizant.core;

import com.cognizant.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoffeeCornerImpl implements CoffeeCorner {

    @Override
    public Receipt applyDiscount(List<Product> products) {
        boolean checkExtra = products.stream()
                .filter(product -> product.getCoffeeOffer() != null)
                .filter(product -> product.getExtraOffer() != ExtraOffer.NoExtra)
                .anyMatch(product -> getExtraOffer().contains(product.getExtraOffer().getLabel()));
        long checkSnack = products.stream()
                .filter(product -> product.getOtherOffer() != null)
                .filter(product -> product.getOtherOffer().getPromotionType().equals("snack")).count();
        long checkBeverage = countBeverage(products);

        long discount = checkBeverage > checkSnack ? checkSnack : 0L;
        Receipt receipt = calculateDiscount(products, checkExtra, discount, checkBeverage);
        return receipt;
    }

    private Receipt calculateDiscount(List<Product> products, boolean checkExtra, long discount, long checkBeverage) {
        Receipt receipt = new Receipt();
        TreeMap<Integer, Double> map = new TreeMap<>();
        double subTotal = 0.0;
        products.stream()
                .filter(product -> product.getOtherOffer() != null)
                .forEach(product -> {
                    if (product.getOtherOffer().getLabel() != null)
                        map.put(product.getId(), product.getPrice());
                });
        if (checkExtra) {
            setCheckCoffeeOffer(products, map);
            while (discount > 0L) {
                subTotal = subTotal + checkExtraForFree(products);
                discount--;
            }
        } else {
            setCheckCoffeeOffer(products, map);
        }
        subTotal = subTotal + checkBeverageForFree(products, checkBeverage);
        receipt.setMap(map);
        receipt.setProducts(products.stream().map(Product::getName).collect(Collectors.toList()));
        receipt.setDate(LocalDate.now());
        receipt.setSubTotal(subTotal);
        receipt.setTotal(receipt.getValues().stream().reduce(0.0, Double::sum));
        return receipt;
    }

    private void setCheckCoffeeOffer(List<Product> products, TreeMap<Integer, Double> map) {
        products.stream()
                .filter(product -> product.getCoffeeOffer() != null)
                .forEach(product -> {
                    if (product.getCoffeeOffer().getLabel() != null)
                        map.put(product.getId(), product.getPrice());
                });
    }

    private double checkBeverageForFree(List<Product> products, long checkBeverage) {
        return getRange().contains((int) checkBeverage) ? checkBeverage(getCheckCoffee(products), getCheckOther(products))
                .stream().reduce((first, second) -> second).orElse(null).getPrice() : 0.0;
    }

    private double checkExtraForFree(List<Product> products) {
        return getCheckCoffee(products).stream().map(Product::getExtraOffer).findAny().get().getValue();
    }

    private long countBeverage(List<Product> products) {
        return checkBeverage(getCheckCoffee(products), getCheckOther(products)).stream().count();
    }

    private List<Product> getCheckOther(List<Product> products) {
        return products.stream()
                .filter(product -> product.getOtherOffer() != null)
                .filter(product -> getBeverage().contains(product.getOtherOffer().getPromotionType()))
                .collect(Collectors.toList());
    }

    private List<Product> getCheckCoffee(List<Product> products) {
        return products.stream()
                .filter(product -> product.getCoffeeOffer() != null)
                .filter(product -> getBeverage().contains(product.getCoffeeOffer().getPromotionType()))
                .collect(Collectors.toList());
    }

    private List<Product> checkBeverage(List<Product> coffeeOffer, List<Product> otherOffer) {
        return Stream.concat(coffeeOffer.stream(), otherOffer.stream()).collect(Collectors.toList());
    }

    private List<String> getExtraOffer() {
        return List.of(ExtraOffer.values()).stream()
                .map(ExtraOffer::getLabel)
                .collect(Collectors.toList());
    }

    private List<String> getBeverage() {
        return List.of(CoffeeOffer.values()).stream()
                .map(CoffeeOffer::getPromotionType)
                .collect(Collectors.toList());
    }

    private List<Integer> getRange() {
        return List.of(CountBeverage.values()).stream()
                .map(CountBeverage::getRangeOfBeverage)
                .collect(Collectors.toList());
    }
}

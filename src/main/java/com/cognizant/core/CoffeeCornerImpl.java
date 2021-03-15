package com.cognizant.core;

import com.cognizant.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        Receipt receipt = calculateDiscount(products, checkExtra, discount,checkBeverage);
        return receipt;
    }

    private Receipt calculateDiscount(List<Product> products, boolean checkExtra, long discount, long checkBeverage) {
        Receipt receipt = new Receipt();
        TreeMap<Integer, Double> map = new TreeMap<>();
        products.stream()
                .filter(product -> product.getOtherOffer() != null)
                .forEach(product -> {
                    if (product.getOtherOffer().getLabel() != null)
                        map.put(product.getId(), product.getPrice());
                });
        if (checkExtra) {
            setCheckCoffeeOffer(products, map);
            while (discount > 0L) {
                int key = getRandomNumber(map.firstKey(), map.lastKey());
                map.put(key, removeExtra(map, key));
                discount--;
            }
        } else {
            setCheckCoffeeOffer(products, map);
        }

        receipt.setMap(map);
        receipt.setProducts(products.stream().map(Product::getName).collect(Collectors.toList()));
        receipt.setDate(LocalDate.now());
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


    private double removeExtra(TreeMap<Integer, Double> map, int key) {
        return map.get(key) - ExtraOffer.ExtraMilk.getValue();
    }

    private int getRandomNumber(int min, int max) {
        return new Random().ints(min, max)
                .findFirst()
                .getAsInt();
    }

    private long countBeverage(List<Product> products) {
        long checkCoffee = products.stream()
                .filter(product -> product.getCoffeeOffer() != null)
                .filter(product -> getBeverage().contains(product.getCoffeeOffer().getPromotionType()))
                .count();
        long checkOther = products.stream()
                .filter(product -> product.getOtherOffer() != null)
                .filter(product -> getBeverage().contains(product.getOtherOffer().getPromotionType()))
                .count();
        return checkCoffee + checkOther;
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

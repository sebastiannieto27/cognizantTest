package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Receipt {

    private TreeMap<Integer,Double> map = new TreeMap<>();
    private List<String> products = new ArrayList<>();
    private LocalDate date;
    private double discount;
    private  double total;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to Charlene's Coffee Corner: ").append("\n");
        sb.append("Date      : ").append(date).append("\n");
        sb.append(String.format("%-30s %-20s\n", "Item", "Price"));
        sb.append(String.format("%-30s %-20s\n", "----", "-----"));

        for(int i=0;i<products.size();i++){
            sb.append(String.format("%-30s %-20.2f\n", products.get(i),getValues().get(i)));
        }
        sb.append("\n");
        sb.append("Discount  : ").append(String.format("%23.2f", discount)).append(" ").append("\n");
        sb.append("Total     : ").append(String.format("%23.2f", total - discount)).append(" ").append("\n");
        return sb.toString();
    }

    public List<Double> getValues() {
        return  map.values().stream().collect(Collectors.toList());
    }

}

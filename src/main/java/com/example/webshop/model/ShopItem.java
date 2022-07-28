package com.example.webshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopItem {
    private String name;
    private String description;
    private double price;
    private long quantityOfStock;

    public ShopItem(String name, String description, double price, long quantityOfStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityOfStock = quantityOfStock;
    }
}

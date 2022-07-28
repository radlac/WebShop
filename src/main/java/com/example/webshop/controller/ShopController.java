package com.example.webshop.controller;

import com.example.webshop.model.ShopItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;




@Controller
@RequestMapping
public class ShopController {
    private List<ShopItem> shopItems = new ArrayList<>();

    public ShopController() {
        shopItems.add(new ShopItem("Running shoes", "Nike running shoes for every day sport", 1000, 5));
        shopItems.add(new ShopItem("Printer", "Some HP printer that will print pages", 3000, 2));
        shopItems.add(new ShopItem("Coca-cola", "0.5l standard coke", 25, 0));
        shopItems.add(new ShopItem("Wokin", "Chicken with fried rice and WOKIN sauce", 119, 100));
        shopItems.add(new ShopItem("T-shirt", "Blue with a corgi on a bike", 300, 1));
    }

    @GetMapping("/webshop")
    public String addItems(Model model) {
        model.addAttribute("items", shopItems);
        return "index";
    }

    @GetMapping("availableOnly")
    public String available(Model model) {
        List<ShopItem> returnAvailable = shopItems.stream().filter(item -> item.getQuantityOfStock() > 0).collect(Collectors.toList());
        model.addAttribute("items", returnAvailable);
        return "index";
    }

    @GetMapping("containsNike")
    public String containsNike(Model model) {
        List<ShopItem> returnNike = shopItems.stream().filter(item -> item.getDescription().contains("Nike")).collect(Collectors.toList());
        model.addAttribute("items", returnNike);
        return "index";
    }

    @GetMapping("cheapestOrder")
    public String cheapestFirst(Model model) {
        List<ShopItem> returnCheapestFirst = shopItems.stream().sorted(Comparator.comparingDouble(ShopItem::getPrice)).collect(Collectors.toList());
        model.addAttribute("items", returnCheapestFirst);
        return "index";
    }

    @GetMapping("mostExpensive")
    public String expensiveFirst(Model model) {
        String returnMostExpensive = shopItems.stream().max(Comparator.comparingDouble(ShopItem::getPrice)).get().getName();
        model.addAttribute("expensiveItem", "Most expensive item: " + returnMostExpensive);
        return "stock";

    }

    @GetMapping("stockAverage")
    public String returnAverage(Model model) {
        Double returnItemAverage = shopItems.stream().mapToDouble(ShopItem::getQuantityOfStock).average().getAsDouble();
        model.addAttribute("averageStock", "Average stock: " + returnItemAverage);
        return "stock";
    }

    @PostMapping(value ="/search")
    public String search(Model model, @RequestParam() String text) {
        List<ShopItem> searchItem = shopItems.stream().filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("items", searchItem);
        return "index";
    }
}
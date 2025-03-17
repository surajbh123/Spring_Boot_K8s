package com.ecommerce.orderservice;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    private static final List<Order> orders = new ArrayList<>();
    private static final String[] PRODUCT_NAMES = {
            "Laptop", "Smartphone", "Headphones", "Keyboard", "Monitor", "Mouse", "E-Book",
            "Online Course", "Smartwatch", "Tablet", "Camera", "Speaker", "Charger", "Backpack",
            "Notebook", "Printer", "Router", "SSD", "Graphics Card", "Power Bank"
    };
    private static final int PRODUCT_COUNT = 500;

    // Static block to initialize orders
    static {
        Random random = new Random();
        for (long i = 1; i <= PRODUCT_COUNT; i++) {
            String name = PRODUCT_NAMES[random.nextInt(PRODUCT_NAMES.length)];
            double price = Math.round((50 + (950 * random.nextDouble())) * 100.0) / 100.0; // Random price between 50 and 1000
            orders.add(new Order(i, name, price));
        }
    }

    public List<Order> getAllOrders() {
        return orders;
    }


    // Get order by ID
    public Order getOrderById(Long id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst()
                .get();
    }

    // Create a new order
    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    // Update an existing order
    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                order.setName(updatedOrder.getName());
                order.setPrice(updatedOrder.getPrice());
                return Optional.of(order);
            }
        }
        return Optional.empty(); // Order not found
    }

    // Delete an order
    public boolean deleteOrder(Long id) {
        return orders.removeIf(order -> order.getId().equals(id));
    }

}

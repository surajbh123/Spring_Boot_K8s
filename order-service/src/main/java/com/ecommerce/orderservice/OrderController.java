package com.ecommerce.orderservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final DPCResource dpcResource;

    public static Integer serviceId;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    static {
        // Generate a random integer between 1 and 100 (inclusive)
        serviceId = (int) (Math.random() * 100) + 1;
        System.out.println("Random number generated in static block: " + serviceId);
    }

    // Constructor-based dependency injection
    public OrderController(OrderService orderService, DPCResource dpcResource) {
        this.orderService = orderService;
        this.dpcResource = dpcResource;
    }

    // GET: Retrieve all orders
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Order>>> getOrders() {
        logger.info("Fetching all orders");
        ApiResponse<List<Order>> body = new ApiResponse<>(null, null, serviceId, 200);
        List<Order> orders = orderService.getAllOrders()
                .stream()
                .map(order -> {
                    ApiResponse<Double> productPrice = this.dpcResource.getProductPrice(order.getId());
                    order.setPrice(productPrice.getData());
                    body.setServiceIds(productPrice.getServiceIds());
                    return order;
                } ).collect(Collectors.toList());
        logger.debug("Fetched {} orders", orders.size());
        body.setData(orders);
        return ResponseEntity.ok(body);
    }

    // GET: Retrieve order by ID

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order with ID: {}", id);
        Order order = orderService.getOrderById(id);
        ApiResponse<Double> priceRes = dpcResource.getProductPrice(id);
        order.setPrice(priceRes.getData());
        ApiResponse<Order> orderApiResponse = new ApiResponse<>(order, null, serviceId, 200);
        orderApiResponse.getServiceIds().addAll(priceRes.getServiceIds());
        return ResponseEntity.ok(orderApiResponse);
    }

    // POST: Create a new order
    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
        logger.info("Creating a new order");
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(new ApiResponse<>(createdOrder, null, serviceId, 201));
    }

    // PUT: Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        logger.info("Updating order with ID: {}", id);
        Optional<Order> order = orderService.updateOrder(id, updatedOrder);
        return order.map(value -> ResponseEntity.ok(new ApiResponse<>(value, null, serviceId, 200)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(null, "Order not found", serviceId, 404)));
    }

    // DELETE: Remove an order
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable Long id) {
        logger.info("Deleting order with ID: {}", id);
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>("Order with ID " + id + " deleted successfully.", null, serviceId, 200));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(null, "Order not found", serviceId, 404));
    }
}

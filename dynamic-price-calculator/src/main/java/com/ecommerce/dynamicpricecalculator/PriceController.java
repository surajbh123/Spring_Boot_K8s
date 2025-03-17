package com.ecommerce.dynamicpricecalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@RestController
@RequestMapping("/price")
public class PriceController {

    private final Random random = new Random();
    public static Integer serviceId;
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);


    static {
        // Generate a random integer between 1 and 100 (inclusive)
        serviceId = (int) (Math.random() * 100) + 1;
        System.out.println("Random number generated in static block: " + serviceId);
    }

    // GET: Generate a random price for a product based on product ID
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<BigDecimal>> getPrice(@PathVariable Long productId) {
        logger.info("Fetching price for product ID: {}", productId);
        BigDecimal price = calculateRandomPrice(productId);
        logger.debug("Calculated price for product ID {}: {}", productId, price);
        ApiResponse<BigDecimal> res = new ApiResponse<>(price, null, serviceId, 200);

        return ResponseEntity.ok(res);
    }

    // Method to generate a random price between 10 and 1000 (customize if needed)
    private BigDecimal calculateRandomPrice(Long productId) {
        double minPrice = 10.0;
        double maxPrice = 1000.0;
        double randomPrice = minPrice + (maxPrice - minPrice) * random.nextDouble();
        return BigDecimal.valueOf(randomPrice).setScale(2, RoundingMode.HALF_UP);
    }
}

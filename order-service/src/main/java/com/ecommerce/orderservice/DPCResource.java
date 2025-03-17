package com.ecommerce.orderservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class DPCResource {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Value("${dpc.service.url}")
    private String productServiceUrl;

    public DPCResource(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }
    public ApiResponse<Double> getProductPrice(Long productId) {
        String url = productServiceUrl + "/price/"+productId;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ApiResponse<Double> apiResponse = objectMapper.readValue(response.body(),
                        new TypeReference<ApiResponse<Double>>() {});
                return apiResponse;
            } else {
                throw new RuntimeException("Failed to fetch product price: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while calling product-service", e);
        }
    }


}

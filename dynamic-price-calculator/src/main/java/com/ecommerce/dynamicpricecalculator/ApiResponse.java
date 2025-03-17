package com.ecommerce.dynamicpricecalculator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ApiResponse<T> {
    private T data;
    private String errors;
    @JsonProperty("serviceIds")
    private Set<Integer> serviceIds = new HashSet<>();
    private int code;

    public ApiResponse(T data, String errors, Integer serviceId, int code) {
        this.data = data;
        this.errors = errors;
        this.serviceIds.add(serviceId);
        this.code = code;
    }

    public ApiResponse() {
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Set getServiceIds() {
        return serviceIds;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceIds.add(serviceId);
    }

    public void setServiceIds(Set serviceIds) {
        this.serviceIds.addAll(serviceIds);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

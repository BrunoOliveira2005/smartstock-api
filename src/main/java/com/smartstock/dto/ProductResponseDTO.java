package com.smartstock.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantityInStock;
    private Integer minimumStock;

    public ProductResponseDTO(Long id, String name, String category,
                              BigDecimal price, Integer quantityInStock, Integer minimumStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.minimumStock = minimumStock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }
}
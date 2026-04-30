package com.smartstock.mapper;

import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setMinimumStock(dto.getMinimumStock());

        return product;
    }

    public static ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getMinimumStock()
        );
    }
}
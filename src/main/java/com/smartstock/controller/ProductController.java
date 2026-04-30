package com.smartstock.controller;

import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.mapper.ProductMapper;
import com.smartstock.response.ApiResponse;
import com.smartstock.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResponse<List<ProductResponseDTO>> listAll() {
        List<ProductResponseDTO> products = productService.listAll()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ApiResponse<>("Products listed successfully", products);
    }

    @PostMapping
    public ApiResponse<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = ProductMapper.toResponseDTO(
                productService.save(ProductMapper.toEntity(dto))
        );

        return new ApiResponse<>("Product created successfully", product);
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = ProductMapper.toResponseDTO(
                productService.update(id, ProductMapper.toEntity(dto))
        );

        return new ApiResponse<>("Product updated successfully", product);
    }

    @GetMapping("/low-stock")
    public ApiResponse<List<ProductResponseDTO>> getLowStock() {
        List<ProductResponseDTO> products = productService.findLowStock()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ApiResponse<>("Low stock products listed successfully", products);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ApiResponse<>("Product deleted successfully", null);
    }
}
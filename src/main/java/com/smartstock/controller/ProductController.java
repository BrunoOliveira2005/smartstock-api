package com.smartstock.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.smartstock.dto.PageResponseDTO;
import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.mapper.ProductMapper;
import com.smartstock.response.ApiResponse;
import com.smartstock.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "List products",
            description = "Returns a paginated list of products. Allows filtering by name and category."
    )
    @GetMapping
    public ApiResponse<PageResponseDTO<ProductResponseDTO>> listAll(
            @Parameter(description = "Filter by product name")
            @RequestParam(required = false) String name,

            @Parameter(description = "Filter by product category")
            @RequestParam(required = false) String category,

            @ParameterObject Pageable pageable
    ) {

        Page<ProductResponseDTO> productsPage = productService
                .listAll(name, category, pageable)
                .map(ProductMapper::toResponseDTO);

        PageResponseDTO<ProductResponseDTO> response = new PageResponseDTO<>(
                productsPage.getContent(),
                productsPage.getNumber(),
                productsPage.getSize(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious()
        );

        return new ApiResponse<>("Products listed successfully", response);
    }

    @Operation(
            summary = "Create product",
            description = "Creates a new product in stock."
    )
    @PostMapping
    public ApiResponse<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = ProductMapper.toResponseDTO(
                productService.save(ProductMapper.toEntity(dto))
        );

        return new ApiResponse<>("Product created successfully", product);
    }

    @Operation(
            summary = "Update product",
            description = "Updates an existing product by ID."
    )
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> update(
            @Parameter(description = "Product ID")
            @PathVariable Long id,

            @Valid @RequestBody ProductRequestDTO dto
    ) {
        ProductResponseDTO product = ProductMapper.toResponseDTO(
                productService.update(id, ProductMapper.toEntity(dto))
        );

        return new ApiResponse<>("Product updated successfully", product);
    }

    @Operation(
            summary = "List low stock products",
            description = "Returns products where quantity in stock is lower than the minimum stock."
    )
    @GetMapping("/low-stock")
    public ApiResponse<List<ProductResponseDTO>> getLowStock() {
        List<ProductResponseDTO> products = productService.findLowStock()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ApiResponse<>("Low stock products listed successfully", products);
    }

    @Operation(
            summary = "Delete product",
            description = "Deletes an existing product by ID."
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "Product ID")
            @PathVariable Long id
    ) {
        productService.delete(id);
        return new ApiResponse<>("Product deleted successfully", null);
    }
}
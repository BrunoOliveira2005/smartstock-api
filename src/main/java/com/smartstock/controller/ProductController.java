package com.smartstock.controller;

import com.smartstock.dto.PageResponseDTO;
import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.mapper.ProductMapper;
import com.smartstock.response.ApiResponse;
import com.smartstock.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ApiResponse<PageResponseDTO<ProductResponseDTO>> listAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            Pageable pageable
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
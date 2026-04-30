package com.smartstock.controller;

import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.mapper.ProductMapper;
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
    public List<ProductResponseDTO> listAll() {
        return productService.listAll()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    @PostMapping
    public ProductResponseDTO save(@Valid @RequestBody ProductRequestDTO dto) {
        return ProductMapper.toResponseDTO(
                productService.save(ProductMapper.toEntity(dto))
        );
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return ProductMapper.toResponseDTO(
                productService.update(id, ProductMapper.toEntity(dto))
        );
    }

    @GetMapping("/low-stock")
    public List<ProductResponseDTO> getLowStock() {
        return productService.findLowStock()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
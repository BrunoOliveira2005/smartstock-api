package com.smartstock.controller;

import com.smartstock.dto.ProductRequestDTO;
import com.smartstock.dto.ProductResponseDTO;
import com.smartstock.model.Product;
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
                .map(this::convertToResponseDTO)
                .toList();
    }

    @PostMapping
    public ProductResponseDTO save(@Valid @RequestBody ProductRequestDTO dto) {
        Product product = convertToEntity(dto);
        Product savedProduct = productService.save(product);
        return convertToResponseDTO(savedProduct);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        Product product = convertToEntity(dto);
        Product updatedProduct = productService.update(id, product);
        return convertToResponseDTO(updatedProduct);
    }

    @GetMapping("/low-stock")
    public List<ProductResponseDTO> getLowStock() {
        return productService.findLowStock()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    private Product convertToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setMinimumStock(dto.getMinimumStock());
        return product;
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {
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
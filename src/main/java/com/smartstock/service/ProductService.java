package com.smartstock.service;

import com.smartstock.model.Product;
import com.smartstock.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {

        if (product.getQuantityInStock() != null &&
            product.getMinimumStock() != null &&
            product.getQuantityInStock() < product.getMinimumStock()) {

            System.out.println("ALERTA: Produto com estoque baixo -> " + product.getName());
        }

        return productRepository.save(product);
    }
    public Product update(Long id, Product updatedProduct) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());
        existingProduct.setMinimumStock(updatedProduct.getMinimumStock());

        return productRepository.save(existingProduct);
    }

    public List<Product> findLowStock() {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getQuantityInStock() != null)
                .filter(p -> p.getMinimumStock() != null)
                .filter(p -> p.getQuantityInStock() < p.getMinimumStock())
                .toList();
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}

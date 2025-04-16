package com.reactive.reactive.controler;

import com.reactive.reactive.model.Product;
import com.reactive.reactive.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public Flux<Product> getAll() {
        return productRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id) {
        return productRepo.findById(id);
    }

    @PostMapping
    public Mono<Product> create(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable String id, @RequestBody Product product) {
        return productRepo.findById(id)
                .flatMap(existing -> {
                    existing.setName(product.getName());
                    existing.setPrice(product.getPrice());
                    return productRepo.save(existing);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return productRepo.deleteById(id);
    }
}
package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> index(@RequestParam(defaultValue = "-1") Integer min, @RequestParam(defaultValue = "-2") Integer max) {
        if(min != -1 && max != -2) {
            return productRepository.findAll(Sort.by(Sort.Order.asc("price"))).stream().filter(p -> p.getPrice() >= min && p.getPrice() <= max).toList();
        } else if (min != -1) {
            return productRepository.findAll(Sort.by(Sort.Order.asc("price"))).stream().filter(p -> p.getPrice() >= min).toList();
        } else if (max != -2) {
            return productRepository.findAll(Sort.by(Sort.Order.asc("price"))).stream().filter(p ->p.getPrice() <= max).toList();
        } else {
            return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        }
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}

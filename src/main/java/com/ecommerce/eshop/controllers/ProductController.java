package com.ecommerce.eshop.controllers;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.service.CategoryService;
import com.ecommerce.eshop.service.ProductService;
import com.ecommerce.eshop.utils.excepctions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @GetMapping("/promo")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> getAllPromoProducts(){
        return productService.getAllPromoProducts();
    }

    @GetMapping("/price")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> getAllByPrice(@RequestParam(required = false) String order,
                                       @RequestParam(required = false) String category){

        if (category != null && order != null && order.equalsIgnoreCase("asc")){
            return productService.getAllByPriceAndCategoryAsc(category);
        }
        if (category != null && order != null && order.equalsIgnoreCase("desc")){
            return productService.getAllByPriceAndCategoryDesc(category);
        }

        if (order != null && order.equalsIgnoreCase("asc")){
            return productService.getAllProductsByPriceAsc();
        }
        if (order != null && order.equalsIgnoreCase("desc")){
            return productService.getAllProductsByPriceDesc();
        }

        return productService.getAllProductsByPriceAsc();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.FOUND)
//   np http://localhost:8080/products/filter?category=szabla
    public List<Product> getAllByCategory(@RequestParam String category){
        return productService.getAllByCategory(category);
    }

    @GetMapping("/searchByName")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product> getAllByName(@RequestParam String name){
        return productService.getAllByName(name);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@Valid @RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Product deleteProduct(@PathVariable Long id){
        return productService.deactivate(id);
    }

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError productNotFoundExceptionHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ProductCreationException.class, CategoryCreationException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiError creationExceptionHandler(RuntimeException runtimeException){
        return new ApiError(UUID.randomUUID(), runtimeException.getMessage(), LocalDateTime.now());
    }


}

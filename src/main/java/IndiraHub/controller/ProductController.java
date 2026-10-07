package IndiraHub.controller;

import IndiraHub.dto.ApiResponse;
import IndiraHub.dto.ReviewRequest;
import IndiraHub.model.Product;
import IndiraHub.model.Review;
import IndiraHub.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {

        if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(productService.searchProducts(search));
        }

        if (category != null && !category.trim().equalsIgnoreCase("all") && !category.trim().isEmpty()) {
            return ResponseEntity.ok(productService.getProductsByCategory(category));
        }

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(p -> ResponseEntity.ok(ApiResponse.success("Product found", p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Product not found with id: " + id)));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(updated -> ResponseEntity.ok(ApiResponse.success("Product updated successfully", updated)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Product not found with id: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        if (productService.getProductById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found with id: " + id));
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductReviews(id));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<Review>> addReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {
        try {
            Review review = productService.addReview(id, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Thank you! Your review has been published.", review));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}

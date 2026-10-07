package IndiraHub.service;

import IndiraHub.dto.ReviewRequest;
import IndiraHub.model.Product;
import IndiraHub.model.Review;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.searchProducts(query.trim());
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            if (updatedProduct.getName() != null) existing.setName(updatedProduct.getName());
            if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
            if (updatedProduct.getCategory() != null) existing.setCategory(updatedProduct.getCategory());
            if (updatedProduct.getImageUrl() != null) existing.setImageUrl(updatedProduct.getImageUrl());
            if (updatedProduct.getStock() != null) existing.setStock(updatedProduct.getStock());
            if (updatedProduct.getBadge() != null) existing.setBadge(updatedProduct.getBadge());
            return productRepository.save(existing);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }

    @Transactional
    public Review addReview(Long productId, ReviewRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Review review = new Review(
                product,
                request.getReviewerName(),
                request.getReviewerEmail(),
                request.getRating(),
                request.getComment()
        );

        Review savedReview = reviewRepository.save(review);

        // Recalculate average rating & reviews count
        List<Review> allReviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
        int totalReviews = allReviews.size();
        double avg = allReviews.stream().mapToInt(Review::getRating).average().orElse(request.getRating());
        double roundedRating = Math.round(avg * 10.0) / 10.0;

        product.setReviewsCount(totalReviews);
        product.setRating(roundedRating);
        productRepository.save(product);

        return savedReview;
    }
}

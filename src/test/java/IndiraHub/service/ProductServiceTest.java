package IndiraHub.service;

import IndiraHub.dto.ReviewRequest;
import IndiraHub.model.Product;
import IndiraHub.model.Review;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                "Test Laptop",
                "Powerful laptop for developers",
                49999.00,
                "Electronics",
                "https://example.com/laptop.jpg",
                10,
                4.5,
                10,
                "Hot"
        );
        testProduct.setId(1L);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Test Laptop", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductsByCategory() {
        when(productRepository.findByCategoryIgnoreCase("Electronics")).thenReturn(List.of(testProduct));

        List<Product> result = productService.getProductsByCategory("Electronics");

        assertFalse(result.isEmpty());
        assertEquals("Electronics", result.get(0).getCategory());
        verify(productRepository, times(1)).findByCategoryIgnoreCase("Electronics");
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Optional<Product> found = productService.getProductById(1L);

        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product updateData = new Product();
        updateData.setPrice(45000.0);
        updateData.setStock(15);

        Optional<Product> updated = productService.updateProduct(1L, updateData);

        assertTrue(updated.isPresent());
        assertEquals(45000.0, testProduct.getPrice());
        assertEquals(15, testProduct.getStock());
    }

    @Test
    void testAddReview() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        Review sampleReview = new Review(testProduct, "Alice", "alice@example.com", 5, "Excellent device!");
        when(reviewRepository.findByProductIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(sampleReview));

        ReviewRequest request = new ReviewRequest("Alice", "alice@example.com", 5, "Excellent device!");
        Review created = productService.addReview(1L, request);

        assertNotNull(created);
        assertEquals("Alice", created.getReviewerName());
        assertEquals(5, created.getRating());
        verify(productRepository, times(1)).save(testProduct);
    }
}

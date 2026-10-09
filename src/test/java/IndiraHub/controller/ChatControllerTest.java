package IndiraHub.controller;

import IndiraHub.dto.ChatRequest;
import IndiraHub.dto.ChatResponse;
import IndiraHub.model.Order;
import IndiraHub.model.Product;
import IndiraHub.repository.OrderRepository;
import IndiraHub.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ChatController chatController;

    private Product laptopProduct;

    @BeforeEach
    void setUp() {
        laptopProduct = new Product(
                "ProBook Ultra 16\" Laptop",
                "Intel Core i7 13th Gen, 16GB DDR5 RAM",
                45999.00,
                "Electronics",
                "https://images.unsplash.com/photo-1517336714731",
                25,
                4.8,
                142,
                "Bestseller"
        );
    }

    @Test
    void testGetStatus() {
        ResponseEntity<ChatResponse> response = chatController.getStatus();
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("IndiraBot"));
        assertFalse(response.getBody().getSuggestions().isEmpty());
    }

    @Test
    void testEmptyMessageReturnsWelcome() {
        ChatRequest req = new ChatRequest("", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("IndiraBot"));
        assertFalse(response.getBody().getSuggestions().isEmpty());
    }

    @Test
    void testGreetingIntent() {
        ChatRequest req = new ChatRequest("hello", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("Welcome to **IndiraHub**"));
        // Ensure no product search was called for "hello"
        verify(productRepository, never()).searchProducts(anyString());
    }

    @Test
    void testShippingIntent() {
        ChatRequest req = new ChatRequest("what is shipping fee and delivery time?", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("IndiraHub Shipping Policy"));
        assertTrue(response.getBody().getReply().contains("FREE Delivery"));
    }

    @Test
    void testPaymentIntent() {
        ChatRequest req = new ChatRequest("how can I pay with UPI or COD?", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("UPI & QR Code"));
        assertTrue(response.getBody().getReply().contains("Cash on Delivery"));
    }

    @Test
    void testOrderTrackingFound() {
        Order mockOrder = new Order();
        mockOrder.setId(101L);
        mockOrder.setOrderStatus("CONFIRMED");
        mockOrder.setCustomerName("Test User");
        mockOrder.setTotalAmount(45999.00);
        mockOrder.setShippingAddress("Bangalore, India");
        mockOrder.setPaymentMethod("UPI");

        when(orderRepository.findById(101L)).thenReturn(Optional.of(mockOrder));

        ChatRequest req = new ChatRequest("track order 101", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("Order #101 Status"));
        assertTrue(response.getBody().getReply().contains("CONFIRMED"));
    }

    @Test
    void testOrderTrackingNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        ChatRequest req = new ChatRequest("track order 999", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("Order **#999** was not found"));
    }

    @Test
    void testCategoryRecommendation() {
        when(productRepository.findByCategoryIgnoreCase("Electronics"))
                .thenReturn(List.of(laptopProduct));

        ChatRequest req = new ChatRequest("show me laptops and electronic items", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().getRecommendedProducts().isEmpty());
        assertEquals("ProBook Ultra 16\" Laptop", response.getBody().getRecommendedProducts().get(0).getName());
    }

    @Test
    void testPriceBasedRecommendation() {
        when(productRepository.findAll()).thenReturn(List.of(laptopProduct));

        ChatRequest req = new ChatRequest("recommend a laptop under 50000", null);
        ResponseEntity<ChatResponse> response = chatController.chat(req);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getReply().contains("under **₹50,000**"));
        assertFalse(response.getBody().getRecommendedProducts().isEmpty());
    }
}

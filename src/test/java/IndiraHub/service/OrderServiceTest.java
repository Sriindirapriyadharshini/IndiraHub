package IndiraHub.service;

import IndiraHub.dto.OrderItemRequest;
import IndiraHub.dto.OrderRequest;
import IndiraHub.model.Order;
import IndiraHub.model.Product;
import IndiraHub.repository.OrderRepository;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.UserRepository;
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
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Lamp", "Desk lamp", 1000.0, "Home Decor", "img.jpg", 10, 4.5, 5, "New");
        testProduct.setId(10L);
    }

    @Test
    void testCreateOrder() {
        when(productRepository.findById(10L)).thenReturn(Optional.of(testProduct));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> {
            Order o = i.getArgument(0);
            o.setId(101L);
            return o;
        });

        OrderRequest request = new OrderRequest();
        request.setCustomerName("Bob");
        request.setCustomerEmail("bob@example.com");
        request.setCustomerPhone("9876543210");
        request.setShippingAddress("123 Street");
        request.setPaymentMethod("COD");
        request.setItems(List.of(new OrderItemRequest(10L, 2)));

        Order created = orderService.createOrder(request);

        assertNotNull(created);
        assertEquals(101L, created.getId());
        assertEquals(2000.0, created.getTotalAmount());
        assertEquals("CONFIRMED", created.getOrderStatus());
        assertEquals(8, testProduct.getStock()); // stock deducted
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = new Order();
        order.setId(101L);
        order.setOrderStatus("CONFIRMED");

        when(orderRepository.findById(101L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Optional<Order> updated = orderService.updateOrderStatus(101L, "SHIPPED");

        assertTrue(updated.isPresent());
        assertEquals("SHIPPED", updated.get().getOrderStatus());
        verify(orderRepository, times(1)).save(order);
    }
}

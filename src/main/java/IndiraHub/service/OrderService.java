package IndiraHub.service;

import IndiraHub.dto.OrderItemRequest;
import IndiraHub.dto.OrderRequest;
import IndiraHub.model.Order;
import IndiraHub.model.OrderItem;
import IndiraHub.model.Product;
import IndiraHub.model.User;
import IndiraHub.repository.OrderRepository;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId()).orElse(null);
        } else if (request.getCustomerEmail() != null) {
            user = userRepository.findByEmailIgnoreCase(request.getCustomerEmail()).orElse(null);
        }

        double totalAmount = 0.0;
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "COD");
        order.setOrderStatus("CONFIRMED");
        order.setPaymentStatus("PAID");

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemReq.getProductId()));

            int quantity = itemReq.getQuantity() != null && itemReq.getQuantity() > 0 ? itemReq.getQuantity() : 1;

            // Reduce stock if available
            if (product.getStock() != null && product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
            }

            double itemTotal = product.getPrice() * quantity;
            totalAmount += itemTotal;

            OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice());
            order.addItem(orderItem);
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public List<Order> getOrdersByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(orderRepository::findByUserOrderByOrderDateDesc).orElseGet(List::of);
    }

    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByCustomerEmailIgnoreCaseOrderByOrderDateDesc(email);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> updateOrderStatus(Long orderId, String newStatus) {
        return orderRepository.findById(orderId).map(order -> {
            order.setOrderStatus(newStatus.toUpperCase());
            return orderRepository.save(order);
        });
    }
}

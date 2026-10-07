package IndiraHub.controller;

import IndiraHub.model.Order;
import IndiraHub.model.Product;
import IndiraHub.repository.OrderRepository;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        double totalRevenue = orders.stream()
                .filter(o -> !"CANCELLED".equalsIgnoreCase(o.getOrderStatus()))
                .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount() : 0.0)
                .sum();

        long lowStockCount = products.stream()
                .filter(p -> p.getStock() != null && p.getStock() < 25)
                .count();

        Map<String, Long> categoryCount = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalOrders", orders.size());
        stats.put("totalProducts", products.size());
        stats.put("totalCustomers", userRepository.count());
        stats.put("lowStockCount", lowStockCount);
        stats.put("categoryCounts", categoryCount);

        return ResponseEntity.ok(stats);
    }
}

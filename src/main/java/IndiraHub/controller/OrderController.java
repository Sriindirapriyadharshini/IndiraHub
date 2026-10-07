package IndiraHub.controller;

import IndiraHub.dto.ApiResponse;
import IndiraHub.dto.OrderRequest;
import IndiraHub.model.Order;
import IndiraHub.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderRequest request) {
        try {
            Order order = orderService.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Order placed successfully! Order ID #" + order.getId(), order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to process order: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String email) {

        if (userId != null) {
            return ResponseEntity.ok(orderService.getOrdersByUser(userId));
        }
        if (email != null && !email.trim().isEmpty()) {
            return ResponseEntity.ok(orderService.getOrdersByEmail(email.trim()));
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(ApiResponse.success("Order found", order)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Order not found with id: " + id)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return orderService.updateOrderStatus(id, status)
                .map(order -> ResponseEntity.ok(ApiResponse.success("Order status updated to " + order.getOrderStatus(), order)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Order not found with id: " + id)));
    }
}

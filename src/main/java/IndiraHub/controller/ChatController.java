package IndiraHub.controller;

import IndiraHub.dto.ChatRequest;
import IndiraHub.dto.ChatResponse;
import IndiraHub.model.Order;
import IndiraHub.model.Product;
import IndiraHub.repository.OrderRepository;
import IndiraHub.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ChatController(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String msg = request.getMessage() != null ? request.getMessage().trim().toLowerCase(Locale.ROOT) : "";
        String userEmail = request.getUserEmail();

        String reply;
        List<String> suggestions = new ArrayList<>();
        List<Product> recommendedProducts = new ArrayList<>();

        if (msg.isEmpty()) {
            reply = "Hello! I am **IndiraBot**, your virtual assistant at IndiraHub. How can I help you today?";
            suggestions = List.of("Show Electronics", "Recommend Home Decor", "Track an Order", "Shipping Info");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 1. Order Tracking
        if (msg.contains("track") || msg.contains("order") || msg.contains("status")) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                Long orderId = Long.parseLong(matcher.group(1));
                var orderOpt = orderRepository.findById(orderId);
                if (orderOpt.isPresent()) {
                    Order o = orderOpt.get();
                    reply = String.format("📦 **Order #%d Status**: **%s**\n\n- Customer: %s\n- Total: ₹%,.2f\n- Destination: %s\n- Payment: %s",
                            o.getId(), o.getOrderStatus(), o.getCustomerName(), o.getTotalAmount(), o.getShippingAddress(), o.getPaymentMethod());
                    suggestions = List.of("Track another order", "Browse new arrivals", "Contact Support");
                    return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
                }
            }

            if (userEmail != null && !userEmail.isEmpty()) {
                List<Order> userOrders = orderRepository.findByCustomerEmailIgnoreCaseOrderByOrderDateDesc(userEmail);
                if (!userOrders.isEmpty()) {
                    Order latest = userOrders.get(0);
                    reply = String.format("📦 Found your latest order **#%d** with status **%s** (Total: ₹%,.2f).",
                            latest.getId(), latest.getOrderStatus(), latest.getTotalAmount());
                    suggestions = List.of("View order history", "Shop more items", "Track another order");
                    return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
                }
            }

            reply = "To track your order, please provide your Order ID (e.g., 'Track order 1') or sign in with your account email.";
            suggestions = List.of("Track order 1", "Shipping info", "Browse products");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 2. Shipping & Delivery
        if (msg.contains("shipping") || msg.contains("delivery") || msg.contains("dispatch") || msg.contains("charges")) {
            reply = "🚚 **IndiraHub Shipping Policy**:\n\n- **FREE Delivery** on all orders of ₹999 and above.\n- A flat delivery fee of ₹99 applies to orders under ₹999.\n- Same-day dispatch with 2-4 business days express delivery across India.";
            suggestions = List.of("Show Electronics", "Show Books", "Payment Methods");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 3. Returns & Refunds
        if (msg.contains("return") || msg.contains("refund") || msg.contains("exchange") || msg.contains("warranty")) {
            reply = "🛡️ **Warranty & Returns**:\n\n- 7-day hassle-free replacement on defective items.\n- 100% genuine brand warranty on all electronics.\n- Easy return pickups initiated directly from your account portal.";
            suggestions = List.of("Browse Electronics", "Contact Support", "Track Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 4. Payment Methods
        if (msg.contains("payment") || msg.contains("upi") || msg.contains("cod") || msg.contains("pay")) {
            reply = "💳 We support multiple secure payment options:\n- **UPI & QR Code** (Google Pay, PhonePe, Paytm)\n- **Cash on Delivery (COD)**\n- **Credit / Debit Cards** (Visa, MasterCard, RuPay)\n- **Net Banking**";
            suggestions = List.of("Browse Catalog", "Check Shipping", "Track Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 5. Product Recommendations by Category or Keywords
        String matchedCategory = null;
        if (msg.contains("electronic") || msg.contains("laptop") || msg.contains("phone") || msg.contains("headphone")) {
            matchedCategory = "Electronics";
        } else if (msg.contains("home") || msg.contains("decor") || msg.contains("lamp") || msg.contains("chair")) {
            matchedCategory = "Home Decor";
        } else if (msg.contains("fitness") || msg.contains("gym") || msg.contains("dumbbell") || msg.contains("workout")) {
            matchedCategory = "Fitness";
        } else if (msg.contains("kitchen") || msg.contains("bake") || msg.contains("cook") || msg.contains("oven")) {
            matchedCategory = "Kitchen & Baking";
        } else if (msg.contains("beauty") || msg.contains("makeup") || msg.contains("skin") || msg.contains("primer")) {
            matchedCategory = "Beauty & Makeup";
        } else if (msg.contains("fashion") || msg.contains("apparel") || msg.contains("dress") || msg.contains("cloth") || msg.contains("hoodie")) {
            matchedCategory = "Apparel & Fashion";
        } else if (msg.contains("snack") || msg.contains("grocery") || msg.contains("food") || msg.contains("fruit") || msg.contains("tea")) {
            matchedCategory = "Groceries & Snacks";
        } else if (msg.contains("book") || msg.contains("read") || msg.contains("java") || msg.contains("python")) {
            matchedCategory = "Books";
        }

        if (matchedCategory != null) {
            recommendedProducts = productRepository.findByCategoryIgnoreCase(matchedCategory);
            reply = String.format("Here are top recommendations from our **%s** collection:", matchedCategory);
            suggestions = List.of("Show another category", "Shipping details", "Help with checkout");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // Generic keyword search
        List<Product> searchResults = productRepository.searchProducts(msg);
        if (!searchResults.isEmpty()) {
            recommendedProducts = searchResults.stream().limit(4).toList();
            reply = String.format("I found %d matching items for '%s':", searchResults.size(), request.getMessage());
            suggestions = List.of("Show All Items", "Filter by Category", "Track an Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        reply = "I couldn't find a direct match, but IndiraHub carries top items in Electronics, Home Decor, Fitness, Kitchen, Beauty, Fashion, Snacks, and Books. What can I explore for you?";
        suggestions = List.of("📱 Electronics", "🏠 Home Decor", "🏋️ Fitness", "🥨 Snacks");
        return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
    }
}

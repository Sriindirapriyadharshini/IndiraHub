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

    @GetMapping
    public ResponseEntity<ChatResponse> getStatus() {
        String reply = "👋 Hello! I am **IndiraBot**, your AI shopping assistant at IndiraHub. How can I help you today?";
        List<String> suggestions = List.of("Show Electronics", "Recommend Home Decor", "Track an Order", "Shipping Info");
        return ResponseEntity.ok(new ChatResponse(reply, suggestions, List.of()));
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody(required = false) ChatRequest request) {
        String msg = (request != null && request.getMessage() != null)
                ? request.getMessage().trim().toLowerCase(Locale.ROOT)
                : "";
        String userEmail = request != null ? request.getUserEmail() : null;

        String reply;
        List<String> suggestions = new ArrayList<>();
        List<Product> recommendedProducts = new ArrayList<>();

        if (msg.isEmpty()) {
            reply = "👋 Hello! I am **IndiraBot**, your virtual assistant at IndiraHub. How can I help you today?";
            suggestions = List.of("Show Electronics", "Recommend Home Decor", "Track an Order", "Shipping Info");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 0. Greetings & Small Talk
        if (msg.equals("hi") || msg.equals("hello") || msg.equals("hey") || msg.startsWith("hi ") || msg.startsWith("hello ")
                || msg.contains("namaste") || msg.contains("vanakkam") || msg.contains("good morning")
                || msg.contains("good afternoon") || msg.contains("good evening") || msg.equals("start")) {
            reply = "👋 Hello! Welcome to **IndiraHub**! I am **IndiraBot**, ready to help you discover products, track orders, check shipping, or find the best deals.";
            suggestions = List.of("💻 Recommend Laptop", "📱 Electronics", "🚚 Free Shipping", "📦 Track Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 0.1 Bot Identity & Help
        if (msg.contains("who are you") || msg.contains("what can you do") || msg.equals("help") || msg.contains("about you") || msg.contains("bot")) {
            reply = "🤖 I am **IndiraBot**, your intelligent shopping companion at IndiraHub! Here is what I can do:\n\n"
                    + "• 🔍 **Search & Recommend Products**: Ask for laptops, phones, books, fitness gear, and more\n"
                    + "• 📦 **Track Orders**: Give me your Order ID (e.g., 'Track order 1') to get live updates\n"
                    + "• 🚚 **Delivery Info**: Check our delivery timelines and shipping fees\n"
                    + "• 💳 **Payment & Offers**: Ask about UPI, cards, COD, or discount coupon codes\n"
                    + "• 🛡️ **Returns & Warranty**: Learn about our 7-day hassle-free replacement policy";
            suggestions = List.of("Show Electronics", "Track Order", "Shipping Info", "Payment Methods");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 0.2 Gratitude & Exit
        if (msg.contains("thank") || msg.equals("bye") || msg.contains("goodbye") || msg.equals("ok") || msg.equals("okay") || msg.equals("cool")) {
            reply = "You're very welcome! Feel free to ask whenever you need assistance. Happy shopping at **IndiraHub**! 🛍️✨";
            suggestions = List.of("Browse Electronics", "Browse Books", "Track an Order");
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
                } else {
                    reply = String.format("❌ Order **#%d** was not found in our database. Please double-check your Order ID or contact support.", orderId);
                    suggestions = List.of("Track order 1", "Contact Support", "Browse products");
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
        if (msg.contains("shipping") || msg.contains("delivery") || msg.contains("dispatch") || msg.contains("charges") || msg.contains("courier") || msg.contains("freight")) {
            reply = "🚚 **IndiraHub Shipping Policy**:\n\n"
                    + "• **FREE Delivery** on all orders of ₹999 and above.\n"
                    + "• A flat delivery fee of ₹99 applies to orders under ₹999.\n"
                    + "• **Express Dispatch**: Same-day dispatch with 2-4 business days express delivery across India.\n"
                    + "• Real-time parcel tracking provided on every shipment.";
            suggestions = List.of("Show Electronics", "Show Books", "Payment Methods");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 3. Returns & Refunds & Warranty
        if (msg.contains("return") || msg.contains("refund") || msg.contains("exchange") || msg.contains("warranty") || msg.contains("replace") || msg.contains("damaged")) {
            reply = "🛡️ **Warranty & Returns**:\n\n"
                    + "• **7-Day Replacement**: Hassle-free replacement on any defective or damaged items.\n"
                    + "• **100% Genuine Brand Warranty**: Official manufacturer warranty on all electronics.\n"
                    + "• **Doorstep Pickup**: Convenient reverse logistics arranged right from your address.\n"
                    + "• **Prompt Refunds**: Refund issued within 24-48 hours of inspection approval.";
            suggestions = List.of("Browse Electronics", "Contact Support", "Track Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 4. Payment Methods
        if (msg.contains("payment") || msg.contains("upi") || msg.contains("cod") || msg.contains("pay") || msg.contains("card") || msg.contains("google pay") || msg.contains("phonepe")) {
            reply = "💳 We support multiple secure payment options:\n\n"
                    + "• **UPI & QR Code** (Google Pay, PhonePe, Paytm, BHIM)\n"
                    + "• **Cash on Delivery (COD)** on eligible PIN codes\n"
                    + "• **Credit & Debit Cards** (Visa, MasterCard, RuPay)\n"
                    + "• **Net Banking** from all major Indian banks\n"
                    + "• 256-bit SSL encryption for 100% secure checkout.";
            suggestions = List.of("Browse Catalog", "Check Shipping", "Track Order");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 4.1 Offers, Discounts & Coupons
        if (msg.contains("offer") || msg.contains("discount") || msg.contains("coupon") || msg.contains("deal") || msg.contains("promo") || msg.contains("sale")) {
            reply = "🎉 **Current IndiraHub Promotions**:\n\n"
                    + "• **Coupon Code `INDIRA10`**: Extra 10% instant discount on orders above ₹1,499!\n"
                    + "• **Super Electronics Deal**: Up to 40% OFF on laptops, audio gear, and accessories.\n"
                    + "• **Free Shipping**: Automatically applied when your cart exceeds ₹999.";
            suggestions = List.of("Show Electronics", "Show Books", "Payment Methods");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 4.2 Contact & Customer Support
        if (msg.contains("contact") || msg.contains("customer care") || msg.contains("support") || msg.contains("helpline") || msg.contains("email") || msg.contains("phone")) {
            reply = "📞 **IndiraHub Customer Support**:\n\n"
                    + "• **Email**: support@indirahub.com\n"
                    + "• **Toll-Free Helpline**: 1800-123-4567 (Mon-Sat, 9:00 AM - 8:00 PM IST)\n"
                    + "• **Live Chat**: IndiraBot is active 24/7 right here to guide your shopping journey!";
            suggestions = List.of("Track an Order", "Shipping Info", "Browse Catalog");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 5. Price-based recommendations (e.g. "under 50000", "under 1000", "under 2000")
        Pattern pricePattern = Pattern.compile("under\\s+(\\d+)");
        Matcher priceMatcher = pricePattern.matcher(msg);
        if (priceMatcher.find()) {
            double maxPrice = Double.parseDouble(priceMatcher.group(1));
            List<Product> cheapProducts = productRepository.findAll().stream()
                    .filter(p -> p.getPrice() != null && p.getPrice() <= maxPrice)
                    .limit(4)
                    .toList();
            if (!cheapProducts.isEmpty()) {
                recommendedProducts = cheapProducts;
                reply = String.format("Here are top items available under **₹%,.0f**:", maxPrice);
                suggestions = List.of("Show Electronics", "Show Books", "Shipping Info");
                return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
            }
        }

        // 6. Product Recommendations by Category or Keywords
        String matchedCategory = null;
        if (msg.contains("electronic") || msg.contains("laptop") || msg.contains("phone") || msg.contains("headphone") || msg.contains("monitor") || msg.contains("keyboard") || msg.contains("mouse")) {
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
        } else if (msg.contains("book") || msg.contains("read") || msg.contains("java") || msg.contains("python") || msg.contains("c programming") || msg.contains("algorithms")) {
            matchedCategory = "Books";
        }

        if (matchedCategory != null) {
            recommendedProducts = productRepository.findByCategoryIgnoreCase(matchedCategory);
            reply = String.format("Here are top recommendations from our **%s** collection:", matchedCategory);
            suggestions = List.of("Show another category", "Shipping details", "Help with checkout");
            return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
        }

        // 7. Generic keyword search (only for queries with at least 3 characters)
        if (msg.length() >= 3) {
            List<Product> searchResults = productRepository.searchProducts(msg);
            if (!searchResults.isEmpty()) {
                recommendedProducts = searchResults.stream().limit(4).toList();
                reply = String.format("I found %d matching items for '%s':", searchResults.size(), request != null ? request.getMessage() : msg);
                suggestions = List.of("Show All Items", "Filter by Category", "Track an Order");
                return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
            }
        }

        reply = "I couldn't find a direct match for that query, but IndiraHub carries a wide selection in Electronics, Books, Fashion, Home Decor, Fitness, Kitchen, Beauty, and Groceries. What can I explore for you?";
        suggestions = List.of("📱 Electronics", "📚 Books", "🏋️ Fitness", "🚚 Shipping Info");
        return ResponseEntity.ok(new ChatResponse(reply, suggestions, recommendedProducts));
    }
}

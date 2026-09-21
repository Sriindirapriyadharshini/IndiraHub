// Checkout.java
import java.util.Scanner;

public class Checkout {
    public static void place(Scanner in, User user, OrderData orders) {
        Cart cart = user.getCart();

        if (cart.isEmpty()) {
            System.out.println("Your bag is empty.");
            return;
        }

        AddToCart.showCart(cart);
        System.out.print("Place order? (yes/no): ");

        if (!in.nextLine().equalsIgnoreCase("yes")) return;

        for (OrderItem item : cart.getItems()) {
            if (!item.getProduct().reduceStock(item.getQuantity())) {
                System.out.println("Insufficient stock for " + item.getProduct().getName());
                return;
            }
        }

        Order order = new Order(user.getEmail(), cart.getItems(), cart.getTotal());
        orders.add(order);
        cart.clear();

        System.out.println("Luxury order placed successfully: " + order.getId());
    }
}

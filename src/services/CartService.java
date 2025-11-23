package services;

import models.CartItem;
import models.Product;
import java.util.*;

/**
 * CartService - manages shopping cart operations
 * Demonstrates session-based cart management
 */
public class CartService {
    private Map<String, List<CartItem>> userCarts; // userId -> cart items

    public CartService() {
        this.userCarts = new HashMap<>();
    }

    // Add item to cart
    public boolean addToCart(String userId, Product product, int quantity) {
        if (product == null) {
            System.out.println("Product not found!");
            return false;
        }

        if (!product.isAvailable() || product.getStock() < quantity) {
            System.out.println("Insufficient stock! Available: " + product.getStock());
            return false;
        }

        // Get or create cart for user
        List<CartItem> cart = userCarts.getOrDefault(userId, new ArrayList<>());

        // Check if product already in cart
        CartItem existingItem = findCartItem(cart, product.getProductId());
        if (existingItem != null) {
            // Update quantity
            int newQuantity = existingItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                System.out.println("Cannot add more. Stock limit: " + product.getStock());
                return false;
            }
            existingItem.setQuantity(newQuantity);
            System.out.println("Updated quantity in cart!");
        } else {
            // Add new item
            cart.add(new CartItem(product, quantity));
            System.out.println("Product added to cart!");
        }

        userCarts.put(userId, cart);
        return true;
    }

    // Remove item from cart
    public boolean removeFromCart(String userId, String productId) {
        List<CartItem> cart = userCarts.get(userId);
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return false;
        }

        CartItem item = findCartItem(cart, productId);
        if (item != null) {
            cart.remove(item);
            System.out.println("Item removed from cart!");
            return true;
        }

        System.out.println("Item not found in cart!");
        return false;
    }

    // Update quantity
    public boolean updateQuantity(String userId, String productId, int newQuantity) {
        List<CartItem> cart = userCarts.get(userId);
        if (cart == null) {
            System.out.println("Cart is empty!");
            return false;
        }

        CartItem item = findCartItem(cart, productId);
        if (item != null) {
            if (newQuantity <= 0) {
                cart.remove(item);
                System.out.println("Item removed from cart!");
            } else if (newQuantity <= item.getProduct().getStock()) {
                item.setQuantity(newQuantity);
                System.out.println("Quantity updated!");
            } else {
                System.out.println("Insufficient stock! Available: " + item.getProduct().getStock());
                return false;
            }
            return true;
        }

        System.out.println("Item not found in cart!");
        return false;
    }

    // Get cart items
    public List<CartItem> getCart(String userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    // Display cart
    public void displayCart(String userId) {
        List<CartItem> cart = getCart(userId);
        
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty!");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          YOUR SHOPPING CART");
        System.out.println("========================================");
        
        double total = 0;
        for (CartItem item : cart) {
            item.display();
            total += item.calculateSubtotal();
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("Total: $%.2f%n", total);
        System.out.println("========================================\n");
    }

    // Calculate total
    public double calculateTotal(String userId) {
        List<CartItem> cart = getCart(userId);
        double total = 0;
        for (CartItem item : cart) {
            total += item.calculateSubtotal();
        }
        return total;
    }

    // Clear cart
    public void clearCart(String userId) {
        userCarts.put(userId, new ArrayList<>());
        System.out.println("Cart cleared!");
    }

    // Check if cart is empty
    public boolean isEmpty(String userId) {
        List<CartItem> cart = getCart(userId);
        return cart == null || cart.isEmpty();
    }

    // Get item count
    public int getItemCount(String userId) {
        List<CartItem> cart = getCart(userId);
        return cart.size();
    }

    // Helper method to find cart item
    private CartItem findCartItem(List<CartItem> cart, String productId) {
        for (CartItem item : cart) {
            if (item.getProduct().getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    // Validate cart (check stock availability)
    public boolean validateCart(String userId) {
        List<CartItem> cart = getCart(userId);
        for (CartItem item : cart) {
            if (item.getQuantity() > item.getProduct().getStock()) {
                System.out.println("Error: Insufficient stock for " + item.getProduct().getName());
                return false;
            }
        }
        return true;
    }
}
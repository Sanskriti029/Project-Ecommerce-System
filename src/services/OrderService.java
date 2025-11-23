package services;

import models.*;
import utils.FileHandler;
import java.util.*;

/**
 * OrderService - handles order processing and management
 */
public class OrderService {
    private List<Order> orders;
    private FileHandler fileHandler;
    private static int orderCounter = 5000;

    public OrderService() {
        this.orders = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadOrders();
    }

    // Load orders from file
    private void loadOrders() {
        List<String> lines = fileHandler.readFile("data/orders.txt");
        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    Order order = new Order(parts[0], parts[1]);
                    order.setOrderDate(parts[2]);
                    order.setStatus(parts[3]);
                    
                    // Parse items
                    if (parts.length > 7 && !parts[7].isEmpty()) {
                        String[] itemsStr = parts[7].split(";");
                        for (String itemStr : itemsStr) {
                            OrderItem item = OrderItem.fromString(itemStr);
                            if (item != null) {
                                order.addItem(item);
                            }
                        }
                    }
                    
                    order.calculateTotal();
                    orders.add(order);
                }
            } catch (Exception e) {
                System.err.println("Error loading order: " + e.getMessage());
            }
        }
    }

    // Save orders to file
    private void saveOrders() {
        List<String> lines = new ArrayList<>();
        for (Order order : orders) {
            lines.add(order.toString());
        }
        fileHandler.writeFile("data/orders.txt", lines);
    }

    // Place order from cart
    public Order placeOrder(String customerId, List<CartItem> cartItems, 
                           ProductService productService) {
        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("Cart is empty! Cannot place order.");
            return null;
        }

        // Create new order
        String orderId = "ORD" + (++orderCounter);
        Order order = new Order(orderId, customerId);

        // Add items to order and update stock
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            
            // Check stock availability
            if (product.getStock() < cartItem.getQuantity()) {
                System.out.println("Error: Insufficient stock for " + product.getName());
                return null;
            }

            // Create order item
            OrderItem orderItem = new OrderItem(
                product.getProductId(),
                product.getName(),
                cartItem.getQuantity(),
                product.getPrice()
            );
            order.addItem(orderItem);

            // Update product stock
            productService.updateStock(product.getProductId(), -cartItem.getQuantity());
        }

        // Calculate totals
        order.calculateTotal();
        order.setStatus("CONFIRMED");

        // Save order
        orders.add(order);
        saveOrders();

        System.out.println("\n✓ Order placed successfully!");
        System.out.println("Order ID: " + orderId);
        System.out.printf("Total Amount: $%.2f%n", order.getTotalAmount());

        return order;
    }

    // Get order by ID
    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equalsIgnoreCase(orderId)) {
                return order;
            }
        }
        return null;
    }

    // Get orders by customer
    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    // Display customer orders
    public void displayCustomerOrders(String customerId) {
        List<Order> customerOrders = getOrdersByCustomer(customerId);
        
        if (customerOrders.isEmpty()) {
            System.out.println("\nNo orders found.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          YOUR ORDER HISTORY");
        System.out.println("========================================");
        System.out.printf("%-15s %-20s %-12s %-10s %s%n", 
                         "Order ID", "Date", "Total", "Status", "Items");
        System.out.println("----------------------------------------");
        
        for (Order order : customerOrders) {
            order.displayShort();
        }
        System.out.println("========================================\n");
    }

    // Display order details
    public void displayOrderDetails(String orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.displayOrder();
        } else {
            System.out.println("Order not found!");
        }
    }

    // Update order status (Admin only)
    public boolean updateOrderStatus(String orderId, String newStatus) {
        Order order = getOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return false;
        }

        order.setStatus(newStatus);
        saveOrders();
        System.out.println("Order status updated to: " + newStatus);
        return true;
    }

    // Get all orders (Admin)
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    // Display all orders (Admin)
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("\nNo orders found.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          ALL ORDERS");
        System.out.println("========================================");
        System.out.printf("%-15s %-15s %-20s %-12s %-10s%n", 
                         "Order ID", "Customer ID", "Date", "Total", "Status");
        System.out.println("----------------------------------------");
        
        for (Order order : orders) {
            System.out.printf("%-15s %-15s %-20s $%-11.2f %-10s%n",
                            order.getOrderId(), 
                            order.getCustomerId(),
                            order.getOrderDate(),
                            order.getTotalAmount(),
                            order.getStatus());
        }
        System.out.println("========================================\n");
    }

    // Cancel order
    public boolean cancelOrder(String orderId, String customerId, ProductService productService) {
        Order order = getOrderById(orderId);
        
        if (order == null) {
            System.out.println("Order not found!");
            return false;
        }

        if (!order.getCustomerId().equals(customerId)) {
            System.out.println("You can only cancel your own orders!");
            return false;
        }

        if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED")) {
            System.out.println("Cannot cancel this order!");
            return false;
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {
            productService.updateStock(item.getProductId(), item.getQuantity());
        }

        order.setStatus("CANCELLED");
        saveOrders();
        System.out.println("Order cancelled successfully!");
        return true;
    }
}
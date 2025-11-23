package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Order class - represents a customer order
 * Demonstrates Composition (Order HAS-A List of OrderItems)
 */
public class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private double subtotal;
    private double tax;
    private double totalAmount;
    private String orderDate;
    private String status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    // Constructor
    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Business methods
    public void calculateTotal() {
        subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getSubtotal();
        }
        tax = subtotal * 0.08; // 8% tax
        totalAmount = subtotal + tax;
    }

    public void displayOrder() {
        System.out.println("\n========================================");
        System.out.println("           ORDER DETAILS");
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("----------------------------------------");
        System.out.println("Items:");
        for (OrderItem item : items) {
            item.display();
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Tax (8%%): $%.2f%n", tax);
        System.out.printf("Total Amount: $%.2f%n", totalAmount);
        System.out.println("========================================\n");
    }

    public void displayShort() {
        System.out.printf("%-15s %-20s $%-10.2f %-10s Items: %d%n",
                          orderId, orderDate, totalAmount, status, items.size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(orderId).append(",")
          .append(customerId).append(",")
          .append(orderDate).append(",")
          .append(status).append(",")
          .append(subtotal).append(",")
          .append(tax).append(",")
          .append(totalAmount).append(",");
        
        // Add items
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i < items.size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }
}
package models;

/**
 * OrderItem class - represents a single item in an order
 */
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    // Constructor
    public OrderItem(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Business methods
    public double getSubtotal() {
        return price * quantity;
    }

    public void display() {
        System.out.printf("  %-10s %-25s Qty: %-3d Price: $%-8.2f Subtotal: $%-8.2f%n",
                          productId, productName, quantity, price, getSubtotal());
    }

    @Override
    public String toString() {
        return productId + ":" + productName + ":" + quantity + ":" + price;
    }

    // Parse from string
    public static OrderItem fromString(String data) {
        String[] parts = data.split(":");
        if (parts.length >= 4) {
            return new OrderItem(parts[0], parts[1], 
                               Integer.parseInt(parts[2]), 
                               Double.parseDouble(parts[3]));
        }
        return null;
    }
}
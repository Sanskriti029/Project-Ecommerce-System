package models;

/**
 * Product class - represents products in the e-commerce system
 * Demonstrates Encapsulation
 */
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;

    // Constructor
    public Product(String productId, String name, String description, double price, int stock, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Business methods
    public boolean isAvailable() {
        return stock > 0;
    }

    public boolean updateStock(int quantity) {
        if (stock + quantity >= 0) {
            stock += quantity;
            return true;
        }
        return false;
    }

    public void displayDetails() {
        System.out.println("\n========== Product Details ==========");
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Stock: " + stock);
        System.out.println("Category: " + category);
        System.out.println("Status: " + (isAvailable() ? "In Stock" : "Out of Stock"));
        System.out.println("====================================\n");
    }

    public void displayShort() {
        System.out.printf("%-10s %-30s $%-10.2f %-15s Stock: %d%n", 
                          productId, name, price, category, stock);
    }

    @Override
    public String toString() {
        return productId + "," + name + "," + description + "," + price + "," + stock + "," + category;
    }

    // Parse from CSV string
    public static Product fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 6) {
            return new Product(
                parts[0],
                parts[1],
                parts[2],
                Double.parseDouble(parts[3]),
                Integer.parseInt(parts[4]),
                parts[5]
            );
        }
        return null;
    }
}
package models;

/**
 * CartItem class - represents an item in shopping cart
 * Demonstrates Composition (Cart HAS-A CartItem)
 */
public class CartItem {
    private Product product;
    private int quantity;

    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Business methods
    public double calculateSubtotal() {
        return product.getPrice() * quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity - amount >= 0) {
            this.quantity -= amount;
        }
    }

    public void display() {
        System.out.printf("%-30s Qty: %-3d Price: $%-8.2f Subtotal: $%-8.2f%n",
                          product.getName(), quantity, product.getPrice(), calculateSubtotal());
    }

    @Override
    public String toString() {
        return product.getProductId() + "," + quantity;
    }
}
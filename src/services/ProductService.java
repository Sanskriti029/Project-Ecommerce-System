package services;

import models.Product;
import utils.FileHandler;
import java.util.*;

/**
 * ProductService - handles all product-related operations
 */
public class ProductService {
    private List<Product> products;
    private FileHandler fileHandler;
    private static int productCounter = 1000;

    public ProductService() {
        this.products = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadProducts();
    }

    // Load products from file
    private void loadProducts() {
        List<String> lines = fileHandler.readFile("data/products.txt");
        for (String line : lines) {
            Product product = Product.fromString(line);
            if (product != null) {
                products.add(product);
            }
        }

        // Add sample products if none exist
        if (products.isEmpty()) {
            addSampleProducts();
        }
    }

    // Save products to file
    private void saveProducts() {
        List<String> lines = new ArrayList<>();
        for (Product product : products) {
            lines.add(product.toString());
        }
        fileHandler.writeFile("data/products.txt", lines);
    }

    // Add sample products
    private void addSampleProducts() {
        products.add(new Product("P1001", "Laptop", "High-performance laptop", 899.99, 10, "Electronics"));
        products.add(new Product("P1002", "Smartphone", "Latest model smartphone", 599.99, 25, "Electronics"));
        products.add(new Product("P1003", "Headphones", "Wireless noise-canceling", 149.99, 50, "Electronics"));
        products.add(new Product("P1004", "Book - Java Programming", "Complete Java guide", 39.99, 100, "Books"));
        products.add(new Product("P1005", "Coffee Maker", "Automatic coffee machine", 79.99, 30, "Home"));
        products.add(new Product("P1006", "Desk Chair", "Ergonomic office chair", 199.99, 15, "Furniture"));
        products.add(new Product("P1007", "Water Bottle", "Stainless steel, 1L", 24.99, 200, "Sports"));
        products.add(new Product("P1008", "Backpack", "Laptop backpack", 49.99, 40, "Accessories"));
        productCounter = 1008;
        saveProducts();
    }

    // Add new product (Admin only)
    public boolean addProduct(String name, String description, double price, 
                             int stock, String category) {
        String productId = "P" + (++productCounter);
        Product product = new Product(productId, name, description, price, stock, category);
        products.add(product);
        saveProducts();
        System.out.println("Product added successfully! Product ID: " + productId);
        return true;
    }

    // Update product (Admin only)
    public boolean updateProduct(String productId, String name, String description, 
                                double price, int stock, String category) {
        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return false;
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        saveProducts();
        System.out.println("Product updated successfully!");
        return true;
    }

    // Delete product (Admin only)
    public boolean deleteProduct(String productId) {
        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return false;
        }

        products.remove(product);
        saveProducts();
        System.out.println("Product deleted successfully!");
        return true;
    }

    // Get product by ID
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        return null;
    }

    // Display all products
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          ALL PRODUCTS");
        System.out.println("========================================");
        System.out.printf("%-10s %-30s %-12s %-15s %s%n", 
                         "ID", "Name", "Price", "Category", "Stock");
        System.out.println("----------------------------------------");
        
        for (Product product : products) {
            product.displayShort();
        }
        System.out.println("========================================\n");
    }

    // Search products by name
    public List<Product> searchByName(String keyword) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }

    // Filter by category
    public List<Product> filterByCategory(String category) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                results.add(product);
            }
        }
        return results;
    }

    // Filter by price range
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        return results;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Get available products only
    public List<Product> getAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product product : products) {
            if (product.isAvailable()) {
                available.add(product);
            }
        }
        return available;
    }

    // Update stock
    public boolean updateStock(String productId, int quantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.updateStock(quantity);
            saveProducts();
            return true;
        }
        return false;
    }

    // Get all categories
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.getCategory());
        }
        return categories;
    }
}
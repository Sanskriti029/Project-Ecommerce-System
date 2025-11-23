import models.*;
import services.*;
import utils.*;
import java.util.*;

/**
 * Main class - Entry point for E-Commerce Shopping System
 * Demonstrates complete OOP application with menu-driven interface
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    private static OrderService orderService = new OrderService();

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Welcome to E-Commerce Shopping       ║");
        System.out.println("║          System                        ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        mainMenu();
        
        scanner.close();
    }

    // Main Menu
    private static void mainMenu() {
        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("===============================");
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("\nThank you for using E-Commerce System!");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Register User
    private static void registerUser() {
        System.out.println("\n========== USER REGISTRATION ==========");
        InputValidator.displayRegistrationRules();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (!InputValidator.isValidName(name)) return;

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (!InputValidator.isValidPassword(password)) return;

        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine();
        if (!InputValidator.isValidPhone(phone)) {
            System.out.println("Invalid phone number! Must be 10 digits.");
            return;
        }

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        userService.registerCustomer(name, email, password, phone, address);
    }

    // Login User
    private static void loginUser() {
        System.out.println("\n========== LOGIN ==========");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = userService.login(email, password);
        
        if (user != null) {
            if (user instanceof Customer) {
                customerMenu((Customer) user);
            } else if (user instanceof Admin) {
                adminMenu((Admin) user);
            }
        }
    }

    // Customer Menu
    private static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n========== CUSTOMER MENU ==========");
            System.out.println("1. Browse All Products");
            System.out.println("2. Search Products");
            System.out.println("3. Filter by Category");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. View Order History");
            System.out.println("7. View Profile");
            System.out.println("8. Logout");
            System.out.println("===================================");
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    browseProducts(customer);
                    break;
                case 2:
                    searchProducts(customer);
                    break;
                case 3:
                    filterByCategory(customer);
                    break;
                case 4:
                    viewCart(customer);
                    break;
                case 5:
                    checkout(customer);
                    break;
                case 6:
                    viewOrderHistory(customer);
                    break;
                case 7:
                    customer.displayInfo();
                    break;
                case 8:
                    userService.logout();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Browse Products
    private static void browseProducts(Customer customer) {
        productService.displayAllProducts();
        
        System.out.print("Enter Product ID to add to cart (or 0 to go back): ");
        String productId = scanner.nextLine();
        
        if (productId.equals("0")) return;

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        product.displayDetails();

        System.out.print("Enter quantity: ");
        int quantity = getIntInput();

        if (InputValidator.isValidPurchaseQuantity(quantity)) {
            cartService.addToCart(customer.getUserId(), product, quantity);
        }
    }

    // Search Products
    private static void searchProducts(Customer customer) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();

        List<Product> results = productService.searchByName(keyword);

        if (results.isEmpty()) {
            System.out.println("No products found!");
            return;
        }

        System.out.println("\n========== SEARCH RESULTS ==========");
        for (Product product : results) {
            product.displayShort();
        }

        System.out.print("\nEnter Product ID to add to cart (or 0 to go back): ");
        String productId = scanner.nextLine();
        
        if (productId.equals("0")) return;

        Product product = productService.getProductById(productId);
        if (product != null) {
            System.out.print("Enter quantity: ");
            int quantity = getIntInput();
            cartService.addToCart(customer.getUserId(), product, quantity);
        }
    }

    // Filter by Category
    private static void filterByCategory(Customer customer) {
        Set<String> categories = productService.getAllCategories();
        
        System.out.println("\n========== CATEGORIES ==========");
        int i = 1;
        for (String category : categories) {
            System.out.println(i++ + ". " + category);
        }

        System.out.print("Enter category name: ");
        String category = scanner.nextLine();

        List<Product> results = productService.filterByCategory(category);

        if (results.isEmpty()) {
            System.out.println("No products found in this category!");
            return;
        }

        System.out.println("\n========== PRODUCTS IN " + category.toUpperCase() + " ==========");
        for (Product product : results) {
            product.displayShort();
        }

        System.out.print("\nEnter Product ID to add to cart (or 0 to go back): ");
        String productId = scanner.nextLine();
        
        if (productId.equals("0")) return;

        Product product = productService.getProductById(productId);
        if (product != null) {
            System.out.print("Enter quantity: ");
            int quantity = getIntInput();
            cartService.addToCart(customer.getUserId(), product, quantity);
        }
    }

    // View Cart
    private static void viewCart(Customer customer) {
        cartService.displayCart(customer.getUserId());

        if (cartService.isEmpty(customer.getUserId())) {
            return;
        }

        System.out.println("\n1. Update Quantity");
        System.out.println("2. Remove Item");
        System.out.println("3. Clear Cart");
        System.out.println("4. Back to Menu");
        System.out.print("Enter choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                System.out.print("Enter new quantity: ");
                int quantity = getIntInput();
                cartService.updateQuantity(customer.getUserId(), productId, quantity);
                break;
            case 2:
                System.out.print("Enter Product ID to remove: ");
                productId = scanner.nextLine();
                cartService.removeFromCart(customer.getUserId(), productId);
                break;
            case 3:
                System.out.print("Are you sure? (yes/no): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    cartService.clearCart(customer.getUserId());
                }
                break;
            case 4:
                return;
        }
    }

    // Checkout
    private static void checkout(Customer customer) {
        if (cartService.isEmpty(customer.getUserId())) {
            System.out.println("\nYour cart is empty! Add products first.");
            return;
        }

        cartService.displayCart(customer.getUserId());

        System.out.print("\nProceed with checkout? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            List<CartItem> cartItems = cartService.getCart(customer.getUserId());
            Order order = orderService.placeOrder(customer.getUserId(), cartItems, productService);
            
            if (order != null) {
                cartService.clearCart(customer.getUserId());
                order.displayOrder();
                customer.addOrderToHistory(order.getOrderId());
            }
        } else {
            System.out.println("Checkout cancelled.");
        }
    }

    // View Order History
    private static void viewOrderHistory(Customer customer) {
        orderService.displayCustomerOrders(customer.getUserId());

        System.out.print("Enter Order ID to view details (or 0 to go back): ");
        String orderId = scanner.nextLine();

        if (!orderId.equals("0")) {
            orderService.displayOrderDetails(orderId);
        }
    }

    // Admin Menu
    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. View All Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. View All Orders");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Profile");
            System.out.println("8. Logout");
            System.out.println("================================");
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    productService.displayAllProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    orderService.displayAllOrders();
                    break;
                case 6:
                    updateOrderStatus();
                    break;
                case 7:
                    admin.displayInfo();
                    break;
                case 8:
                    userService.logout();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Add Product (Admin)
    private static void addProduct() {
        System.out.println("\n========== ADD PRODUCT ==========");
        
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = getDoubleInput();

        System.out.print("Enter Stock: ");
        int stock = getIntInput();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        if (InputValidator.isValidPrice(price) && InputValidator.isValidQuantity(stock)) {
            productService.addProduct(name, description, price, stock, category);
        }
    }

    // Update Product (Admin)
    private static void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        product.displayDetails();

        System.out.print("Enter new Name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) name = product.getName();

        System.out.print("Enter new Description (or press Enter to keep current): ");
        String description = scanner.nextLine();
        if (description.isEmpty()) description = product.getDescription();

        System.out.print("Enter new Price (or 0 to keep current): ");
        double price = getDoubleInput();
        if (price == 0) price = product.getPrice();

        System.out.print("Enter new Stock (or -1 to keep current): ");
        int stock = getIntInput();
        if (stock == -1) stock = product.getStock();

        System.out.print("Enter new Category (or press Enter to keep current): ");
        String category = scanner.nextLine();
        if (category.isEmpty()) category = product.getCategory();

        productService.updateProduct(productId, name, description, price, stock, category);
    }

    // Delete Product (Admin)
    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        String productId = scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            productService.deleteProduct(productId);
        }
    }

    // Update Order Status (Admin)
    private static void updateOrderStatus() {
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();

        System.out.println("\nAvailable statuses:");
        System.out.println("1. PENDING");
        System.out.println("2. CONFIRMED");
        System.out.println("3. SHIPPED");
        System.out.println("4. DELIVERED");
        System.out.println("5. CANCELLED");
        
        System.out.print("Enter new status: ");
        String status = scanner.nextLine().toUpperCase();

        orderService.updateOrderStatus(orderId, status);
    }

    // Helper method to get integer input
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }

    // Helper method to get double input
    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }
}
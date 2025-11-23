package services;

import models.*;
import utils.FileHandler;
import java.util.*;

/**
 * UserService - handles all user-related operations
 * Demonstrates Service Layer pattern
 */
public class UserService {
    private List<User> users;
    private User currentUser;
    private FileHandler fileHandler;
    private static int userCounter = 1000;

    public UserService() {
        this.users = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadUsers();
    }

    // Load users from file
    private void loadUsers() {
        List<String> lines = fileHandler.readFile("data/users.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                String userType = parts[6];
                if (userType.equals("CUSTOMER")) {
                    users.add(Customer.fromString(line));
                } else if (userType.equals("ADMIN")) {
                    users.add(Admin.fromString(line));
                }
            }
        }
        
        // Create default admin if no users exist
        if (users.isEmpty()) {
            Admin defaultAdmin = new Admin("A1000", "Admin", "admin@shop.com", 
                                          "admin123", "0000000000", "SUPER");
            users.add(defaultAdmin);
            saveUsers();
        }
    }

    // Save users to file
    private void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.toString());
        }
        fileHandler.writeFile("data/users.txt", lines);
    }

    // Register new customer
    public boolean registerCustomer(String name, String email, String password, 
                                    String phone, String address) {
        // Check if email already exists
        if (getUserByEmail(email) != null) {
            System.out.println("Error: Email already registered!");
            return false;
        }

        String userId = "C" + (++userCounter);
        Customer customer = new Customer(userId, name, email, password, phone, address);
        users.add(customer);
        saveUsers();
        System.out.println("Registration successful! Your Customer ID: " + userId);
        return true;
    }

    // Register new admin (only by existing admin)
    public boolean registerAdmin(String name, String email, String password, 
                                String phone, String adminLevel) {
        if (currentUser == null || !currentUser.getUserType().equals("ADMIN")) {
            System.out.println("Error: Only admins can register new admins!");
            return false;
        }

        if (getUserByEmail(email) != null) {
            System.out.println("Error: Email already registered!");
            return false;
        }

        String userId = "A" + (++userCounter);
        Admin admin = new Admin(userId, name, email, password, phone, adminLevel);
        users.add(admin);
        saveUsers();
        System.out.println("Admin registration successful! Admin ID: " + userId);
        return true;
    }

    // Login
    public User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && user.login(email, password)) {
            currentUser = user;
            System.out.println("\n✓ Login successful! Welcome, " + user.getName());
            return user;
        }
        System.out.println("\n✗ Login failed! Invalid email or password.");
        return null;
    }

    // Logout
    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            System.out.println("Logged out successfully!");
            currentUser = null;
        }
    }

    // Get user by email
    private User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    // Get current user
    public User getCurrentUser() {
        return currentUser;
    }

    // Display current user info
    public void displayCurrentUserInfo() {
        if (currentUser != null) {
            currentUser.displayInfo();
        } else {
            System.out.println("No user logged in.");
        }
    }

    // Update profile
    public boolean updateProfile(String name, String phone, String address) {
        if (currentUser == null) {
            System.out.println("Please login first!");
            return false;
        }

        currentUser.setName(name);
        currentUser.setPhone(phone);
        
        if (currentUser instanceof Customer) {
            ((Customer) currentUser).setAddress(address);
        }

        saveUsers();
        System.out.println("Profile updated successfully!");
        return true;
    }

    // Get all customers (for admin)
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Customer) {
                customers.add((Customer) user);
            }
        }
        return customers;
    }
}
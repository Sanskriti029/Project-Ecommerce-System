package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer class - demonstrates Inheritance
 * Extends User class
 */
public class Customer extends User {
    private String address;
    private List<String> orderHistory; // Stores order IDs

    // Constructor
    public Customer(String userId, String name, String email, String password, String phone, String address) {
        super(userId, name, email, password, phone);
        this.address = address;
        this.orderHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(String orderId) {
        this.orderHistory.add(orderId);
    }

    // Implementation of abstract method (Polymorphism)
    @Override
    public void displayInfo() {
        System.out.println("\n========== Customer Information ==========");
        System.out.println("Customer ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Address: " + address);
        System.out.println("Total Orders: " + orderHistory.size());
        System.out.println("=========================================\n");
    }

    @Override
    public String getUserType() {
        return "CUSTOMER";
    }

    @Override
    public String toString() {
        return super.toString() + "," + address + ",CUSTOMER";
    }

    // Parse from CSV string
    public static Customer fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 6) {
            return new Customer(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
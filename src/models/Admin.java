package models;

/**
 * Admin class - demonstrates Inheritance
 * Extends User class
 */
public class Admin extends User {
    private String adminLevel; // e.g., "SUPER", "MANAGER"

    // Constructor
    public Admin(String userId, String name, String email, String password, String phone, String adminLevel) {
        super(userId, name, email, password, phone);
        this.adminLevel = adminLevel;
    }

    // Getters and Setters
    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    // Implementation of abstract method (Polymorphism)
    @Override
    public void displayInfo() {
        System.out.println("\n========== Admin Information ==========");
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Admin Level: " + adminLevel);
        System.out.println("======================================\n");
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }

    @Override
    public String toString() {
        return super.toString() + "," + adminLevel + ",ADMIN";
    }

    // Parse from CSV string
    public static Admin fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 6) {
            return new Admin(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
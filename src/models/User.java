package models;

/**
 * Abstract User class - demonstrates Abstraction and Encapsulation
 * Parent class for Customer and Admin
 */
public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean isLoggedIn;

    // Constructor
    public User(String userId, String name, String email, String password, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isLoggedIn = false;
    }

    // Getters and Setters (Encapsulation)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    // Common methods
    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    // Abstract method - must be implemented by child classes (Polymorphism)
    public abstract void displayInfo();

    // Method to get user type
    public abstract String getUserType();

    @Override
    public String toString() {
        return userId + "," + name + "," + email + "," + password + "," + phone;
    }
}
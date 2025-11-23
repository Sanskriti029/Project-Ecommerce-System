package utils;

import java.util.regex.Pattern;

/**
 * InputValidator - utility class for validating user inputs
 * Demonstrates input validation and security practices
 */
public class InputValidator {

    // Email validation regex
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Phone validation regex (10 digits)
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate password
     * Requirements: Minimum 6 characters
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters long!");
            return false;
        }
        return true;
    }

    /**
     * Validate phone number
     * Requirements: Exactly 10 digits
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validate name
     * Requirements: Not empty, only letters and spaces
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty!");
            return false;
        }
        if (name.length() < 2) {
            System.out.println("Name must be at least 2 characters!");
            return false;
        }
        if (!name.matches("[a-zA-Z ]+")) {
            System.out.println("Name can only contain letters and spaces!");
            return false;
        }
        return true;
    }

    /**
     * Validate price
     * Requirements: Positive number
     */
    public static boolean isValidPrice(double price) {
        if (price <= 0) {
            System.out.println("Price must be greater than 0!");
            return false;
        }
        return true;
    }

    /**
     * Validate quantity/stock
     * Requirements: Non-negative integer
     */
    public static boolean isValidQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("Quantity cannot be negative!");
            return false;
        }
        return true;
    }

    /**
     * Validate positive quantity (for purchases)
     */
    public static boolean isValidPurchaseQuantity(int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0!");
            return false;
        }
        return true;
    }

    /**
     * Validate string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate integer input
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
            return false;
        }
    }

    /**
     * Validate double input
     */
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
            return false;
        }
    }

    /**
     * Sanitize string input (remove extra spaces)
     */
    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Display validation rules for registration
     */
    public static void displayRegistrationRules() {
        System.out.println("\n=== Registration Requirements ===");
        System.out.println("• Name: At least 2 characters, letters only");
        System.out.println("• Email: Valid email format (e.g., user@example.com)");
        System.out.println("• Password: Minimum 6 characters");
        System.out.println("• Phone: Exactly 10 digits");
        System.out.println("================================\n");
    }
}
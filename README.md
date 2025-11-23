# E-Commerce Shopping System

A console-based e-commerce shopping application built using Java, demonstrating core Object-Oriented Programming principles including inheritance, polymorphism, encapsulation, and abstraction.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [OOP Concepts Demonstrated](#oop-concepts-demonstrated)
- [Screenshots](#screenshots)

## 🎯 Overview

The E-Commerce Shopping System is a Java-based application that simulates an online shopping platform. It allows customers to browse products, manage shopping carts, place orders, and track order history. Administrators can manage products, view all orders, and update order statuses.

## ✨ Features

### Customer Features:
- ✅ User registration and login
- ✅ Browse all products
- ✅ Search products by name
- ✅ Filter products by category
- ✅ Add/remove items from shopping cart
- ✅ Update cart quantities
- ✅ Checkout and place orders
- ✅ View order history
- ✅ View order details

### Admin Features:
- ✅ Login to admin panel
- ✅ Add new products
- ✅ Update product details
- ✅ Delete products
- ✅ View all orders
- ✅ Update order status
- ✅ View all products inventory

### System Features:
- ✅ Data persistence using text files
- ✅ Input validation
- ✅ Error handling
- ✅ Stock management
- ✅ Automatic tax calculation
- ✅ Order total calculation

## 🛠️ Technologies Used

- **Language:** Java (JDK 8 or higher)
- **IDE:** Eclipse / IntelliJ IDEA / VS Code
- **Data Storage:** Text files (CSV format)
- **Collections:** ArrayList, HashMap
- **Design Pattern:** Service Layer Pattern

## 📁 Project Structure

```
ECommerceSystem/
│
├── src/
│   ├── models/
│   │   ├── User.java              # Abstract user class
│   │   ├── Customer.java          # Customer entity
│   │   ├── Admin.java             # Admin entity
│   │   ├── Product.java           # Product entity
│   │   ├── CartItem.java          # Cart item entity
│   │   ├── Order.java             # Order entity
│   │   └── OrderItem.java         # Order item entity
│   │
│   ├── services/
│   │   ├── UserService.java       # User management
│   │   ├── ProductService.java    # Product management
│   │   ├── CartService.java       # Cart operations
│   │   └── OrderService.java      # Order processing
│   │
│   ├── utils/
│   │   ├── FileHandler.java       # File operations
│   │   └── InputValidator.java    # Input validation
│   │
│   └── Main.java                  # Application entry point
│
├── data/                          # Data storage (created automatically)
│   ├── users.txt
│   ├── products.txt
│   └── orders.txt
│
├── README.md
└── statement.md
```

## 🚀 Installation & Setup

### Prerequisites:
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, VS Code) or Command Line

### Steps:

1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/ecommerce-system.git
cd ecommerce-system
```

2. **Create the project structure:**
   - Create folders: `src/models`, `src/services`, `src/utils`
   - Copy all Java files to their respective folders
   - The `data` folder will be created automatically on first run

3. **Compile the project:**
```bash
javac -d bin src/*.java src/models/*.java src/services/*.java src/utils/*.java
```

4. **Run the application:**
```bash
java -cp bin Main
```

### Using an IDE:
1. Open your IDE
2. Create a new Java project
3. Copy all source files maintaining the package structure
4. Run `Main.java`

## 💻 How to Run

### Option 1: Command Line
```bash
# Navigate to src directory
cd src

# Compile all files
javac Main.java models/*.java services/*.java utils/*.java

# Run the application
java Main
```

### Option 2: Using IDE
1. Open the project in your IDE
2. Locate `Main.java`
3. Right-click and select "Run"

## 📖 Usage Guide

### First Time Setup:
1. Run the application
2. **Default Admin Account:**
   - Email: `admin@shop.com`
   - Password: `admin123`

### For Customers:
1. **Register:** Create a new account
2. **Login:** Use your email and password
3. **Browse Products:** View all available products
4. **Search:** Find specific products
5. **Add to Cart:** Select products and quantities
6. **Checkout:** Complete your order
7. **View Orders:** Track your order history

### For Admins:
1. **Login** with admin credentials
2. **Manage Products:**
   - Add new products
   - Update existing products
   - Delete products
3. **Manage Orders:**
   - View all customer orders
   - Update order status

### Example Workflow:
```
Customer Journey:
Register → Login → Browse Products → Add to Cart → Checkout → View Order

Admin Journey:
Login → Add Products → View Orders → Update Status
```

## 🎓 OOP Concepts Demonstrated

### 1. **Encapsulation**
- Private fields with public getters/setters
- Data hiding in all model classes
- Example: `User`, `Product`, `Order` classes

### 2. **Inheritance**
- `Customer` and `Admin` extend abstract `User` class
- Code reusability and hierarchical relationships

### 3. **Polymorphism**
- Method overriding: `displayInfo()`, `getUserType()`
- Runtime polymorphism through abstract methods

### 4. **Abstraction**
- Abstract `User` class with abstract methods
- Service layer abstraction

### 5. **Composition**
- `Order` HAS-A List<OrderItem>
- `CartItem` HAS-A Product

### 6. **Association**
- Customer places Order
- Product belongs to Category

## 📊 Key Classes & Responsibilities

| Class | Responsibility |
|-------|----------------|
| `User` | Abstract base class for all users |
| `Customer` | Customer-specific data and operations |
| `Admin` | Admin-specific data and operations |
| `Product` | Product information and stock management |
| `Order` | Order details and calculations |
| `UserService` | User authentication and management |
| `ProductService` | Product CRUD operations |
| `CartService` | Shopping cart management |
| `OrderService` | Order processing and history |
| `FileHandler` | File I/O operations |
| `InputValidator` | Input validation and security |

## 🧪 Testing

### Manual Testing:
1. **User Registration:** Test with valid/invalid inputs
2. **Login:** Test authentication
3. **Product Management:** Add, update, delete products
4. **Cart Operations:** Add, remove, update quantities
5. **Order Placement:** Complete checkout process
6. **Data Persistence:** Restart app and verify data

### Test Cases:
- Empty cart checkout (should fail)
- Invalid email format (should reject)
- Insufficient stock (should prevent order)
- Admin-only operations (should enforce access control)

## 📸 Screenshots

*(Add screenshots of your running application here)*

1. Main Menu
2. Product Listing
3. Shopping Cart
4. Order Confirmation
5. Admin Dashboard

## 🔒 Security Features

- Password validation (minimum 6 characters)
- Email format validation
- Phone number validation
- Role-based access control
- Input sanitization

## 🐛 Known Issues & Limitations

- Console-based interface (no GUI)
- Single-session operation (no concurrent users)
- Basic text file storage (no database)
- No payment gateway integration
- No actual product images

## 🚀 Future Enhancements

- [ ] Add GUI using JavaFX or Swing
- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Multiple payment methods
- [ ] Product reviews and ratings
- [ ] Wishlist feature
- [ ] Order tracking with real-time updates
- [ ] Email notifications
- [ ] Product recommendation system
- [ ] Discount/coupon system
- [ ] Invoice generation (PDF)

## 👨‍💻 Author

**Your Name**
- GitHub: [Sansriti029](https://github.com/Sanskriti029)
- Email: sanskritikhandelwal029@gmail.com

## 📄 License

This project is created for educational purposes as part of VITyarthi coursework.

## 🙏 Acknowledgments

- VITyarthi - Build Your Own Project Guidelines
- Java Documentation
- Object-Oriented Programming Principles

---

**Note:** This project is for educational purposes and demonstrates OOP concepts. It's not intended for production use.

## 📞 Support

For issues or questions:
1. Check the documentation
2. Review the code comments
3. Create an issue on GitHub
4. Contact the author

---

**Happy Shopping! 🛒**

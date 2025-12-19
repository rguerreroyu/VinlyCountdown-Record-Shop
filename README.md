# Vinyl Countdown - E-Commerce Capstone Project

## Project Description

**Vinyl Countdown** is a Java + Spring Boot e-commerce application that allows users to browse, purchase, and manage vinyl records online. Users can view product details, add items to their shopping cart, and manage their cart in real time.  

The application demonstrates full-stack development concepts including:

- **Backend:** Java, Spring Boot, JDBC, MySQL  
- **Frontend:** Optional (React, Thymeleaf, or other)  
- **Security:** Spring Security for authentication  
- **Database:** MySQL database with tables for users, products, categories, and shopping cart  

This project was designed as a capstone to showcase database integration, REST API design, and CRUD operations.

---

## Application Screenshots

### Categories
<img width="412" height="262" alt="image" src="https://github.com/user-attachments/assets/8d9b0db6-c2c0-4f3a-bc2b-74f70ac8100b" />


### Price Filter
<img width="1910" height="805" alt="image" src="https://github.com/user-attachments/assets/540d4d5f-0e2e-41e9-a568-822857e43dee" />


### Shopping Cart
<img width="1022" height="547" alt="image" src="https://github.com/user-attachments/assets/577d9b7a-c417-49a4-a9d1-9f2a74edd901" />


---

## Interesting Piece of Code

One interesting part of the project is the **ShoppingCartDao implementation**, which ensures that users can view the items in their cart
@Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT product_id, quantity FROM shopping_cart WHERE user_id = ?";

        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next()) {
                int product_id = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                Product product = productDao.getById(product_id);

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);

                cart.add(item);
            }
            {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

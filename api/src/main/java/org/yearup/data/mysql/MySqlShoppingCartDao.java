package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.data.ProductDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.yearup.data.mysql.MySqlProductDao.mapRow;
@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao=productDao;
    }

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
}

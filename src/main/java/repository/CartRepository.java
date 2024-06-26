package repository;

import model.Cart;
import services.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private Connection connection;

    public CartRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    public Cart getCartByUserIdAndProductId(int userId, int productId) throws SQLException {
        String query = "SELECT * FROM Cart WHERE UserId = ? AND ProductId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, productId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Cart cart = new Cart();
            cart.setCartId(resultSet.getInt("CartId"));
            cart.setUserId(resultSet.getInt("UserId"));
            cart.setProductId(resultSet.getInt("ProductId"));
            cart.setQuantity(resultSet.getInt("Quantity"));

            cart.setPrice(getProductPriceById(cart.getProductId()));
            String productName = getProductNameById(cart.getProductId());
            cart.setProductName(productName);
            return cart;
        }

        return null;
    }

    public void updateCart(Cart cart) throws SQLException {
        String query = "UPDATE Cart SET Quantity = ? WHERE CartId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cart.getQuantity());
        statement.setInt(2, cart.getCartId());
        statement.executeUpdate();
    }

    public void addCart(Cart cart) throws SQLException {
        String query = "INSERT INTO Cart (UserId, ProductId, Quantity) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cart.getUserId());
        statement.setInt(2, cart.getProductId());
        statement.setInt(3, cart.getQuantity());
        statement.executeUpdate();
    }

    public int getTotalPriceByUserId(int userId) throws SQLException {
        String query = "SELECT SUM(p.Price * c.Quantity) AS TotalPrice " +
                "FROM Cart c JOIN Products p ON c.ProductId = p.ProductId " +
                "WHERE c.UserId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("TotalPrice");
        }

        return 0;
    }

    public String getProductNameById(int productId) throws SQLException {
        String query = "SELECT ProductName FROM Products WHERE ProductId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("ProductName");
        }

        return null;
    }
    public int getProductPriceById(int productId) throws SQLException {
        String query = "SELECT Price FROM Products WHERE ProductId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("Price");
        }

        return 0; // Or handle this case as needed
    }
    public List<Cart> getCartItemsByUserId(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT * FROM Cart WHERE UserId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Cart cart = new Cart();
            cart.setCartId(resultSet.getInt("CartId"));
            cart.setUserId(resultSet.getInt("UserId"));
            cart.setProductId(resultSet.getInt("ProductId"));
            cart.setQuantity(resultSet.getInt("Quantity"));
            cart.setPrice(resultSet.getInt("TotalPrice"));

            cart.setPrice(getProductPriceById(cart.getProductId()));
            String productName = getProductNameById(cart.getProductId());
            cart.setProductName(productName);

            cartItems.add(cart);
        }

        return cartItems;
    }

    public void clearCartByUserId(int userId) throws SQLException {
        String query = "DELETE FROM Cart WHERE UserId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    public void removeCartByUserIdAndProductId(int userId, int productId) throws SQLException {
        String query = "DELETE FROM Cart WHERE UserId = ? AND ProductId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, productId);
        statement.executeUpdate();
    }

    public void decrementProductQuantity(int productId, int quantity) throws SQLException {
        String query = "UPDATE Products SET Quantity = Quantity - ? WHERE ProductId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quantity);
        statement.setInt(2, productId);
        statement.executeUpdate();
    }
}
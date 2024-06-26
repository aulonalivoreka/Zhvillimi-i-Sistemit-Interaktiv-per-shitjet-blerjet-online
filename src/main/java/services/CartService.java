package services;

import model.Cart;
import repository.CartRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    private CartRepository cartRepository;

    public CartService() throws SQLException {
        this.cartRepository = new CartRepository();
    }

    public Cart getCartByUserIdAndProductId(int userId, int productId) throws SQLException {
        return cartRepository.getCartByUserIdAndProductId(userId, productId);
    }

    public void updateCart(Cart cart) throws SQLException {
        cartRepository.updateCart(cart);
    }

    public void addCart(Cart cart) throws SQLException {
        cartRepository.addCart(cart);
    }

    public int getTotalPriceByUserId(int userId) throws SQLException {
        return cartRepository.getTotalPriceByUserId(userId);
    }

    public List<Cart> getCartItemsByUserId(int userId) throws SQLException {
        return cartRepository.getCartItemsByUserId(userId);
    }

    public void clearCartByUserId(int userId) throws SQLException {
        cartRepository.clearCartByUserId(userId);
    }

    public void removeCartByUserIdAndProductId(int userId, int productId) throws SQLException {
        cartRepository.removeCartByUserIdAndProductId(userId, productId);
    }

    public void decrementProductQuantities(List<Cart> cartItems) throws SQLException {
        for (Cart cartItem : cartItems) {
            cartRepository.decrementProductQuantity(cartItem.getProductId(), cartItem.getQuantity());
        }
    }
}
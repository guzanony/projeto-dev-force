package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.domain.Cart.Cart;
import br.com.DevForce.Coffe.on.domain.Cart.CartItem;
import br.com.DevForce.Coffe.on.domain.Cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(cartItem.getUserId());
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(cartItem.getUserId());
        }
        cart.addItem(cartItem);
        cartRepository.save(cart);
        return cart;
    }
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

}

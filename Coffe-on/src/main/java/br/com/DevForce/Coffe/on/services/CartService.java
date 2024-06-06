package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.domain.Cart.Cart;
import br.com.DevForce.Coffe.on.domain.Cart.CartItem;
import br.com.DevForce.Coffe.on.domain.Cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(CartItem cartItem) throws Exception {
        logger.info("Adding item to cart for user: {}", cartItem.getNomeCompleto());
        Optional<Cart> optionalCart = cartRepository.findByNomeCompleto(cartItem.getNomeCompleto());
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart(cartItem.getNomeCompleto());
        }
        cart.addItem(cartItem);
        cartRepository.save(cart);
        logger.info("Item added to cart successfully for user: {}", cartItem.getNomeCompleto());
        return cart;
    }

    public Cart getCartByNomeCompleto(String nomeCompleto) throws Exception {
        logger.info("Fetching cart for user: {}", nomeCompleto);
        Optional<Cart> optionalCart = cartRepository.findByNomeCompleto(nomeCompleto);
        if (optionalCart.isPresent()) {
            logger.info("Cart found for user: {}", nomeCompleto);
            return optionalCart.get();
        } else {
            logger.warn("Cart not found for user: {}", nomeCompleto);
            throw new Exception("Cart not found for user: " + nomeCompleto);
        }
    }
}

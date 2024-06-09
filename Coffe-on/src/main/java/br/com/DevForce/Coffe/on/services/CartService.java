package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.domain.Cart.Cart;
import br.com.DevForce.Coffe.on.domain.Cart.CartItem;
import br.com.DevForce.Coffe.on.domain.Cart.CartRepository;
import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.Product.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductsRepository productRepository;

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCart(Long id) {
        return cartRepository.findById(id);
    }

    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        cart.addItem(item);
        return cartRepository.save(cart);
    }

    public int getItemCount(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        cartRepository.save(cart);
    }

    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(itemId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        cartRepository.save(cart);
    }
}
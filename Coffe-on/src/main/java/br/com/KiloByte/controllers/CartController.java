package br.com.KiloByte.controllers;

import br.com.KiloByte.domain.Cart.Cart;
import br.com.KiloByte.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public Cart createCart() {
        return cartService.createCart();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }

    @PostMapping("/{cartId}/items")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestBody AddItemRequest request) {
        return cartService.addItemToCart(cartId, request.getProductId(), request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public void removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItemFromCart(cartId, itemId);
    }

    @GetMapping("/{cartId}/count")
    public int getItemCount(@PathVariable Long cartId) {
        return cartService.getItemCount(cartId);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public void updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestBody UpdateItemQuantityRequest request) {
        cartService.updateItemQuantity(cartId, itemId, request.getQuantity());
    }

    public static class AddItemRequest {
        private Long productId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class UpdateItemQuantityRequest {
        private int quantity;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

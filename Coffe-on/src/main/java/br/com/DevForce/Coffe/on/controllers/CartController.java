package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Cart.Cart;
import br.com.DevForce.Coffe.on.domain.Cart.CartItem;
import br.com.DevForce.Coffe.on.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartItem cartItem) {
        System.out.println("Received add to cart request: " + cartItem);
        Cart updatedCart = cartService.addToCart(cartItem);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String userId) {
        System.out.println("Received get cart request for userId: " + userId);
        Cart cart = cartService.getCartByUserId(userId);
        System.out.println("Returning cart: " + cart);
        return ResponseEntity.ok(cart);
    }
}

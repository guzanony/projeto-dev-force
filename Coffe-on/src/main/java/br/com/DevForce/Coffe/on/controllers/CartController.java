package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Cart.Cart;
import br.com.DevForce.Coffe.on.domain.Cart.CartItem;
import br.com.DevForce.Coffe.on.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItem cartItem) {
        logger.info("Received request to add to cart: {}", cartItem);
        if (cartItem.getQuantity() <= 0) {
            logger.error("Quantity must be greater than zero.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be greater than zero.");
        }
        try {
            Cart updatedCart = cartService.addToCart(cartItem);
            logger.info("Item added to cart successfully: {}", updatedCart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            logger.error("Error adding item to cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding item to cart: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam String nomeCompleto) {
        logger.info("Received request to get cart for user: {}", nomeCompleto);
        try {
            Cart cart = cartService.getCartByNomeCompleto(nomeCompleto);
            if (cart == null) {
                logger.warn("Cart not found for user: {}", nomeCompleto);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user: " + nomeCompleto);
            }
            logger.info("Cart retrieved successfully for user: {}", nomeCompleto);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            logger.error("Error retrieving cart for user: {}", nomeCompleto, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving cart: " + e.getMessage());
        }
    }
}

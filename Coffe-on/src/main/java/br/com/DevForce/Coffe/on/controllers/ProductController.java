package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.Product.RequestProducts;
import br.com.DevForce.Coffe.on.domain.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;
    private ProductsRepository productsRepository;

    @Autowired
    public ProductController(ProductsService productService) {
        this.productsService = productService;
    }

    @GetMapping
    public List<Product> listarProdutos() {
        return productsService.listarProdutos();
    }

    @PostMapping
    public ResponseEntity<Product> adicionarProduto(@RequestBody @Valid RequestProducts data) {
        Product newProduct = productsService.registerNewProduct(data);
        return ResponseEntity.ok(newProduct);
    }



    @GetMapping("/productsRepository/{id}")
    public ResponseEntity<Product> getUserById(@PathVariable Long id) {
        Optional<Product> user = productsRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        boolean activated = productsService.activateUser(id);
        if (activated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para desativar produto
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        boolean deactivated = productsService.deactivateUser(id);
        if (deactivated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
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

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;

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

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activeProduct(@PathVariable Long id) {
        boolean activated = productsService.activeProduct(id);
        if (activated) {
            return  ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivade")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id) {
        boolean deactivated = productsService.deactivateProduct(id);
        if (deactivated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
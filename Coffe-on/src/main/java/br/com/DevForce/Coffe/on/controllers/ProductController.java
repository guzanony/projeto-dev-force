package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.Product.Product;
import br.com.DevForce.Coffe.on.domain.user.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.domain.user.Product.RequestProducts;
import br.com.DevForce.Coffe.on.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}


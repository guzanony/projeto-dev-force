package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.Product.RequestProducts;
import br.com.DevForce.Coffe.on.domain.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> adicionarProduto(@ModelAttribute RequestProducts requestProducts) {
        try {
            Product newProduct = productsService.registerNewProduct(requestProducts);
            if (requestProducts.getImage() != null && !requestProducts.getImage().isEmpty()) {
                newProduct.setImage(requestProducts.getImage().getBytes());
            }
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Produto adicionado com sucesso!");
            response.put("productId", newProduct.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar produto");
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        try {
            Product product = productsService.getProductById(id);
            byte[] imageBytes = product.getImage();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
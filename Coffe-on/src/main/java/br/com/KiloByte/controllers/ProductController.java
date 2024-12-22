package br.com.KiloByte.controllers;

import br.com.KiloByte.domain.Product.Product;
import br.com.KiloByte.domain.Product.ProductResponseDTO;
import br.com.KiloByte.domain.Product.RequestProducts;
import br.com.KiloByte.domain.Product.ProductsRepository;
import br.com.KiloByte.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;
    private final ProductsRepository productsRepository;


    @Autowired
    public ProductController(ProductsService productService, ProductsRepository productsRepository) {
        this.productsService = productService;
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productsRepository.findAll();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductResponseDTO> productsDTO = products.stream().map(this::convertToDTO).toList();

        return ResponseEntity.ok(productsDTO);
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setNome(product.getNome());
        dto.setAvaliacao(product.getAvaliacao());
        dto.setQuantidade(product.getQuantidade());
        dto.setPreco(product.getPreco());
        dto.setDescricao(product.getDescricao());
        dto.setActive(product.isActive());

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<String> imagesBase64 = product.getImages().stream()
                    .map(image -> Base64.getEncoder().encodeToString(image.getImageData()))
                    .toList();
            dto.setImages(imagesBase64);
        }

        return dto;
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

            if (product.getImages() != null && !product.getImages().isEmpty()) {
                byte[] imageBytes = product.getImages().get(0).getImageData();

                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProducts(
            @PathVariable Long id,
            @RequestParam("nome") String nome,
            @RequestParam("avaliacao") Double avaliacao,
            @RequestParam("quantidade") int quantidade,
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("descricao") String descricao,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Produto não encontrado com o ID: " + id));
        product.setNome(nome);
        product.setAvaliacao(avaliacao);
        product.setQuantidade(quantidade);
        product.setPreco(preco);
        product.setDescricao(descricao);
        // Se uma nova imagem for fornecida, processa e salva
        if (image != null && !image.isEmpty()) {
            // Implementar lógica para armazenar a imagem
        if (image != null && !image.isEmpty()) {
        }
        Product updatedProduct = productsRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Produto não encontrado com o ID: " + id));
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Produto não encontrado com o ID: " + id));

        ProductResponseDTO productDTO = convertToDTO(product);

        return ResponseEntity.ok(productDTO);
    }

}
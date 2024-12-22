package br.com.KiloByte.services;

import br.com.KiloByte.domain.Product.Product;
import br.com.KiloByte.domain.Product.ProductsRepository;
import br.com.KiloByte.domain.Product.RequestProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.KiloByte.domain.Product.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
       this.productsRepository = productsRepository;
    }

    public List<Product> listarProdutos() {
        return productsRepository.findAll();
    }

    public Product registerNewProduct(RequestProducts requestProducts) throws IOException {
        Product newProduct = new Product(requestProducts);
        newProduct.setNome(requestProducts.getNome());
        newProduct.setAvaliacao(requestProducts.getAvaliacao());
        newProduct.setQuantidade(requestProducts.getQuantidade());
        newProduct.setPreco(requestProducts.getPreco());
        newProduct.setDescricao(requestProducts.getDescricao());
        newProduct.setActive(true);

        if (requestProducts.getImages() != null && !requestProducts.getImages().isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile imageFile : requestProducts.getImages()) {
                byte[] imageBytes = imageFile.getBytes();
                Image image = new Image(imageBytes, newProduct);
                images.add(image);
            }
            newProduct.setImages(images);
        }

        return productsRepository.save(newProduct);
    }


    public boolean activeProduct(Long id) {
        return productsRepository.findById(id)
                .map(products -> {
                    products.setActive(true);
                    productsRepository.save(products);
                    return true;
                }).orElse(false);

    }

    public boolean deactivateProduct(Long id) {
        return productsRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productsRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public Product getProductById(Long id) {
        return productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }
}

package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.domain.user.Product.Product;
import br.com.DevForce.Coffe.on.domain.user.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.domain.user.Product.RequestProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Product registerNewProduct(RequestProducts requestProducts) {
        Product newProduct = new Product(requestProducts);
        return productsRepository.save(newProduct);
    }

}

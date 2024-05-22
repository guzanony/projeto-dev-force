package br.com.DevForce.Coffe.on.domain.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
@Table(name = "products")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double avaliacao;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Product (RequestProducts requestProducts) {
        this.nome = requestProducts.nome();
        this.avaliacao = requestProducts.avaliacao();
        this.quantidade = requestProducts.quantidade();
        this.preco = requestProducts.preco();
        this.descricao = requestProducts.descricao();
        this.active = requestProducts.active() != null ? requestProducts.active() : true;
    }


    public void setActive(boolean active) {
        this.active = active;
    }
}

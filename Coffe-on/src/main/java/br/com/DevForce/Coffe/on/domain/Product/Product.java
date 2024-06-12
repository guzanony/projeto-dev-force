package br.com.DevForce.Coffe.on.domain.Product;

import br.com.DevForce.Coffe.on.domain.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double avaliacao;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ManyToMany(mappedBy = "produtos")
    @JsonBackReference
    private List<Pedido> pedidos;

    public Product(RequestProducts requestProducts) {
        this.nome = requestProducts.getNome();
        this.avaliacao = requestProducts.getAvaliacao();
        this.quantidade = requestProducts.getQuantidade();
        this.preco = requestProducts.getPreco();
        this.descricao = requestProducts.getDescricao();
        this.active = requestProducts.getActive();
    }



    public void setActive(boolean active) {
        this.active = active;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isActive() {
        return active;
    }

    public Long getId() {
        return id;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

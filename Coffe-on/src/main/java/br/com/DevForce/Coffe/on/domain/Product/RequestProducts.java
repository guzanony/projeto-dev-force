package br.com.DevForce.Coffe.on.domain.Product;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class RequestProducts {
    private String nome;
    private Double avaliacao;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;
    private Boolean active;
    private MultipartFile image;

    public RequestProducts(String nome, Double avaliacao, Integer quantidade, BigDecimal preco, String descricao, Boolean active, MultipartFile image) {
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
        this.active = active;
        this.image = image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getActive() {
        return active != null ? active.booleanValue() : false;
    }
}

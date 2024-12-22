package br.com.KiloByte.domain.Product;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class RequestProducts {
    private String nome;
    private Double avaliacao;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;
    private Boolean active;
    private List<MultipartFile> images;


    public RequestProducts(String nome, Double avaliacao, Integer quantidade, BigDecimal preco, String descricao, Boolean active, List<MultipartFile> images) {
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
        this.active = active;
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }
    public void setImages(List<MultipartFile> images) {
        this.images = images;
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

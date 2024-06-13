package br.com.DevForce.Coffe.on.dto;

public record ProductQuantidadeDTO(Long produtoId, int quantidade) {
    public ProductQuantidadeDTO() {
        this(null, 0);
    }
}

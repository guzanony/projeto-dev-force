package br.com.KiloByte.dto;

public record ProductQuantidadeDTO(Long produtoId, int quantidade) {
    public ProductQuantidadeDTO() {
        this(null, 0);
    }
}

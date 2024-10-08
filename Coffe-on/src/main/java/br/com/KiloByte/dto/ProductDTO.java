package br.com.KiloByte.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Integer quantidade
) {}

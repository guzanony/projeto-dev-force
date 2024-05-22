package br.com.DevForce.Coffe.on.domain.Product;

import java.math.BigDecimal;

public record RequestProducts(String nome, Double avaliacao, Integer quantidade, BigDecimal preco, String descricao, Boolean active) {
}

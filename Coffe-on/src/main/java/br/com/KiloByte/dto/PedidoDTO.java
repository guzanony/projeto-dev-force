package br.com.KiloByte.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        Long id,
        Long clienteId,
        List<ProductQuantidadeDTO> produtos,
        EnderecoEntregaDTO enderecoEntrega,
        BigDecimal valorFrete,
        String formaPagamento,
        String status,
        Long numeroPedido,
        LocalDateTime dataCriacao,
        BigDecimal valorTotal
) {}

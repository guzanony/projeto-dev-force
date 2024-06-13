package br.com.DevForce.Coffe.on.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetailsDTO(
        Long id,
        Long clienteId,
        List<ProductDTO> produtos,
        EnderecoEntregaDTO enderecoEntrega,
        BigDecimal valorFrete,
        String formaPagamento,
        String status,
        Long numeroPedido,
        LocalDateTime dataCriacao,
        BigDecimal valorTotal
) {}

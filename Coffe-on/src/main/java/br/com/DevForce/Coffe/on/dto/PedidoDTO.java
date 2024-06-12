package br.com.DevForce.Coffe.on.dto;

import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntrega;
import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
        Long id,
        Long clienteId,
        List<Long> produtos,
        EnderecoEntregaDTO enderecoEntrega,
        BigDecimal valorFrete,
        String formaPagamento,
        String status,
        Long numeroPedido
) {}


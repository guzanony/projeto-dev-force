package br.com.DevForce.Coffe.on.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    @Query("SELECT COALESCE(MAX(p.numeroPedido), 0) FROM Pedido p")
    Long findMaxNumeroPedido();
}

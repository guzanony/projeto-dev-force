package br.com.DevForce.Coffe.on.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoEntregaRepository extends JpaRepository<EnderecoEntrega, Long> {
    List<EnderecoEntrega> findByClienteId(Long ClienteId);
}

package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.domain.cliente.Cliente;
import br.com.DevForce.Coffe.on.domain.cliente.ClienteRepository;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntrega;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntregaRepository;
import br.com.DevForce.Coffe.on.domain.pedido.Pedido;
import br.com.DevForce.Coffe.on.domain.pedido.PedidoRepository;
import br.com.DevForce.Coffe.on.dto.EnderecoEntregaDTO;
import br.com.DevForce.Coffe.on.dto.PedidoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class OrderController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoEntregaRepository enderecoEntregaRepository;
    private final ProductsRepository productsRepository;

    public OrderController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, EnderecoEntregaRepository enderecoEntregaRepository, ProductsRepository productsRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoEntregaRepository = enderecoEntregaRepository;
        this.productsRepository = productsRepository;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        List<Product> produtos = pedidoDTO.produtos().stream()
                .map(produtoId -> productsRepository.findById(produtoId)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + produtoId)))
                .collect(Collectors.toList());
        pedido.setProdutos(produtos);

        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        enderecoEntrega.setCep(pedidoDTO.enderecoEntrega().cep());
        enderecoEntrega.setLogradouro(pedidoDTO.enderecoEntrega().logradouro());
        enderecoEntrega.setNumero(pedidoDTO.enderecoEntrega().numero());
        enderecoEntrega.setComplemento(pedidoDTO.enderecoEntrega().complemento());
        enderecoEntrega.setBairro(pedidoDTO.enderecoEntrega().bairro());
        enderecoEntrega.setCidade(pedidoDTO.enderecoEntrega().cidade());
        enderecoEntrega.setUf(pedidoDTO.enderecoEntrega().uf());
        enderecoEntrega.setCliente(cliente);
        enderecoEntrega = enderecoEntregaRepository.save(enderecoEntrega);

        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setValorFrete(pedidoDTO.valorFrete());
        pedido.setFormaPagamento(pedidoDTO.formaPagamento());
        pedido.setStatus("aguardando pagamento");

        Long maxNumeroPedido = pedidoRepository.findMaxNumeroPedido();
        pedido.setNumeroPedido(maxNumeroPedido != null ? maxNumeroPedido + 1 : 1L);

        Pedido savedPedido = pedidoRepository.save(pedido);

        PedidoDTO responseDTO = new PedidoDTO(
                savedPedido.getId(),
                savedPedido.getCliente().getId(),
                savedPedido.getProdutos().stream().map(Product::getId).collect(Collectors.toList()),
                new EnderecoEntregaDTO(
                        savedPedido.getEnderecoEntrega().getCep(),
                        savedPedido.getEnderecoEntrega().getLogradouro(),
                        savedPedido.getEnderecoEntrega().getNumero(),
                        savedPedido.getEnderecoEntrega().getComplemento(),
                        savedPedido.getEnderecoEntrega().getBairro(),
                        savedPedido.getEnderecoEntrega().getCidade(),
                        savedPedido.getEnderecoEntrega().getUf()
                ),
                savedPedido.getValorFrete(),
                savedPedido.getFormaPagamento(),
                savedPedido.getStatus(),
                savedPedido.getNumeroPedido()
        );

        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            return ResponseEntity.ok(new PedidoDTO(
                    p.getId(),
                    p.getCliente().getId(),
                    p.getProdutos().stream().map(Product::getId).collect(Collectors.toList()),
                    new EnderecoEntregaDTO(
                            p.getEnderecoEntrega().getCep(),
                            p.getEnderecoEntrega().getLogradouro(),
                            p.getEnderecoEntrega().getNumero(),
                            p.getEnderecoEntrega().getComplemento(),
                            p.getEnderecoEntrega().getBairro(),
                            p.getEnderecoEntrega().getCidade(),
                            p.getEnderecoEntrega().getUf()
                    ),
                    p.getValorFrete(),
                    p.getFormaPagamento(),
                    p.getStatus(),
                    p.getNumeroPedido()
            ));
        }
        return ResponseEntity.notFound().build();
    }
}


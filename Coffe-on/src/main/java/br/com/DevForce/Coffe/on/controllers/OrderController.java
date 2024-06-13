package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.Product.Product;
import br.com.DevForce.Coffe.on.domain.Product.ProductsRepository;
import br.com.DevForce.Coffe.on.domain.cliente.Cliente;
import br.com.DevForce.Coffe.on.domain.cliente.ClienteRepository;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntrega;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntregaRepository;
import br.com.DevForce.Coffe.on.domain.pedido.Pedido;
import br.com.DevForce.Coffe.on.domain.pedido.PedidoRepository;
import br.com.DevForce.Coffe.on.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
                .map(productQuantidadeDTO -> {
                    Product produto = productsRepository.findById(productQuantidadeDTO.produtoId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + productQuantidadeDTO.produtoId()));
                    produto.setQuantidade(productQuantidadeDTO.quantidade());
                    return produto;
                }).collect(Collectors.toList());
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

        BigDecimal valorTotal = produtos.stream()
                .map(produto -> produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(valorTotal);

        Pedido savedPedido = pedidoRepository.save(pedido);

        PedidoDTO responseDTO = new PedidoDTO(
                savedPedido.getId(),
                savedPedido.getCliente().getId(),
                savedPedido.getProdutos().stream().map(produto -> new ProductQuantidadeDTO(produto.getId(), produto.getQuantidade())).collect(Collectors.toList()),
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
                savedPedido.getNumeroPedido(),
                savedPedido.getDataCriacao(),
                savedPedido.getValorTotal()
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
                    p.getProdutos().stream().map(produto -> new ProductQuantidadeDTO(produto.getId(), produto.getQuantidade())).collect(Collectors.toList()),
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
                    p.getNumeroPedido(),
                    p.getDataCriacao(),
                    p.getValorTotal()
            ));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> getOrdersByClienteId(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        List<PedidoDTO> pedidoDTOs = pedidos.stream().map(pedido -> new PedidoDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getProdutos().stream().map(produto -> new ProductQuantidadeDTO(produto.getId(), produto.getQuantidade())).collect(Collectors.toList()),
                new EnderecoEntregaDTO(
                        pedido.getEnderecoEntrega().getCep(),
                        pedido.getEnderecoEntrega().getLogradouro(),
                        pedido.getEnderecoEntrega().getNumero(),
                        pedido.getEnderecoEntrega().getComplemento(),
                        pedido.getEnderecoEntrega().getBairro(),
                        pedido.getEnderecoEntrega().getCidade(),
                        pedido.getEnderecoEntrega().getUf()
                ),
                pedido.getValorFrete(),
                pedido.getFormaPagamento(),
                pedido.getStatus(),
                pedido.getNumeroPedido(),
                pedido.getDataCriacao(),
                pedido.getValorTotal()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(pedidoDTOs);
    }

    @GetMapping("/{pedidoId}/detalhes")
    public ResponseEntity<PedidoDetailsDTO> getPedidoDetalhes(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        List<ProductDTO> productDTOs = pedido.getProdutos().stream()
                .map(product -> new ProductDTO(product.getId(), product.getNome(), product.getPreco(), product.getQuantidade()))
                .collect(Collectors.toList());

        PedidoDetailsDTO pedidoDetailsDTO = new PedidoDetailsDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                productDTOs,
                new EnderecoEntregaDTO(
                        pedido.getEnderecoEntrega().getCep(),
                        pedido.getEnderecoEntrega().getLogradouro(),
                        pedido.getEnderecoEntrega().getNumero(),
                        pedido.getEnderecoEntrega().getComplemento(),
                        pedido.getEnderecoEntrega().getBairro(),
                        pedido.getEnderecoEntrega().getCidade(),
                        pedido.getEnderecoEntrega().getUf()
                ),
                pedido.getValorFrete(),
                pedido.getFormaPagamento(),
                pedido.getStatus(),
                pedido.getNumeroPedido(),
                pedido.getDataCriacao(),
                pedido.getValorTotal()
        );

        return ResponseEntity.ok(pedidoDetailsDTO);
    }
}


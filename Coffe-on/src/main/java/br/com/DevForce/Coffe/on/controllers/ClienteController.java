package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.cliente.Cliente;
import br.com.DevForce.Coffe.on.domain.cliente.ClienteRepository;
import br.com.DevForce.Coffe.on.domain.cliente.EnderecoEntrega;
import br.com.DevForce.Coffe.on.domain.user.userAdmin.UserAdmin;
import br.com.DevForce.Coffe.on.domain.user.userAdmin.UserAdminRepository;
import br.com.DevForce.Coffe.on.dto.*;
import br.com.DevForce.Coffe.on.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class ClienteController {
    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ClienteController(ClienteRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }
    @PostMapping("/loginCliente")
    public ResponseEntity loginCliente(@RequestBody LoginClienteDTO body) {
        Cliente cliente = this.repository.findByEmail(body.email()).orElseThrow(()-> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), cliente.getPassword())) {
            String token = this.tokenService.generateToken(cliente);
            return ResponseEntity.ok(new ResponseDTO(cliente.getEmail(), token)); //O que meu front end espera
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registerCliente")
    public ResponseEntity<?> registerCliente(@RequestBody RegisterClienteDTO body) {
        if (repository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já registrado!");
        }

        Cliente newCliente = new Cliente();
        newCliente.setEmail(body.email());
        newCliente.setPassword(passwordEncoder.encode(body.password()));
        newCliente.setNomeCompleto(body.nomeCompleto());
        newCliente.setDataNascimento(body.dataNascimento());
        newCliente.setGenero(body.genero());
        newCliente.setCpf(body.cpf());

        newCliente.setCepFaturamento(body.cepFaturamento());
        newCliente.setLogradouroFaturamento(body.logradouroFaturamento());
        newCliente.setNumeroFaturamento(body.numeroFaturamento());
        newCliente.setComplementoFaturamento(body.complementoFaturamento());
        newCliente.setBairroFaturamento(body.bairroFaturamento());
        newCliente.setCidadeFaturamento(body.cidadeFaturamento());
        newCliente.setUfFaturamento(body.ufFaturamento());

        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        enderecoEntrega.setCep(body.cep());
        enderecoEntrega.setLogradouro(body.logradouro());
        enderecoEntrega.setNumero(body.numero());
        enderecoEntrega.setComplemento(body.complemento());
        enderecoEntrega.setBairro(body.bairro());
        enderecoEntrega.setCidade(body.cidade());
        enderecoEntrega.setUf(body.uf());
        enderecoEntrega.setCliente(newCliente);

        newCliente.setEnderecosEntrega(List.of(enderecoEntrega));

        repository.save(newCliente);

        String token = tokenService.generateToken(newCliente);
        return ResponseEntity.ok(new ResponseDTO(newCliente.getNomeCompleto(), token));
    }

    @PostMapping("/validateTokenCliente")
    public ResponseEntity validateTokenCliente(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = tokenService.validateToken(token);
            if (username != null) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/cliente/me")
    public ResponseEntity<Cliente> getMyInfo(Principal principal) {
        String username = principal.getName();
        Optional<Cliente> cliente = repository.findByEmail(username);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

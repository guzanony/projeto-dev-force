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
import java.util.stream.Collectors;

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
            return ResponseEntity.ok(new ResponseDTO(cliente.getEmail(), cliente.getNomeCompleto(), token));
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

        List<EnderecoEntrega> enderecosEntrega = body.enderecosEntrega().stream().map(enderecoDTO -> {
            EnderecoEntrega endereco = new EnderecoEntrega();
            endereco.setCep(enderecoDTO.cep());
            endereco.setLogradouro(enderecoDTO.logradouro());
            endereco.setNumero(enderecoDTO.numero());
            endereco.setComplemento(enderecoDTO.complemento());
            endereco.setBairro(enderecoDTO.bairro());
            endereco.setCidade(enderecoDTO.cidade());
            endereco.setUf(enderecoDTO.uf());
            endereco.setCliente(newCliente);
            return endereco;
        }).collect(Collectors.toList());

        newCliente.setEnderecosEntrega(enderecosEntrega);

        repository.save(newCliente);

        String token = tokenService.generateToken(newCliente);
        return ResponseEntity.ok(new ResponseDTO(newCliente.getEmail(), newCliente.getNomeCompleto(), token));
    }

    @PostMapping("/validateTokenCliente")
    public ResponseEntity<?> validateTokenCliente(HttpServletRequest request) {
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
    public ResponseEntity<RegisterClienteDTO> getMyInfo(Principal principal) {
        String username = principal.getName();
        Optional<Cliente> cliente = repository.findByEmail(username);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            List<EnderecoEntregaDTO> enderecosEntrega = c.getEnderecosEntrega().stream()
                    .map(endereco -> new EnderecoEntregaDTO(
                            endereco.getCep(),
                            endereco.getLogradouro(),
                            endereco.getNumero(),
                            endereco.getComplemento(),
                            endereco.getBairro(),
                            endereco.getCidade(),
                            endereco.getUf()
                    )).collect(Collectors.toList());

            RegisterClienteDTO clienteDTO = new RegisterClienteDTO(
                    c.getNomeCompleto(),
                    c.getDataNascimento(),
                    c.getGenero(),
                    c.getCepFaturamento(),
                    c.getLogradouroFaturamento(),
                    c.getNumeroFaturamento(),
                    c.getComplementoFaturamento(),
                    c.getBairroFaturamento(),
                    c.getCidadeFaturamento(),
                    c.getUfFaturamento(),
                    c.getPassword(),
                    c.getEmail(),
                    c.getCpf(),
                    enderecosEntrega
            );

            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/cliente/me")
    public ResponseEntity<?> updateInfoCliente(Principal principal, @RequestBody RegisterClienteDTO body) {
        String username = principal.getName();
        Optional<Cliente> optionalCliente = repository.findByEmail(username);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNomeCompleto(body.nomeCompleto());
            cliente.setDataNascimento(body.dataNascimento());
            cliente.setGenero(body.genero());
            cliente.setCpf(body.cpf());

            cliente.setCepFaturamento(body.cepFaturamento());
            cliente.setLogradouroFaturamento(body.logradouroFaturamento());
            cliente.setNumeroFaturamento(body.numeroFaturamento());
            cliente.setComplementoFaturamento(body.complementoFaturamento());
            cliente.setBairroFaturamento(body.bairroFaturamento());
            cliente.setCidadeFaturamento(body.cidadeFaturamento());
            cliente.setUfFaturamento(body.ufFaturamento());

            cliente.setEnderecosEntrega(body.enderecosEntrega().stream().map(enderecoDTO -> {
                EnderecoEntrega endereco = new EnderecoEntrega();
                endereco.setCep(enderecoDTO.cep());
                endereco.setLogradouro(enderecoDTO.logradouro());
                endereco.setNumero(enderecoDTO.numero());
                endereco.setComplemento(enderecoDTO.complemento());
                endereco.setBairro(enderecoDTO.bairro());
                endereco.setCidade(enderecoDTO.cidade());
                endereco.setUf(enderecoDTO.uf());
                endereco.setCliente(cliente);
                return endereco;
            }).collect(Collectors.toList()));

            repository.save(cliente);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.cliente.Cliente;
import br.com.DevForce.Coffe.on.domain.cliente.ClienteRepository;
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
    public ResponseEntity registerCliente(@RequestBody RegisterClienteDTO body){
        Optional<Cliente> cliente = this.repository.findByEmail(body.email());

        if(cliente.isEmpty()) {
            Cliente newCliente = new Cliente();
            newCliente.setPassword(passwordEncoder.encode(body.password()));
            newCliente.setEmail(body.email());
            newCliente.setNomeCompleto(body.nomeCompleto());
            newCliente.setDataNascimento(body.dataNascimento());
            newCliente.setGenero(body.genero());
            newCliente.setCepFaturamento(body.cepFaturamento());
            newCliente.setLogradouroFaturamento(body.logradouroFaturamento());
            newCliente.setNumeroFaturamento(body.numeroFaturamento());
            newCliente.setComplementoFaturamento(body.complementoFaturamento());
            newCliente.setBairroFaturamento(body.bairroFaturamento());
            newCliente.setCidadeFaturamento(body.cidadeFaturamento());
            newCliente.setUfFaturamento(body.ufFaturamento());
            newCliente.setCpf(body.cpf());
            this.repository.save(newCliente);

            String token = this.tokenService.generateToken(newCliente);
            return ResponseEntity.ok(new ResponseDTO(newCliente.getNomeCompleto(), token));
        }
        return ResponseEntity.badRequest().build();
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

}

package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.userAdmin.UserAdmin;
import br.com.DevForce.Coffe.on.domain.user.userAdmin.UserAdminRepository;
import br.com.DevForce.Coffe.on.dto.LoginRequestDTO;
import br.com.DevForce.Coffe.on.dto.RegisterRequestDTO;
import br.com.DevForce.Coffe.on.dto.ResponseDTO;
import br.com.DevForce.Coffe.on.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class UserAdminController {
    private final UserAdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserAdminController(UserAdminRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }
    @PostMapping("/login")
    public ResponseEntity loginUserAdmin(@RequestBody LoginRequestDTO body) {
        UserAdmin userAdmin = this.repository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), userAdmin.getPassword())) {
            return ResponseEntity.ok(new ResponseDTO(userAdmin.getUsername(), userAdmin.getName(), userAdmin.getRole(), userAdmin.getClienteId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<UserAdmin> user = this.repository.findByUsername(body.getEmail());

        if (user.isEmpty()) {
            UserAdmin newUserAdmin = new UserAdmin();
            newUserAdmin.setPassword(passwordEncoder.encode(body.getPassword()));
            newUserAdmin.setUsername(body.getEmail());
            newUserAdmin.setName(body.getName());
            newUserAdmin.setCpf(body.getCpf());
            newUserAdmin.setActive(body.isActive());
            if ("admin".equals(body.getGrupo())) {
                newUserAdmin.setRole("ROLE_ADMIN");
            } else {
                newUserAdmin.setRole("ROLE_ESTOQUISTA");
            }            this.repository.save(newUserAdmin);

            String token = this.tokenService.generateToken(newUserAdmin);
            return ResponseEntity.ok(new ResponseDTO(newUserAdmin.getUsername(), newUserAdmin.getName(), newUserAdmin.getRole(), newUserAdmin.getClienteId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/users/admin")
    public ResponseEntity<List<UserAdmin>> listAdmins() {
        List<UserAdmin> admins = repository.findByRole("ROLE_ADMIN");
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/users/estoquista")
    public ResponseEntity<List<UserAdmin>> listEstoquistas() {
        List<UserAdmin> estoquistas = repository.findByRole("ROLE_ESTOQUISTA");
        return ResponseEntity.ok(estoquistas);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserAdmin>> listUsers() {
        List<UserAdmin> users = repository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user")
    public ResponseEntity<UserAdmin> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = tokenService.validateToken(token);
            if (username != null) {
                UserAdmin userAdmin = repository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                return ResponseEntity.ok(userAdmin);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }





    @PostMapping("/validateToken")
    public ResponseEntity validateToken(HttpServletRequest request) {
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

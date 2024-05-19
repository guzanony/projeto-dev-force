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
        UserAdmin userAdmin = this.repository.findByUsername(body.username()).orElseThrow(()-> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), userAdmin.getPassword())) {
            String token = this.tokenService.generateToken(userAdmin);
            return ResponseEntity.ok(new ResponseDTO(userAdmin.getUsername(), userAdmin.getId(), token)); //O que meu front end espera
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<UserAdmin> user = this.repository.findByUsername(body.username());

        if(user.isEmpty()) {
            UserAdmin newUserAdmin = new UserAdmin();
            newUserAdmin.setPassword(passwordEncoder.encode(body.password()));
            newUserAdmin.setUsername(body.username());
            newUserAdmin.setName(body.name());
            this.repository.save(newUserAdmin);

            String token = this.tokenService.generateToken(newUserAdmin);
            return ResponseEntity.ok(new ResponseDTO(newUserAdmin.getName(), newUserAdmin.getId(), token));
        }
        return ResponseEntity.badRequest().build();
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

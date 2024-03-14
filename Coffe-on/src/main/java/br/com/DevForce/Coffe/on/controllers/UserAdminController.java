package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.AuthResponse;
import br.com.DevForce.Coffe.on.domain.user.RequestUserAdmin;
import br.com.DevForce.Coffe.on.domain.user.UserAdmin;
import br.com.DevForce.Coffe.on.domain.user.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/usersAdmin")
public class UserAdminController {
@Autowired
private UserAdminRepository repository;
@CrossOrigin(origins = "http://localhost:63342")
@GetMapping
public ResponseEntity getAllUsers() {
 var allUserAdmin = repository.findAll();
return ResponseEntity.ok(allUserAdmin);
 }
    @PostMapping
    public ResponseEntity<?> login(@RequestBody RequestUserAdmin loginRequest) {
        UserAdmin usuario = repository.findByUsername(loginRequest.getUsername());
        if (usuario != null && usuario.getPassword().equals(loginRequest.getPassword())) {
            String token = gerarToken(usuario);
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String gerarToken(UserAdmin userAdmin) {
        String chaveSecreta = "chave-secreta";

        String token = Jwts.builder()
                .setSubject(userAdmin.getUsername())
                .signWith(SignatureAlgorithm.HS512, chaveSecreta)
                .compact();

        return token;
    }

}

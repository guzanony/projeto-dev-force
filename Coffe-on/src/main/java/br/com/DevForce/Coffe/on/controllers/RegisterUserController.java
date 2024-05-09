package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.registerUser.RegisterUserRepository;
import br.com.DevForce.Coffe.on.domain.registerUser.RequestRegisterUser;
import br.com.DevForce.Coffe.on.services.RegisterUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/registerUsers")
public class RegisterUserController {

    private RegisterUserService registerUserService;
    private RegisterUserRepository registerUserRepository;


    @Autowired
    public RegisterUserController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @GetMapping
    public List<RegisterUser> listarUsuarios() {
        return registerUserService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RequestRegisterUser data) {
        try {
            RegisterUser newRegisterUser = registerUserService.registerNewUser(data);
            return ResponseEntity.ok(newRegisterUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/registerUsers/{id}")
    public ResponseEntity<RegisterUser> getUserById(@PathVariable String id) {
        Optional<RegisterUser> user = registerUserRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable String id) {
        boolean activated = registerUserService.activateUser(id);
        if (activated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para desativar usuário
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable String id) {
        boolean deactivated = registerUserService.deactivateUser(id);
        if (deactivated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin("*")
    @PutMapping("/registerUsers/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody RequestRegisterUser requestRegisterUser) {
        try {
            RegisterUser updatedUser = registerUserService.updateUser(userId, requestRegisterUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

}

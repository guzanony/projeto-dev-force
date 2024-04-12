package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.registerUser.RequestRegisterUser;
import br.com.DevForce.Coffe.on.services.RegisterUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/registerUsers")
public class RegisterUserController {

    private RegisterUserService registerUserService;


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
}

package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUserRepository;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RequestRegisterUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/registerUsers")
public class RegisterUserController {
    @Autowired
    private RegisterUserRepository repository;
    @GetMapping
    public ResponseEntity getAllRegisterUsers() {
        var allRegisterUser = repository.findAll();
        return ResponseEntity.ok(allRegisterUser);
    }

    @PostMapping
    public ResponseEntity registerUser( @RequestBody @Valid RequestRegisterUser data ) {
        RegisterUser newRegisterUser = new RegisterUser(data);
        repository.save(newRegisterUser);
        return ResponseEntity.ok().build();
    }
}

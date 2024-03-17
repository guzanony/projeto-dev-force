package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/userAdmin")
public class UserAdminController {
    @Autowired
    private UserAdminRepository repository;
    @GetMapping
    public ResponseEntity getAllUserAdmin() {
        var allUserAdmin = repository.findAll();
        return ResponseEntity.ok(allUserAdmin);
    }

    @PostMapping
    public ResponseEntity userAdmin( @RequestBody @Valid RequestUserAdmin data ) {
        UserAdmin newUserAdmin = new UserAdmin(data);
        repository.save(newUserAdmin);
        return ResponseEntity.ok().build();
    }
}

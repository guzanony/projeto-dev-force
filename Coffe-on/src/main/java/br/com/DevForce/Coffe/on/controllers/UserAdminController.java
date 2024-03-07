package br.com.DevForce.Coffe.on.controllers;

import br.com.DevForce.Coffe.on.domain.user.RequestUserAdmin;
import br.com.DevForce.Coffe.on.domain.user.UserAdmin;
import br.com.DevForce.Coffe.on.domain.user.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usersAdmin")
public class UserAdminController {
@Autowired
private UserAdminRepository repository;
@CrossOrigin("*")
@GetMapping
public ResponseEntity getAllUsers() {
 var allUserAdmin = repository.findAll();
return ResponseEntity.ok(allUserAdmin);
 }

@PostMapping
public ResponseEntity registerUserAdmin( @RequestBody @Valid RequestUserAdmin data ) {
UserAdmin newUserAdmin = new UserAdmin(data);
repository.save(newUserAdmin);
return ResponseEntity.ok().build();
    }
}

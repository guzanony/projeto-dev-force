package br.com.DevForce.Coffe.on.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping
    public ResponseEntity getAllProdructs() {
        return ResponseEntity.ok("deu ok");
    }
}

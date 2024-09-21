package br.com.KiloByte.infra.security;

import br.com.KiloByte.domain.cliente.Cliente;
import br.com.KiloByte.domain.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomClienteDetailsService implements UserDetailsService {
    @Autowired
    private ClienteRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = this.repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(cliente.getEmail(), cliente.getPassword(), new ArrayList<>());
    }
}

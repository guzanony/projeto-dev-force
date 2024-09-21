package br.com.KiloByte.infra.security;

import br.com.KiloByte.domain.user.userAdmin.UserAdmin;
import br.com.KiloByte.domain.user.userAdmin.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserAdminDetailsService implements UserDetailsService {
    @Autowired
    private UserAdminRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserAdmin userAdmin = this.repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       return new org.springframework.security.core.userdetails.User(userAdmin.getUsername(), userAdmin.getPassword(), new ArrayList<>());
    }
}

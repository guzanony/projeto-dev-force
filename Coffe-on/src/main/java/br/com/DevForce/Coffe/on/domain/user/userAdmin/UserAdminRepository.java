package br.com.DevForce.Coffe.on.domain.user.userAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAdminRepository extends JpaRepository<UserAdmin, String> { //Verifica o usuário pelo login
    UserDetails findByUsername(String username);

}

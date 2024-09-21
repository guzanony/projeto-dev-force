package br.com.KiloByte.domain.user.userAdmin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAdminRepository extends JpaRepository<UserAdmin, String> {//Verifica o usuário pelo login
    Optional<UserAdmin> findByUsername(String username);

    List<UserAdmin> findByRole(String role);
}

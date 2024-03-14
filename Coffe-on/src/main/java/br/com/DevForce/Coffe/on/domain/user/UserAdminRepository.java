package br.com.DevForce.Coffe.on.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdminRepository extends JpaRepository<UserAdmin, String> {
    UserAdmin findByUsername(String username);
}

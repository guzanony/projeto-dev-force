package br.com.DevForce.Coffe.on.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterUserRepository extends JpaRepository<RegisterUser, String> {
}
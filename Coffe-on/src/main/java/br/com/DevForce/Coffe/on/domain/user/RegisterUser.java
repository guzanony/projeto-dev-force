package br.com.DevForce.Coffe.on.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.*;

@Table(name="registeruser")
@Entity(name="registeruser")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegisterUser {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String email;
    private String password;
    private String cpf;
    private String grupo;

    public RegisterUser(RequestRegisterUser requestRegisterUser) {
        this.username = requestRegisterUser.username();
        this.email = requestRegisterUser.email();
        this.password = requestRegisterUser.password();
        this.cpf = requestRegisterUser.cpf();
        this.grupo = requestRegisterUser.grupo();
    }

}

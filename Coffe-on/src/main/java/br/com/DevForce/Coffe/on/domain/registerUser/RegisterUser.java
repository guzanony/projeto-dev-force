package br.com.DevForce.Coffe.on.domain.registerUser;

import jakarta.persistence.*;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

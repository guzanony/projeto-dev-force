package br.com.KiloByte.domain.registerUser;

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
    @Column(name = "active", nullable = false)
    private boolean active = true;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}

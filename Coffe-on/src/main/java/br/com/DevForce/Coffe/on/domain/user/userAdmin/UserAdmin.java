package br.com.DevForce.Coffe.on.domain.user.userAdmin;

import jakarta.persistence.*;
import lombok.*;

@Table(name="useradmin")
@Entity(name="useradmin")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserAdmin {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String username;
    private String password;
    private String role;
    private Long clienteId;
    private String cpf;
    private String grupo;
    @Column(name = "active", nullable = false)
    private boolean active = true;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}

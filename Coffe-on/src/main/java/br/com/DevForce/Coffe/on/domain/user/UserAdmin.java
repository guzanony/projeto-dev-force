package br.com.DevForce.Coffe.on.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.*;

@Table(name="useradmin")
@Entity(name="useradmin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserAdmin {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;

    public UserAdmin(RequestUserAdmin requestUserAdmin) {
         this.username = requestUserAdmin.username();
         this.password = requestUserAdmin.password();
    }

}

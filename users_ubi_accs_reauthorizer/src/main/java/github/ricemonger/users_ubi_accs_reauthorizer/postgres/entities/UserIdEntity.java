package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "helper_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserIdEntity userEntity)) {
            return false;
        }
        return Objects.equals(id, userEntity.id);
    }
}

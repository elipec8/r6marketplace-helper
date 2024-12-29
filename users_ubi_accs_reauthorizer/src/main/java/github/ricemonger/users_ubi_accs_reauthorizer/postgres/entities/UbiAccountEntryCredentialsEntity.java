package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "ubi_account_authorization_entry")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiAccountEntryEntityId.class)
public class UbiAccountEntryCredentialsEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserIdEntity user;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(columnDefinition = "TEXT", name = "ubi_remember_me_ticket")
    private String ubiRememberMeTicket;

    public Long getUserId_() {
        return user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UbiAccountEntryCredentialsEntity ubiAccountEntryEntity)) {
            return false;
        }
        return Objects.equals(user, ubiAccountEntryEntity.user) &&
               Objects.equals(email, ubiAccountEntryEntity.email);
    }
}

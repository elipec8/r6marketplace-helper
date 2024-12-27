package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "ubi_account_authorization_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiAccountEntryEntityId.class)
public class UbiAccountEntryCredentialsEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserIdEntity user;

    @Id
    private String email;

    private String encodedPassword;

    @Column(columnDefinition = "TEXT")
    private String ubiRememberMeTicket;

    public Long getUserId_() {
        return user.getId();
    }
}

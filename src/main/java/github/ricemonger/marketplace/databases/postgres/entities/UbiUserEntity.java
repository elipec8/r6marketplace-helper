package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity(name = "ubi_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiUserEntityId.class)
public class UbiUserEntity {

    @Id
    private String chatId;

    @Id
    private String email;
    private String password;

    private String ubiProfileId;
    private String ubiSessionId;
    private String ubiAuthTicket;
    private String ubiSpaceId;
    private String ubiTwoFactorAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    public UbiUserEntity(String chatId, String email, String password) {
        this.chatId = chatId;
        this.email = email;
        this.password = password;
    }
}

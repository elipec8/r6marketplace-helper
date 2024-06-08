package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ubi_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramLinkedUbiUserEntityId.class)
public class TelegramLinkedUbiUserEntity {
    @Id
    private String chatId;

    @Id
    private String email;
    private String encodedPassword;

    private String ubiProfileId;
    private String ubiSessionId;
    private String ubiSpaceId;
    @Column(columnDefinition = "TEXT")
    private String ubiAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiTwoFactorAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberDeviceTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberMeTicket;

    @Column(columnDefinition = "TEXT")
    private String ownedItemsIds;
}

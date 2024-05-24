package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String ubiSpaceId;
    @Column(columnDefinition = "TEXT")
    private String ubiAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiTwoFactorAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberDeviceTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberMeTicket;

    @ManyToMany
    private List<ItemEntity> ownedItems = new ArrayList<>();

    public UbiUserEntity(String chatId, String email, String password) {
        this.chatId = chatId;
        this.email = email;
        this.password = password;
    }
}

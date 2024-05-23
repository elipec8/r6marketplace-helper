package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("UbiUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserNode {

    @Id
    @GeneratedValue
    private String id;

    private String email;
    private String password;

    private String ubiProfileId;
    private String ubiSessionId;
    private String ubiAuthTicket;
    private String ubiSpaceId;
    private String ubiTwoFactorAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude
    private TelegramLinkedUserNode linkedTelegramUser;

    public UbiUserNode(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

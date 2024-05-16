package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("UbiUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserEntity {

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
    private TelegramLinkedUserEntity linkedTelegramUser;

    public UbiUserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

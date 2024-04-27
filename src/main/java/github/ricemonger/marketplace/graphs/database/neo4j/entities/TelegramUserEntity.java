package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("TelegramUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {

    @Id
    private String chatId;

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.OUTGOING)
    private List<UbiUserEntity> linkedAccounts;
}

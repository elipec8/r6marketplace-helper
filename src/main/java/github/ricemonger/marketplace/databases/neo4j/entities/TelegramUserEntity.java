package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Map;

@Node("TelegramUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {

    @Id
    private String chatId;

    private InputState inputState;

    private InputGroup inputGroup;

    private Map<InputState, String> inputValues;

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.OUTGOING)
    private List<UbiUserEntity> linkedUbisoftAccounts;
}

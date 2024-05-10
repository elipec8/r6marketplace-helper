package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.telegramBot.executors.InputGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Node("TelegramLinkedUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramLinkedUserEntity {

    public TelegramLinkedUserEntity(String chatId){
        this.chatId = chatId;
    }

    @Id
    private String chatId;

    private InputState inputState = InputState.BASE;

    private InputGroup inputGroup = InputGroup.BASE;

    private boolean publicNotificationsEnabledFlag = true;

    @Relationship(value = "INPUT_VALUES", direction = Relationship.Direction.OUTGOING)
    private List<TelegramInputValuesEntity> inputValues = new ArrayList<>();

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.OUTGOING)
    private List<UbiUserEntity> linkedUbisoftAccounts = new ArrayList<>();
}

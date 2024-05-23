package github.ricemonger.marketplace.databases.neo4j.entities;


import github.ricemonger.telegramBot.executors.InputState;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("TelegramUserInputValues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramInputValuesNode {

    public TelegramInputValuesNode(InputState inputState, String value){
        this.inputState = inputState;
        this.value = value;
    }

    @Id
    @GeneratedValue
    private String id;

    @Relationship(value = "INPUT_VALUES", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude
    private TelegramLinkedUserNode owner;

    private InputState inputState;

    private String value;
}

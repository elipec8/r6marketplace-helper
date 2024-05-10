package github.ricemonger.marketplace.databases.neo4j.entities;


import github.ricemonger.telegramBot.executors.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("TelegramUserInputValues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramInputValuesEntity {

    public TelegramInputValuesEntity(InputState inputState, String value){
        this.inputState = inputState;
        this.value = value;
    }

    @Id
    @GeneratedValue
    private String id;

    @Relationship(value = "INPUT_VALUES", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude
    private TelegramLinkedUserEntity owner;

    private InputState inputState;

    private String value;
}

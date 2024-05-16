package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("TelegramLinkedUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramLinkedUserEntity {

    @Id
    private String chatId;

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    private boolean publicNotificationsEnabledFlag = true;

    private int speculativeItemSearchProfitPercentSetting = 40;
    private int speculativeItemSearchProfitAbsoluteSetting = 50;
    private int speculativeItemSearchMinSellPriceSetting = 0;
    private int speculativeItemSearchMaxSellPriceSetting = 15000;

    @Relationship(value = "INPUT_VALUES", direction = Relationship.Direction.OUTGOING)
    private List<TelegramInputValuesEntity> inputValues = new ArrayList<>();

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.OUTGOING)
    private List<UbiUserEntity> linkedUbisoftAccounts = new ArrayList<>();

    public TelegramLinkedUserEntity(String chatId) {
        this.chatId = chatId;
    }

    public TelegramLinkedUserEntity(String chatId, InputState inputState, InputGroup inputGroup, boolean publicNotificationsEnabledFlag, int speculativeItemSearchProfitPercentSetting, int speculativeItemSearchProfitAbsoluteSetting, int speculativeItemSearchMinSellPriceSetting, int speculativeItemSearchMaxSellPriceSetting) {
        this.chatId = chatId;
        this.inputState = inputState;
        this.inputGroup = inputGroup;
        this.publicNotificationsEnabledFlag = publicNotificationsEnabledFlag;
    }
}

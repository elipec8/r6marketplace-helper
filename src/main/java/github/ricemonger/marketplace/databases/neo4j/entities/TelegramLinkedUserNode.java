package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("TelegramLinkedUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramLinkedUserNode {

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
    private List<TelegramInputValuesNode> inputValues = new ArrayList<>();

    @Relationship(value = "LINKED_ACCOUNTS", direction = Relationship.Direction.OUTGOING)
    private List<UbiUserNode> linkedUbisoftAccounts = new ArrayList<>();

    public TelegramLinkedUserNode(String chatId) {
        this.chatId = chatId;
    }

    public TelegramLinkedUserNode(String chatId, InputState inputState, InputGroup inputGroup, boolean publicNotificationsEnabledFlag, int speculativeItemSearchProfitPercentSetting, int speculativeItemSearchProfitAbsoluteSetting, int speculativeItemSearchMinSellPriceSetting, int speculativeItemSearchMaxSellPriceSetting) {
        this.chatId = chatId;
        this.inputState = inputState;
        this.inputGroup = inputGroup;
        this.publicNotificationsEnabledFlag = publicNotificationsEnabledFlag;
    }
}

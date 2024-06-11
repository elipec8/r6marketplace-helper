package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "telegram_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {
    @Id
    private String chatId;

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    private boolean publicNotificationsEnabledFlag = true;

    private Integer itemShowMessagesLimit = 50;
    private boolean itemShowFewInMessageFlag = false;

    private boolean itemShowNameFlag = true;
    private boolean itemShowItemTypeFlag = true;
    private boolean itemShowMaxBuyPrice = true;
    private boolean itemShowBuyOrdersCountFlag = true;
    private boolean itemShowMinSellPriceFlag = true;
    private boolean itemsShowSellOrdersCountFlag = true;
    private boolean itemShowPictureFlag = true;

    @OneToMany(mappedBy = "chatId")
    private List<ItemFilterEntity> itemShowAppliedFilters;
}

package github.ricemonger.marketplace.services.central_trade_manager;

import github.ricemonger.utils.DTOs.AuthorizationDTO;
import github.ricemonger.utils.DTOs.CentralTradeManagerCommand;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommandForCentralTradeManagerFactory {

    public List<CentralTradeManagerCommand> createCommandsForCentralTradeManagerForUser(Collection<PotentialPersonalSellTrade> resultingSellTrades,
                                                                                        Collection<UbiTrade> currentSellTrades,
                                                                                        Collection<PotentialPersonalBuyTrade> resultingBuyTrades,
                                                                                        Collection<UbiTrade> currentBuyTrades,
                                                                                        Long userId,
                                                                                        AuthorizationDTO authorizationDTO,
                                                                                        String chatId,
                                                                                        boolean privateNotificationsEnabledFlag) {
        List<CentralTradeManagerCommand> commands = new LinkedList<>();

        for (PotentialPersonalSellTrade sellTrade : resultingSellTrades) {
            if (sellTrade.tradeForItemAlreadyExists()) {
                if (sellTrade.getOldPrice() != sellTrade.getNewPrice()) {
                    commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                            CentralTradeManagerCommandType.SELL_ORDER_UPDATE, sellTrade.getItemId(), sellTrade.getItemName(),
                            sellTrade.getTradeId(), sellTrade.getOldPrice(), sellTrade.getNewPrice()));
                }
            } else {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                        CentralTradeManagerCommandType.SELL_ORDER_CREATE, sellTrade.getItemId(), sellTrade.getItemName(),
                        sellTrade.getNewPrice()));
            }
        }

        for (UbiTrade currentSellTrade : currentSellTrades) {
            if (resultingSellTrades.stream().noneMatch(sellTrade -> sellTrade.getTradeId() != null && Objects.equals(sellTrade.getTradeId(),
                    currentSellTrade.getTradeId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                        CentralTradeManagerCommandType.SELL_ORDER_CANCEL, currentSellTrade.getItemId(), currentSellTrade.getItemName(),
                        currentSellTrade.getTradeId(), currentSellTrade.getProposedPaymentPrice()));
            }
        }

        for (PotentialPersonalBuyTrade buyTrade : resultingBuyTrades) {
            if (buyTrade.tradeForItemAlreadyExists()) {
                if (buyTrade.getOldPrice() != buyTrade.getNewPrice()) {
                    commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                            CentralTradeManagerCommandType.BUY_ORDER_UPDATE, buyTrade.getItemId(), buyTrade.getItemName(),
                            buyTrade.getTradeId(), buyTrade.getOldPrice(), buyTrade.getNewPrice()));
                }
            } else {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                        CentralTradeManagerCommandType.BUY_ORDER_CREATE, buyTrade.getItemId(), buyTrade.getItemName(),
                        buyTrade.getNewPrice()));
            }
        }

        for (UbiTrade currentBuyTrade : currentBuyTrades) {
            if (resultingBuyTrades.stream().noneMatch(buyTrade -> buyTrade.getTradeId() != null && Objects.equals(buyTrade.getTradeId(),
                    currentBuyTrade.getTradeId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag,
                        CentralTradeManagerCommandType.BUY_ORDER_CANCEL, currentBuyTrade.getItemId(), currentBuyTrade.getItemName(),
                        currentBuyTrade.getTradeId(), currentBuyTrade.getProposedPaymentPrice()));
            }
        }

        return commands;
    }
}

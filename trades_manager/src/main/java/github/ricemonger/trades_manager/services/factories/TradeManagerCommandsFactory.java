package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalBuyTrade;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalSellTrade;
import github.ricemonger.trades_manager.services.DTOs.PrioritizedUbiTrade;
import github.ricemonger.trades_manager.services.DTOs.TradeManagerCommand;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TradeManagerCommandsFactory {

    public List<TradeManagerCommand> createTradeManagerCommandsForUser(Collection<PotentialPersonalSellTrade> resultingSellTrades,
                                                                       Collection<PrioritizedUbiTrade> prioritizedCurrentSellTrades,
                                                                       Collection<PotentialPersonalBuyTrade> resultingBuyTrades,
                                                                       Collection<PrioritizedUbiTrade> prioritizedCurrentBuyTrades,
                                                                       Long userId,
                                                                       AuthorizationDTO authorizationDTO,
                                                                       ConfigTrades configTrades) {
        List<TradeManagerCommand> commands = new LinkedList<>();

        int sellOrdersCreated = 0;
        int buyOrdersCreated = 0;

        int sellOrdersSlotsLeft = configTrades.getSellSlots() - prioritizedCurrentSellTrades.size();
        int buyOrdersSlotsLeft = configTrades.getBuySlots() - prioritizedCurrentBuyTrades.size();

        for (PotentialPersonalSellTrade sellTrade : resultingSellTrades) {
            if (sellTrade.tradeForItemAlreadyExists()) {
                if (!Objects.equals(sellTrade.getOldPrice(), sellTrade.getNewPrice())) {
                    commands.add(new TradeManagerCommand(userId, authorizationDTO,
                            CentralTradeManagerCommandType.SELL_ORDER_UPDATE, sellTrade.getItemId(), sellTrade.getItemName(),
                            sellTrade.getTradeId(), sellTrade.getOldPrice(), sellTrade.getNewPrice()));
                }
            } else {
                sellOrdersCreated++;
                commands.add(new TradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.SELL_ORDER_CREATE, sellTrade.getItemId(), sellTrade.getItemName(),
                        sellTrade.getNewPrice()));
            }
        }

        for (PrioritizedUbiTrade prioritizedCurrentSellTrade : prioritizedCurrentSellTrades.stream().sorted(new Comparator<PrioritizedUbiTrade>() {
            @Override
            public int compare(PrioritizedUbiTrade o1, PrioritizedUbiTrade o2) {
                Long tradePriority1 = o1.getTradePriority() == null ? Long.MIN_VALUE : o1.getTradePriority();
                Long tradePriority2 = o2.getTradePriority() == null ? Long.MIN_VALUE : o2.getTradePriority();

                return tradePriority1.compareTo(tradePriority2);
            }
        }).toList()) {
            if (sellOrdersSlotsLeft < sellOrdersCreated && resultingSellTrades.stream().noneMatch(sellTrade -> sellTrade.getTradeId() != null && Objects.equals(sellTrade.getTradeId(),
                    prioritizedCurrentSellTrade.getTradeId()))) {
                sellOrdersSlotsLeft++;
                commands.add(new TradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.SELL_ORDER_CANCEL, prioritizedCurrentSellTrade.getItemId(), prioritizedCurrentSellTrade.getItemName(),
                        prioritizedCurrentSellTrade.getTradeId(), prioritizedCurrentSellTrade.getProposedPaymentPrice()));
            }
        }

        for (PotentialPersonalBuyTrade buyTrade : resultingBuyTrades) {
            if (buyTrade.tradeForItemAlreadyExists()) {
                if (!Objects.equals(buyTrade.getOldPrice(), buyTrade.getNewPrice())) {
                    commands.add(new TradeManagerCommand(userId, authorizationDTO,
                            CentralTradeManagerCommandType.BUY_ORDER_UPDATE, buyTrade.getItemId(), buyTrade.getItemName(),
                            buyTrade.getTradeId(), buyTrade.getOldPrice(), buyTrade.getNewPrice()));
                }
            } else {
                buyOrdersCreated++;
                commands.add(new TradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.BUY_ORDER_CREATE, buyTrade.getItemId(), buyTrade.getItemName(),
                        buyTrade.getNewPrice()));
            }
        }

        for (PrioritizedUbiTrade prioritizedCurrentBuyTrade : prioritizedCurrentBuyTrades.stream().sorted(new Comparator<PrioritizedUbiTrade>() {
            @Override
            public int compare(PrioritizedUbiTrade o1, PrioritizedUbiTrade o2) {
                Long tradePriority1 = o1.getTradePriority() == null ? Long.MIN_VALUE : o1.getTradePriority();
                Long tradePriority2 = o2.getTradePriority() == null ? Long.MIN_VALUE : o2.getTradePriority();

                return tradePriority1.compareTo(tradePriority2);
            }
        }).toList()) {
            if (buyOrdersSlotsLeft < buyOrdersCreated && resultingBuyTrades.stream().noneMatch(buyTrade -> buyTrade.getTradeId() != null && Objects.equals(buyTrade.getTradeId(),
                    prioritizedCurrentBuyTrade.getTradeId()))) {
                buyOrdersSlotsLeft++;
                commands.add(new TradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.BUY_ORDER_CANCEL, prioritizedCurrentBuyTrade.getItemId(), prioritizedCurrentBuyTrade.getItemName(),
                        prioritizedCurrentBuyTrade.getTradeId(), prioritizedCurrentBuyTrade.getProposedPaymentPrice()));
            }
        }

        return commands;
    }
}

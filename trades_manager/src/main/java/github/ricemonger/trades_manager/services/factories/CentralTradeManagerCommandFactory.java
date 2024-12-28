package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.CentralTradeManagerCommand;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalBuyTrade;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalSellTrade;
import github.ricemonger.trades_manager.services.DTOs.Trade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CentralTradeManagerCommandFactory {

    public List<CentralTradeManagerCommand> createCommandsForCentralTradeManagerForUser(Collection<PotentialPersonalSellTrade> resultingSellTrades,
                                                                                        Collection<Trade> currentSellTrades,
                                                                                        Collection<PotentialPersonalBuyTrade> resultingBuyTrades,
                                                                                        Collection<Trade> currentBuyTrades,
                                                                                        Long userId,
                                                                                        AuthorizationDTO authorizationDTO) {
        List<CentralTradeManagerCommand> commands = new LinkedList<>();

        for (PotentialPersonalSellTrade sellTrade : resultingSellTrades) {
            if (sellTrade.tradeForItemAlreadyExists()) {
                if (!Objects.equals(sellTrade.getOldPrice(), sellTrade.getNewPrice())) {
                    commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                            CentralTradeManagerCommandType.SELL_ORDER_UPDATE, sellTrade.getItemId(), sellTrade.getItemName(),
                            sellTrade.getTradeId(), sellTrade.getOldPrice(), sellTrade.getNewPrice()));
                }
            } else {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.SELL_ORDER_CREATE, sellTrade.getItemId(), sellTrade.getItemName(),
                        sellTrade.getNewPrice()));
            }
        }

        for (Trade currentSellTrade : currentSellTrades) {
            if (resultingSellTrades.stream().noneMatch(sellTrade -> sellTrade.getTradeId() != null && Objects.equals(sellTrade.getTradeId(),
                    currentSellTrade.getTradeId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.SELL_ORDER_CANCEL, currentSellTrade.getItemId(), currentSellTrade.getItemName(),
                        currentSellTrade.getTradeId(), currentSellTrade.getProposedPaymentPrice()));
            }
        }

        for (PotentialPersonalBuyTrade buyTrade : resultingBuyTrades) {
            if (buyTrade.tradeForItemAlreadyExists()) {
                if (!Objects.equals(buyTrade.getOldPrice(), buyTrade.getNewPrice())) {
                    commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                            CentralTradeManagerCommandType.BUY_ORDER_UPDATE, buyTrade.getItemId(), buyTrade.getItemName(),
                            buyTrade.getTradeId(), buyTrade.getOldPrice(), buyTrade.getNewPrice()));
                }
            } else {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.BUY_ORDER_CREATE, buyTrade.getItemId(), buyTrade.getItemName(),
                        buyTrade.getNewPrice()));
            }
        }

        for (Trade currentBuyTrade : currentBuyTrades) {
            if (resultingBuyTrades.stream().noneMatch(buyTrade -> buyTrade.getTradeId() != null && Objects.equals(buyTrade.getTradeId(),
                    currentBuyTrade.getTradeId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO,
                        CentralTradeManagerCommandType.BUY_ORDER_CANCEL, currentBuyTrade.getItemId(), currentBuyTrade.getItemName(),
                        currentBuyTrade.getTradeId(), currentBuyTrade.getProposedPaymentPrice()));
            }
        }

        return commands;
    }
}

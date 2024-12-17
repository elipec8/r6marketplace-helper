package github.ricemonger.marketplace.services.central_trade_manager;

import github.ricemonger.utils.enums.TradeCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class PotentialTrade implements Comparable<PotentialTrade> {
    private final PersonalItem personalItem;

    private final PotentialTradeStats potentialTradeStats;

    public abstract TradeCategory getTradeCategory();

    @Override
    public int compareTo(@NotNull PotentialTrade other) {
        int tradeCategoryComparison = this.getTradeCategory().compareTo(other.getTradeCategory());
        if (tradeCategoryComparison != 0) {
            return tradeCategoryComparison;
        } else {
            Long priority = this.getTradePriority() * this.getPriorityMultiplier();
            Long otherPriority = other.getTradePriority() * other.getPriorityMultiplier();

            return otherPriority.compareTo(priority);
        }
    }

    private Integer getPriorityMultiplier() {
        return personalItem.getPriorityMultiplier();
    }

    public Long getTradePriority() {
        return potentialTradeStats.getTradePriority();
    }

    public boolean tradeForItemAlreadyExists() {
        return getPersonalItem().getTradeAlreadyExists();
    }

    public String getItemId() {
        return getPersonalItem().getItemId();
    }

    public int getNewPrice() {
        return potentialTradeStats.getPrice();
    }

    public int getOldPrice() {
        return getPersonalItem().getProposedPaymentPrice();
    }

    public String getItemName() {
        return getPersonalItem().getItemName();
    }

    public String getTradeId() {
        return getPersonalItem().getTradeId();
    }
}

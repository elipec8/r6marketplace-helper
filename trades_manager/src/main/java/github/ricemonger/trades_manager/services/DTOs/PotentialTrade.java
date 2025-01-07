package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class PotentialTrade implements Comparable<PotentialTrade> {
    private final PersonalItem personalItem;

    private final PotentialTradeStats potentialTradeStats;

    public abstract TradeCategory getTradeCategory();

    @Override
    public int compareTo(PotentialTrade other) {
        int tradeCategoryComparison = this.getTradeCategory().compareTo(other.getTradeCategory());
        if (tradeCategoryComparison != 0) {
            return tradeCategoryComparison;
        } else {
            int thisPriorityMultiplier = this.getPriorityMultiplier() == null || this.getPriorityMultiplier() < 1 ? 1 : this.getPriorityMultiplier();
            int otherPriorityMultiplier = other.getPriorityMultiplier() == null || other.getPriorityMultiplier() < 1 ? 1 : other.getPriorityMultiplier();

            Long thisPriority = this.getTradePriority() > 0 ? this.getTradePriority() * thisPriorityMultiplier : this.getTradePriority() / thisPriorityMultiplier;

            Long otherPriority = other.getTradePriority() > 0?  other.getTradePriority() * otherPriorityMultiplier : other.getTradePriority() / otherPriorityMultiplier;

            int priorityComparison = otherPriority.compareTo(thisPriority);

            if (priorityComparison != 0) {
                return priorityComparison;
            } else {
                int itemIdComparison = this.getItemId().compareTo(other.getItemId());

                if (itemIdComparison != 0) {
                    return itemIdComparison;
                } else {
                    return this.getNewPrice().compareTo(other.getNewPrice());
                }
            }
        }
    }

    public Integer getPriorityMultiplier() {
        return personalItem == null ? null : personalItem.getPriorityMultiplier();
    }

    public Long getTradePriority() {
        return potentialTradeStats == null ? null : potentialTradeStats.getTradePriority();
    }

    public Boolean tradeForItemAlreadyExists() {
        return personalItem == null ? null : personalItem.getTradeAlreadyExists();
    }

    public String getItemId() {
        return personalItem == null ? null : personalItem.getItemId();
    }

    public Integer getNewPrice() {
        return potentialTradeStats == null ? null : potentialTradeStats.getPrice();
    }

    public Integer getOldPrice() {
        return personalItem == null ? null : personalItem.getProposedPaymentPrice();
    }

    public String getItemName() {
        return personalItem == null ? null : personalItem.getName();
    }

    public String getTradeId() {
        return personalItem == null ? null : personalItem.getTradeId();
    }
}

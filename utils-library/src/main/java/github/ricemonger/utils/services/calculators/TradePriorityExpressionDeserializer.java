package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import org.springframework.stereotype.Service;

@Service
public class TradePriorityExpressionDeserializer {

    public boolean isValidExpression(String expression) {
        return true;
    }


    public Long calculateTradePriority(String sellTradePriorityExpression, Item item, Integer price, Integer time) {

    }
}

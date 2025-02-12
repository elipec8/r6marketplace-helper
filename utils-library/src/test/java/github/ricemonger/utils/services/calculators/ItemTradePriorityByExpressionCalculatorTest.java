package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class ItemTradePriorityByExpressionCalculatorTest {

    private ItemTradePriorityByExpressionCalculator itemTradePriorityByExpressionCalculator = spy(new ItemTradePriorityByExpressionCalculator());

    @Test
    public void calculateTradePriority_should_return_expected_result() {
        Item item = new Item();

        Integer price = 100;

        Integer time = 1000;

        String expression = "if price == 100 then return 101|if time == 1000 then return 1001|return 10001";

        Long result = itemTradePriorityByExpressionCalculator.calculateTradePriority(expression, item, price, time);

        assertEquals(101, result);
    }
}
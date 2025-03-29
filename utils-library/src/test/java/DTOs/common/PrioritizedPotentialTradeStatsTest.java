package DTOs.common;

import github.ricemonger.utils.DTOs.common.PrioritizedPotentialTradeStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PrioritizedPotentialTradeStatsTest {

    @Test
    public void isValid_should_return_false_when_price_is_null() {
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats = new PrioritizedPotentialTradeStats(null, 1, 1L);
        assertFalse(prioritizedPotentialTradeStats.isValid());
    }

    @Test
    public void isValid_should_return_false_when_prognosedTradeSuccessMinutes_is_null() {
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats = new PrioritizedPotentialTradeStats(1, null, 1L);
        assertFalse(prioritizedPotentialTradeStats.isValid());
    }

    @Test
    public void isValid_should_return_false_when_tradePriority_is_null() {
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats = new PrioritizedPotentialTradeStats(1, 1, null);
        assertFalse(prioritizedPotentialTradeStats.isValid());
    }
}
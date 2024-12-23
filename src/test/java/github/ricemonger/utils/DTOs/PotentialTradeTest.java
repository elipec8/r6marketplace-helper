package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.PersonalItem;
import github.ricemonger.utils.DTOs.personal.PotentialTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotentialTradeTest {


    @Test
    public void getPriorityMultiplier_should_return_priorityMultiplier() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setPriorityMultiplier(1);
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals(1, potentialTrade.getPriorityMultiplier());
    }

    @Test
    public void getPriorityMultiplier_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        Integer result = potentialTrade.getPriorityMultiplier();

        assertNull(result);
    }

    @Test
    public void getTradePriority_should_return_tradePriority() {
        PotentialTradeStats potentialTradeStats = new PotentialTradeStats();
        potentialTradeStats.setTradePriority(1L);
        PotentialTrade potentialTrade = new PotentialTrade(null, potentialTradeStats) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals(1L, potentialTrade.getTradePriority());
    }

    @Test
    public void getTradePriority_should_return_null_when_potentialTradeStats_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        Long result = potentialTrade.getTradePriority();

        assertNull(result);
    }

    @Test
    public void tradeForItemAlreadyExists_should_return_tradeAlreadyExists() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setTradeAlreadyExists(true);
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertTrue(potentialTrade.tradeForItemAlreadyExists());
    }

    @Test
    public void tradeForItemAlreadyExists_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        Boolean result = potentialTrade.tradeForItemAlreadyExists();

        assertNull(result);
    }

    @Test
    public void getItemId_should_return_itemId() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setItem(new Item("itemId"));
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals("itemId", potentialTrade.getItemId());
    }

    @Test
    public void getItemId_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        String result = potentialTrade.getItemId();

        assertNull(result);
    }

    @Test
    public void getNewPrice_should_return_price() {
        PotentialTradeStats potentialTradeStats = new PotentialTradeStats();
        potentialTradeStats.setPrice(1);
        PotentialTrade potentialTrade = new PotentialTrade(null, potentialTradeStats) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals(1, potentialTrade.getNewPrice());
    }

    @Test
    public void getNewPrice_should_return_null_when_potentialTradeStats_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        Integer result = potentialTrade.getNewPrice();

        assertNull(result);
    }

    @Test
    public void getOldPrice_should_return_proposedPaymentPrice() {
        PersonalItem personalItem = new PersonalItem();
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setProposedPaymentPrice(1);
        personalItem.setExistingTrade(ubiTrade);
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals(1, potentialTrade.getOldPrice());
    }

    @Test
    public void getOldPrice_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        Integer result = potentialTrade.getOldPrice();

        assertNull(result);
    }

    @Test
    public void getItemName_should_return_name() {
        PersonalItem personalItem = new PersonalItem();
        Item item = new Item();
        item.setName("name");
        personalItem.setItem(item);
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals("name", potentialTrade.getItemName());
    }

    @Test
    public void getItemName_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        String result = potentialTrade.getItemName();

        assertNull(result);
    }

    @Test
    public void getTradeId_should_return_tradeId() {
        PersonalItem personalItem = new PersonalItem();
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setTradeId("tradeId");
        personalItem.setExistingTrade(ubiTrade);
        PotentialTrade potentialTrade = new PotentialTrade(personalItem, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        assertEquals("tradeId", potentialTrade.getTradeId());
    }

    @Test
    public void getTradeId_should_return_null_when_personalItem_is_null() {
        PotentialTrade potentialTrade = new PotentialTrade(null, null) {
            @Override
            public TradeCategory getTradeCategory() {
                return null;
            }
        };

        String result = potentialTrade.getTradeId();

        assertNull(result);
    }
}
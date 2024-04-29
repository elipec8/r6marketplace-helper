package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemTests {

    public final static Item ITEM = new Item("1","2","3", List.of("4"),"5");

    public final static Item SAME_ITEM = new Item("1","2","3", List.of("4"),"5");

    public final static Item ALT_ID = new Item("99","2","3", List.of("4"),"5");

    public final static Item ALT_ASSERT_URL = new Item("1","99","3", List.of("4"),"5");

    public final static Item ALT_NAME = new Item("1","2","99", List.of("4"),"5");

    public final static Item ALT_TAGS = new Item("1","2","3", List.of("99"),"5");

    public final static Item ALT_TYPE = new Item("1","2","3", List.of("4"),"99");

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(ITEM,SAME_ITEM);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(ITEM,ALT_ID);
        assertNotEquals(ITEM,ALT_ASSERT_URL);
        assertNotEquals(ITEM,ALT_NAME);
        assertNotEquals(ITEM,ALT_TAGS);
        assertNotEquals(ITEM,ALT_TYPE);
    }

}
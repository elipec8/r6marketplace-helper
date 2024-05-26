package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node;

import github.ricemonger.marketplace.graphQl.graphsDTOs.DtoStatics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        Assertions.assertEquals(DtoStatics.ITEM, DtoStatics.ITEM_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        Assertions.assertNotEquals(DtoStatics.ITEM, DtoStatics.ITEM_ALT_ID);
        Assertions.assertNotEquals(DtoStatics.ITEM, DtoStatics.ITEM_ALT_ASSERT_URL);
        Assertions.assertNotEquals(DtoStatics.ITEM, DtoStatics.ITEM_ALT_NAME);
        Assertions.assertNotEquals(DtoStatics.ITEM, DtoStatics.ITEM_ALT_TAGS);
        Assertions.assertNotEquals(DtoStatics.ITEM, DtoStatics.ITEM_ALT_TYPE);
    }

}
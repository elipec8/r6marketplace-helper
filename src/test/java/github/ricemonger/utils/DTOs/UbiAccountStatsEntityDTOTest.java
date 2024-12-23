package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountStatsEntityDTOTest {

    @Test
    public void hashCode_should_return_hash_code_of_ubiProfileId() {
        UbiAccountStatsEntityDTO dto = new UbiAccountStatsEntityDTO("test");
        assertEquals("test".hashCode(), dto.hashCode());
    }

    @Test
    public void hashCode_should_return_same_hashCode_for_different_fields_except_ubiProfileId() {
        UbiAccountStatsEntityDTO dto1 = new UbiAccountStatsEntityDTO("test");
        dto1.setCreditAmount(1);
        dto1.setOwnedItemsIds(List.of("1"));
        UbiAccountStatsEntityDTO dto2 = new UbiAccountStatsEntityDTO("test");
        dto2.setCreditAmount(2);
        dto2.setOwnedItemsIds(null);

        assertEquals(dto2.hashCode(), dto1.hashCode());
    }

    @Test
    public void equals_should_return_true_for_same_object() {
        UbiAccountStatsEntityDTO dto = new UbiAccountStatsEntityDTO("test");
        assertEquals(dto, dto);
    }

    @Test
    public void equals_should_return_true_for_object_with_same_profileId() {
        UbiAccountStatsEntityDTO dto1 = new UbiAccountStatsEntityDTO("test1");
        dto1.setCreditAmount(1);
        dto1.setOwnedItemsIds(List.of("1"));
        UbiAccountStatsEntityDTO dto2 = new UbiAccountStatsEntityDTO("test1");
        dto2.setCreditAmount(2);
        dto2.setOwnedItemsIds(null);
        assertEquals(dto1, dto2);
    }

    @Test
    public void equals_should_return_false_for_object_with_different_profileId() {
        UbiAccountStatsEntityDTO dto1 = new UbiAccountStatsEntityDTO("test1");
        dto1.setCreditAmount(1);
        dto1.setOwnedItemsIds(List.of());
        UbiAccountStatsEntityDTO dto2 = new UbiAccountStatsEntityDTO("test2");
        dto2.setCreditAmount(1);
        dto2.setOwnedItemsIds(List.of());
        assertNotEquals(dto1, dto2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiAccountStatsEntityDTO dto = new UbiAccountStatsEntityDTO("test");
        assertNotEquals(null, dto);
    }

    @Test
    public void isFullyEqual_should_return_false_for_null() {
        UbiAccountStatsEntityDTO dto = new UbiAccountStatsEntityDTO("test");
        assertFalse(dto.isFullyEqual(null));
    }

    @Test
    public void isFullyEqual_should_return_false_for_object_with_different_fields() {
        UbiAccountStatsEntityDTO dto1 = new UbiAccountStatsEntityDTO("test1");
        dto1.setCreditAmount(1);
        dto1.setOwnedItemsIds(List.of());
        UbiAccountStatsEntityDTO dto2 = new UbiAccountStatsEntityDTO("test1");
        dto2.setCreditAmount(1);
        dto2.setOwnedItemsIds(List.of());

        dto1.setUbiProfileId("test2");
        assertFalse(dto1.isFullyEqual(dto2));
        dto1.setUbiProfileId("test1");
        dto1.setCreditAmount(2);
        assertFalse(dto1.isFullyEqual(dto2));
        dto1.setCreditAmount(1);
        dto1.setOwnedItemsIds(List.of("1"));
        assertFalse(dto1.isFullyEqual(dto2));
        dto1.setOwnedItemsIds(null);
        assertFalse(dto1.isFullyEqual(dto2));
    }
}
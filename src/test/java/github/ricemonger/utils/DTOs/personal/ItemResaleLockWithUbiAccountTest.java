package github.ricemonger.utils.DTOs.personal;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemResaleLockWithUbiAccountTest {

    @Test
    public void constructor_should_properly_set_fields() {
        ItemResaleLockWithUbiAccount itemResaleLockWithUbiAccount =
                new ItemResaleLockWithUbiAccount("ubiProfileId", new ItemResaleLock("itemId", LocalDateTime.of(2021, 1, 1, 0, 0)));

        assertEquals("ubiProfileId", itemResaleLockWithUbiAccount.getUbiProfileId());
        assertEquals("itemId", itemResaleLockWithUbiAccount.getItemId());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), itemResaleLockWithUbiAccount.getExpiresAt());
    }
}
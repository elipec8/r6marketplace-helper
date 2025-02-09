package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemResaleLockEntityMapperTest {
    @Autowired
    ItemResaleLockEntityMapper itemResaleLockEntityMapper;

    @Test
    public void createDTO_should_Return_expected_result() {
        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        entity.setItem(new ItemEntity("itemId"));
        entity.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        ItemResaleLock result = itemResaleLockEntityMapper.createDTO(entity);

        assertEquals("itemId", result.getItemId());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), result.getExpiresAt());
    }
}
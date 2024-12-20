package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountStatsEntityMapperTest {
    @Autowired
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;
    @MockBean
    private ItemPostgresRepository itemPostgresRepository;

    @Test
    public void createEntities() {
        when(itemPostgresRepository.findAll()).thenReturn(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2"), new ItemEntity("itemId3")));

        UbiAccountStatsEntityDTO dto1 = new UbiAccountStatsEntityDTO("ubiProfileId", 100, List.of("itemId1", "itemId2", "itemId4"));
        UbiAccountStatsEntityDTO dto2 = new UbiAccountStatsEntityDTO("ubiProfileId1", 120, List.of("itemId1", "itemId2", "itemId3"));

        UbiAccountStatsEntity expected1 = new UbiAccountStatsEntity("ubiProfileId", 100, List.of(new ItemEntity("itemId1"),
                new ItemEntity("itemId2")));
        UbiAccountStatsEntity expected2 = new UbiAccountStatsEntity("ubiProfileId1", 120, List.of(new ItemEntity("itemId1"),
                new ItemEntity("itemId2"), new ItemEntity("itemId3")));


        List<UbiAccountStatsEntity> expected = List.of(expected1, expected2);
        List<UbiAccountStatsEntity> actual = ubiAccountStatsEntityMapper.createEntities(List.of(dto1, dto2));

        assertTrue(expected.size() == actual.size() && expected.stream().allMatch(ex -> actual.stream().anyMatch(ac -> ac.isFullyEqual(ex))));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        entity.setUbiProfileId("ubiProfileId");
        entity.setCreditAmount(100);
        entity.setOwnedItems(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2")));

        UbiAccountStatsEntityDTO expected = new UbiAccountStatsEntityDTO("ubiProfileId", 100, List.of("itemId1", "itemId2"));

        assertEquals(expected, ubiAccountStatsEntityMapper.createDTO(entity));
    }
}
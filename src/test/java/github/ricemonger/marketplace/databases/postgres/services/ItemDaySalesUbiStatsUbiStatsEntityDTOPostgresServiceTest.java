package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.ItemDaySalesUbiStatsEntityMapper;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemDaySalesUbiStatsUbiStatsEntityDTOPostgresServiceTest {
    @Autowired
    private ItemDaySalesUbiStatsPostgresService itemDaySalesUbiStatsPostgresService;
    @MockBean
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;
    @MockBean
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Test
    public void saveAll_should_map_and_save_all_dtos() {
        Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList = new ArrayList<>();

        List<ItemDaySalesUbiStatsEntity> mapperResult = new ArrayList<>();
        when(itemDaySalesUbiStatsEntityMapper.createEntities(same(groupedItemDaySalesUbiStatsList))).thenReturn(mapperResult);

        itemDaySalesUbiStatsPostgresService.saveAll(groupedItemDaySalesUbiStatsList);

        verify(itemDaySalesUbiStatsRepository).saveAll(same(mapperResult));
    }

    @Test
    public void findAllForLastMonth_should_map_and_return_all_entities() {
        ItemDaySalesUbiStatsEntity daySaleEntity1 = new ItemDaySalesUbiStatsEntity();
        ItemDaySalesUbiStatsEntity daySaleEntity2 = new ItemDaySalesUbiStatsEntity();
        List<ItemDaySalesUbiStatsEntity> entities = List.of(daySaleEntity1, daySaleEntity2);
        when(itemDaySalesUbiStatsRepository.findAllForLastMonth()).thenReturn(entities);

        ItemDaySalesUbiStats dto1 = new ItemDaySalesUbiStats();
        ItemDaySalesUbiStats dto2 = new ItemDaySalesUbiStats();
        List<ItemDaySalesUbiStats> dtos = List.of(dto1, dto2);

        when(itemDaySalesUbiStatsEntityMapper.createDTO(same(daySaleEntity1))).thenReturn(dto1);
        when(itemDaySalesUbiStatsEntityMapper.createDTO(same(daySaleEntity2))).thenReturn(dto2);

        List<ItemDaySalesUbiStats> result = itemDaySalesUbiStatsPostgresService.findAllForLastMonth();

        assertTrue(result.size() == dtos.size() && result.stream().allMatch(res -> dtos.stream().anyMatch(ex -> ex == res)));
    }

    @Test
    public void findAll_should_map_and_return_all_entities() {
        ItemDaySalesUbiStatsEntity daySaleEntity1 = new ItemDaySalesUbiStatsEntity();
        ItemDaySalesUbiStatsEntity daySaleEntity2 = new ItemDaySalesUbiStatsEntity();
        List<ItemDaySalesUbiStatsEntity> entities = List.of(daySaleEntity1, daySaleEntity2);
        when(itemDaySalesUbiStatsRepository.findAll()).thenReturn(entities);

        ItemDaySalesUbiStats dto1 = new ItemDaySalesUbiStats();
        ItemDaySalesUbiStats dto2 = new ItemDaySalesUbiStats();
        List<ItemDaySalesUbiStats> dtos = List.of(dto1, dto2);

        when(itemDaySalesUbiStatsEntityMapper.createDTO(same(daySaleEntity1))).thenReturn(dto1);
        when(itemDaySalesUbiStatsEntityMapper.createDTO(same(daySaleEntity2))).thenReturn(dto2);

        List<ItemDaySalesUbiStats> result = itemDaySalesUbiStatsPostgresService.findAll();

        assertTrue(result.size() == dtos.size() && result.stream().allMatch(res -> dtos.stream().anyMatch(ex -> ex == res)));
    }
}
package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemHistoryFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemRecalculationRequiredFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemHistoryFieldsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemRecalculationRequiredFieldsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemEntitiesMapper;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {
    @Autowired
    private ItemPostgresService itemPostgresService;
    @MockBean
    private ItemHistoryFieldsPostgresRepository itemHistoryFieldsRepository;
    @MockBean
    private ItemRecalculationRequiredFieldsPostgresRepository itemMainFieldsRepository;
    @MockBean
    private ItemEntitiesMapper itemEntitiesMapper;

    @Test
    public void saveAllHistoryFields_should_save_mapped_entities() {
        ItemHistoryFieldsI dto1 = Mockito.mock(ItemHistoryFieldsI.class);
        ItemHistoryFieldsI dto2 = Mockito.mock(ItemHistoryFieldsI.class);

        ItemHistoryFieldsEntity entity1 = Mockito.mock(ItemHistoryFieldsEntity.class);
        ItemHistoryFieldsEntity entity2 = Mockito.mock(ItemHistoryFieldsEntity.class);

        when(itemEntitiesMapper.createHistoryFieldsEntity(dto1)).thenReturn(entity1);
        when(itemEntitiesMapper.createHistoryFieldsEntity(dto2)).thenReturn(entity2);

        itemPostgresService.saveAllHistoryFields(List.of(dto1, dto2));

        verify(itemHistoryFieldsRepository).saveAll(argThat(iterable -> {
            List arg = (List) iterable;

            return arg.size() == 2 && arg.contains(entity1) && arg.contains(entity2);
        }));
    }

    @Test
    public void findAllRecalculationRequiredFields_should_return_mapped_dtos() {
        ItemRecalculationRequiredFieldsEntity entity1 = Mockito.mock(ItemRecalculationRequiredFieldsEntity.class);
        ItemRecalculationRequiredFieldsEntity entity2 = Mockito.mock(ItemRecalculationRequiredFieldsEntity.class);

        ItemRecalculationRequiredFields dto1 = Mockito.mock(ItemRecalculationRequiredFields.class);
        ItemRecalculationRequiredFields dto2 = Mockito.mock(ItemRecalculationRequiredFields.class);

        when(itemMainFieldsRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(itemEntitiesMapper.createRecalculationRequiredFieldsDTO(entity1)).thenReturn(dto1);
        when(itemEntitiesMapper.createRecalculationRequiredFieldsDTO(entity2)).thenReturn(dto2);

        List<ItemRecalculationRequiredFields> result = itemPostgresService.findAllRecalculationRequiredFields();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}
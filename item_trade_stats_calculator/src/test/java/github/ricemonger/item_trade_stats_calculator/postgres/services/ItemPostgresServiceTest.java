package github.ricemonger.item_trade_stats_calculator.postgres.services;


import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemHistoryFieldsDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemRecalculationRequiredFieldsDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemPostgresRepository;
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
    private ItemPostgresRepository itemPostgresRepository;
    @MockBean
    private ItemEntitiesMapper itemEntitiesMapper;

    @Test
    public void updateAllItemsHistoryFields_should_update_mapped_entitiesItem() {
        ItemHistoryFieldsI dto1 = Mockito.mock(ItemHistoryFieldsI.class);
        ItemHistoryFieldsI dto2 = Mockito.mock(ItemHistoryFieldsI.class);

        ItemHistoryFieldsDtoProjection proj1 = Mockito.mock(ItemHistoryFieldsDtoProjection.class);
        ItemHistoryFieldsDtoProjection proj2 = Mockito.mock(ItemHistoryFieldsDtoProjection.class);

        when(itemEntitiesMapper.createHistoryFieldsDtoProjection(dto1)).thenReturn(proj1);
        when(itemEntitiesMapper.createHistoryFieldsDtoProjection(dto2)).thenReturn(proj2);

        itemPostgresService.updateAllItemsHistoryFields(List.of(dto1, dto2));

        verify(itemPostgresRepository).updateAllItemsHistoryFields(argThat(iterable -> {
            List arg = (List) iterable;

            return arg.size() == 2 && arg.contains(proj1) && arg.contains(proj2);
        }));
    }

    @Test
    public void findAllItemsRecalculationRequiredFields_should_return_mapped_dtos() {
        ItemRecalculationRequiredFieldsDtoProjection proj1 = Mockito.mock(ItemRecalculationRequiredFieldsDtoProjection.class);
        ItemRecalculationRequiredFieldsDtoProjection proj2 = Mockito.mock(ItemRecalculationRequiredFieldsDtoProjection.class);

        ItemRecalculationRequiredFields dto1 = Mockito.mock(ItemRecalculationRequiredFields.class);
        ItemRecalculationRequiredFields dto2 = Mockito.mock(ItemRecalculationRequiredFields.class);

        when(itemPostgresRepository.findAllItemsRecalculationRequiredFields()).thenReturn(List.of(proj1, proj2));
        when(itemEntitiesMapper.createRecalculationRequiredFieldsDTO(proj1)).thenReturn(dto1);
        when(itemEntitiesMapper.createRecalculationRequiredFieldsDTO(proj2)).thenReturn(dto2);

        List<ItemRecalculationRequiredFields> result = itemPostgresService.findAllItemsRecalculationRequiredFields();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}
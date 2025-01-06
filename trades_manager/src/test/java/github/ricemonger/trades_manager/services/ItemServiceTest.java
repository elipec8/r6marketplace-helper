package github.ricemonger.trades_manager.services;

import github.ricemonger.trades_manager.services.abstractions.ItemDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemDatabaseService itemDatabaseService;

    @Test
    public void getAllItems_should_return_service_result() {
        List items = Mockito.mock(ArrayList.class);

        Mockito.when(itemDatabaseService.findAll()).thenReturn(items);

        assertSame(items, itemService.getAllItems());
    }
}
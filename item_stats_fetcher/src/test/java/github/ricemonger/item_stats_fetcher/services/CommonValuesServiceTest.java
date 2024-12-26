package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {

    @Autowired
    private CommonValuesService commonValuesService;

    @MockBean
    private CommonValuesDatabaseService commonValuesDatabaseService;

    @Test
    void getExpectedItemCount_should_return_service_result() {
        int expectedItemCount = 10;
        when(commonValuesDatabaseService.getExpectedItemCount()).thenReturn(expectedItemCount);
        assertEquals(expectedItemCount, commonValuesService.getExpectedItemCount());
    }

    @Test
    void setExpectedItemCount_should_handle_to_service() {
        int expectedItemCount = 10;
        commonValuesService.setExpectedItemCount(expectedItemCount);
        verify(commonValuesDatabaseService).setExpectedItemCount(expectedItemCount);
    }
}
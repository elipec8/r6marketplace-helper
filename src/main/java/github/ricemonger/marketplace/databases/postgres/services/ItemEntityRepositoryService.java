package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryEntityRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemEntityRepositoryService implements ItemRepositoryService {

    private final ItemEntityRepository itemEntityRepository;

    private final ItemSaleEntityRepository itemSaleEntityRepository;

    private final ItemSaleHistoryEntityRepository itemSaleHistoryEntityRepository;

    private final ItemDtoMapper mapper;

    public void saveAll(Collection<Node> nodeDTOs) {
        Set<ItemEntity> entities = mapper.nodesDTOToItemEntities(nodeDTOs);

        Set<ItemSaleEntity> saleEntities = mapper.nodesDTOToItemSaleEntities(nodeDTOs);

        itemEntityRepository.saveAll(entities);
        itemSaleEntityRepository.saveAll(saleEntities);
    }

    public List<ItemEntity> findAll() throws UbiUserEntityDoesntExistException {
        return itemEntityRepository
                .findAll();
    }

    public void calculateItemsSaleHistoryStats() {

        List<ItemSaleHistoryEntity> histories = new ArrayList<>();

        List<ItemEntity> items = itemEntityRepository.findAll();

        List<ItemSaleEntity> sales = itemSaleEntityRepository.findAll();

        for (ItemEntity item : items) {

            ItemSaleHistoryEntity history = new ItemSaleHistoryEntity();
            history.setItemId(item.getItemId());

            List<ItemSaleEntity> itemSales = sales.stream().filter(sale -> sale.getItemId().equals(item.getItemId())).toList();

            if (itemSales.isEmpty()) {
                histories.add(history);
                continue;
            }

            List<ItemSaleEntity> monthSales =
                    itemSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L))).toList();

            int monthAveragePrice = 0;
            int monthMedianPrice = 0;
            int monthMaxPrice = 0;
            int monthMinPrice = 0;
            int monthSalesPerDay = 0;
            int monthLowPriceSalesPerDay = 0;
            int monthHighPriceSalesPerDay = 0;

            if (!monthSales.isEmpty()) {
                monthAveragePrice = (int) monthSales.stream().mapToInt(ItemSaleEntity::getPrice).average().orElse(0);
                int monthLowPriceThreshold = (int) (monthAveragePrice * 0.75);
                int monthHighPriceThreshold = (int) (monthAveragePrice * 1.2);
                monthMedianPrice = monthSales.stream().mapToInt(ItemSaleEntity::getPrice).sorted().boxed().toList().get(monthSales.size() / 2);
                monthMaxPrice = monthSales.stream().mapToInt(ItemSaleEntity::getPrice).max().orElse(0);
                monthMinPrice = monthSales.stream().mapToInt(ItemSaleEntity::getPrice).min().orElse(0);
                monthSalesPerDay = monthSales.size() / 30;
                monthLowPriceSalesPerDay = monthSales.stream().filter(sale -> sale.getPrice() <= monthLowPriceThreshold).toList().size() / 30;
                monthHighPriceSalesPerDay = monthSales.stream().filter(sale -> sale.getPrice() >= monthHighPriceThreshold).toList().size() / 30;
            }

            List<ItemSaleEntity> daySales =
                    monthSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L))).toList();

            int dayAveragePrice = 0;
            int dayMaxPrice = 0;
            int dayMinPrice = 0;
            int dayMedianPrice = 0;
            int daySalesCount = 0;
            int dayLowPriceSalesCount = 0;
            int dayHighPriceSalesCount = 0;

            if (!daySales.isEmpty()) {
                dayAveragePrice = (int) daySales.stream().mapToInt(ItemSaleEntity::getPrice).average().orElse(0);
                int dayLowPriceThreshold = (int) (dayAveragePrice * 0.75);
                int dayHighPriceThreshold = (int) (dayAveragePrice * 1.2);
                dayMaxPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).max().orElse(0);
                dayMinPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).min().orElse(0);
                dayMedianPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).sorted().boxed().toList().get(daySales.size() / 2);
                daySalesCount = daySales.size();
                dayLowPriceSalesCount = (int) daySales.stream().filter(sale -> sale.getPrice() <= dayLowPriceThreshold).count();
                dayHighPriceSalesCount = (int) daySales.stream().filter(sale -> sale.getPrice() >= dayHighPriceThreshold).count();
            }

            history.setMonthAveragePrice(monthAveragePrice);
            history.setMonthMedianPrice(monthMedianPrice);
            history.setMonthMaxPrice(monthMaxPrice);
            history.setMonthMinPrice(monthMinPrice);
            history.setMonthSalesPerDay(monthSalesPerDay);
            history.setMonthLowPriceSalesPerDay(monthLowPriceSalesPerDay);
            history.setMonthHighPriceSalesPerDay(monthHighPriceSalesPerDay);

            history.setDayAveragePrice(dayAveragePrice);
            history.setDayMedianPrice(dayMedianPrice);
            history.setDayMaxPrice(dayMaxPrice);
            history.setDayMinPrice(dayMinPrice);
            history.setDaySales(daySalesCount);
            history.setDayLowPriceSales(dayLowPriceSalesCount);
            history.setDayHighPriceSales(dayHighPriceSalesCount);

            histories.add(history);
        }
        itemSaleHistoryEntityRepository.saveAll(histories);
    }
}

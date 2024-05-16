package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemSaleHistoryRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemSaleRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final static Set<String> IGNORED_NAMES = new HashSet<>();

    static {
        IGNORED_NAMES.add("BLACK ICE");
        IGNORED_NAMES.add("DUST LINE");
        IGNORED_NAMES.add("SKULL RAIN");
        IGNORED_NAMES.add("RED CROW");
        IGNORED_NAMES.add("VELVET SHELL");
        IGNORED_NAMES.add("HEALTH");
        IGNORED_NAMES.add("BLOOD ORCHID");
        IGNORED_NAMES.add("WHITE NOISE");
        IGNORED_NAMES.add("CHIMERA");
        IGNORED_NAMES.add("PARA BELLUM");
        IGNORED_NAMES.add("GRIM SKY");
        IGNORED_NAMES.add("WIND BASTION");
        IGNORED_NAMES.add("BURNT HORIZON");
        IGNORED_NAMES.add("PHANTOM SIGHT");
        IGNORED_NAMES.add("EMBER RISE");
        IGNORED_NAMES.add("SHIFTING TIDES");
        IGNORED_NAMES.add("VOID EDGE");
        IGNORED_NAMES.add("STEEL WAVE");
        IGNORED_NAMES.add("SHADOW LEGACY");
        IGNORED_NAMES.add("NEON DAWN");
        IGNORED_NAMES.add("CRIMSON HEIST");
        IGNORED_NAMES.add("NORTH STAR");
        IGNORED_NAMES.add("CRYSTAL GUARD");
        IGNORED_NAMES.add("HIGH CALIBRE");
        IGNORED_NAMES.add("DEMON VEIL");
        IGNORED_NAMES.add("VECTOR GLARE");
        IGNORED_NAMES.add("BRUTAL SWARM");
        IGNORED_NAMES.add("SOLAR RAID");
        IGNORED_NAMES.add("COMMANDING FORCE");
        IGNORED_NAMES.add("DREAD FACTOR");
        IGNORED_NAMES.add("HEAVY METTLE");
        IGNORED_NAMES.add("DEEP FREEZE");
        IGNORED_NAMES.add("DEADLY OMEN");
    }

    private final ItemRepository itemRepository;

    private final ItemSaleRepository itemSaleRepository;

    private final ItemSaleHistoryRepository itemSaleHistoryRepository;

    private final DTOsToEntityMapper mapper;

    public void saveAll(Collection<Node> nodeDTOs) {
        Set<ItemEntity> entities = mapper.nodesDTOToItemEntities(nodeDTOs);

        Set<ItemSaleEntity> saleEntities = mapper.nodesDTOToItemSaleEntities(nodeDTOs);

        itemRepository.saveAll(entities);
        itemSaleRepository.saveAll(saleEntities);
    }

    public List<ItemEntity> getSpeculativeItemsByExpectedProfit(int minProfitAbsolute, int minProfitPercentOfBuyPrice, int minSellPrice, int maxSellPrice) throws UbiUserEntityDoesntExistException {
        return itemRepository
                .findAll()
                .stream()
                .filter(itemEntity -> !IGNORED_NAMES.contains(itemEntity.getName()))
                .map(itemEntity -> {
                    if (itemEntity.getExpectedProfit() >= minProfitAbsolute &&
                            itemEntity.getExpectedProfitPercentage() >= minProfitPercentOfBuyPrice &&
                            itemEntity.getMinSellPrice() >= minSellPrice &&
                            itemEntity.getMinSellPrice() <= maxSellPrice) {
                        return itemEntity;
                    }
                    return null;
                }).filter(Objects::nonNull)
                .sorted((o1, o2) -> o2.getExpectedProfit() * o2.getExpectedProfitPercentage() * o2.getSellOrders() - o1.getExpectedProfit() * o1.getExpectedProfitPercentage() * o1.getSellOrders()).toList();
    }

    public void calculateItemsSaleStats() {

        List<ItemEntity> items = itemRepository.findAll();

        List<ItemSaleEntity> sales = itemSaleRepository.findAll();

        List<ItemSaleHistoryEntity> histories = new ArrayList<>();

        for(ItemEntity item : items){
            List<ItemSaleEntity> itemSales = sales.stream().filter(sale -> sale.getItemId().equals(item.getItemFullId())).toList();

            if(itemSales.isEmpty()){
                ItemSaleHistoryEntity history = new ItemSaleHistoryEntity();
                history.setItemId(item.getItemFullId());
                histories.add(history);
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

            if(!monthSales.isEmpty()){
                monthAveragePrice = (int )monthSales.stream().mapToInt(ItemSaleEntity::getPrice).average().orElse(0);
                int monthLowPriceThreshold = (int)(monthAveragePrice * 0.75);
                int monthHighPriceThreshold = (int)(monthAveragePrice * 1.2);
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

            if(!daySales.isEmpty()){
                dayAveragePrice = (int) daySales.stream().mapToInt(ItemSaleEntity::getPrice).average().orElse(0);
                int dayLowPriceThreshold = (int)(dayAveragePrice * 0.75);
                int dayHighPriceThreshold = (int)(dayAveragePrice * 1.2);
                dayMaxPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).max().orElse(0);
                dayMinPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).min().orElse(0);
                dayMedianPrice = daySales.stream().mapToInt(ItemSaleEntity::getPrice).sorted().boxed().toList().get(daySales.size() / 2);
                daySalesCount = daySales.size();
                dayLowPriceSalesCount = (int) daySales.stream().filter(sale -> sale.getPrice() <= dayLowPriceThreshold).count();
                dayHighPriceSalesCount = (int) daySales.stream().filter(sale -> sale.getPrice() >= dayHighPriceThreshold).count();
            }

            ItemSaleHistoryEntity history = new ItemSaleHistoryEntity();

            history.setItemId(item.getItemFullId());

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
        itemSaleHistoryRepository.saveAll(histories);
    }
}

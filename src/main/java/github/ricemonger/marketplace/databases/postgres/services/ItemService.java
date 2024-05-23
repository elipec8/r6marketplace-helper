package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemService {

    private final static List<String> IGNORED_NAMES = new ArrayList<>();

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

    private final ItemRepositoryService itemRepositoryService;

    private final GraphQlClientService graphQlClientService;

    private final ItemDtoMapper itemDtoMapper;

    public void saveAll(Collection<Node> nodeDTOs) {
        itemRepositoryService.saveAll(nodeDTOs);
    }

    public List<? extends Item> getAllSpeculativeItemsByExpectedProfit(int minProfitAbsolute, int minProfitPercentOfBuyPrice, int minSellPrice, int maxSellPrice)
            throws UbiUserEntityDoesntExistException {

        return getSpeculativeItemsFromListByExpectedProfit(itemRepositoryService.findAll(), minProfitAbsolute, minProfitPercentOfBuyPrice, minSellPrice, maxSellPrice);
    }

    public List<? extends Item> getOwnedSpeculativeItemsByExpectedProfit(AuthorizationDTO authorizationDTO, int i, int i1, int i2, int i3) {
        Set<? extends Item> items = itemDtoMapper.nodesDTOToItemEntities(graphQlClientService.fetchAllOwnedItemStatsForUser(authorizationDTO));

        return getSpeculativeItemsFromListByExpectedProfit(items, i, i1, i2, i3);
    }

    public List<? extends Item> getSpeculativeItemsFromListByExpectedProfit(Collection<? extends Item> items,
                                                                             int minProfitAbsolute,
                                                                             int minProfitPercentOfBuyPrice,
                                                                             int minSellPrice,
                                                                             int maxSellPrice)
            throws UbiUserEntityDoesntExistException {

        return items.stream()
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
                .sorted((o1, o2) -> o2.getExpectedProfit() * o2.getExpectedProfitPercentage() * o2.getSellOrdersCount() - o1.getExpectedProfit() * o1.getExpectedProfitPercentage() * o1.getSellOrdersCount()).toList();
    }

    public void calculateItemsSaleStats() {
        itemRepositoryService.calculateItemsSaleStats();
    }
}

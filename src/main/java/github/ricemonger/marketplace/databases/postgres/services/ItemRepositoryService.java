package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;

import java.util.Collection;
import java.util.List;

public interface ItemRepositoryService {

    void saveAll(Collection<Node> nodeDTOs);

    List<? extends Item> findAll();

    void calculateItemsSaleStats();
}

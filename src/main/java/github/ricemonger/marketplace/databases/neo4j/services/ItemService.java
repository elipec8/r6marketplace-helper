package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final DTOsToEntityMapper mapper;

    public void saveAll(List<Node> nodeDTOs) {
        List<ItemEntity> entities = mapper.NodesDTOToItemEntities(nodeDTOs);
        itemRepository.saveAll(entities);
    }

}

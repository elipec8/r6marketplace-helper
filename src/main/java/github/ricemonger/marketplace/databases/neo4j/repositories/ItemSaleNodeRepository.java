package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemSaleNodeRepository extends Neo4jRepository<ItemSaleNode, String>{

}


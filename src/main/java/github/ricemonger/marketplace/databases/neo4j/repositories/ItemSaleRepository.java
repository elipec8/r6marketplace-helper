package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemSaleEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ItemSaleRepository extends Neo4jRepository<ItemSaleEntity, String>{

}


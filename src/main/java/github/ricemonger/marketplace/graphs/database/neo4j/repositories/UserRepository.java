package github.ricemonger.marketplace.graphs.database.neo4j.repositories;

import github.ricemonger.marketplace.graphs.database.neo4j.entities.UbiUserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<UbiUserEntity, String> {
}

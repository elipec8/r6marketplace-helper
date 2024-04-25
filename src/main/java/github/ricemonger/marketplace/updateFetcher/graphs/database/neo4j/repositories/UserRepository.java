package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.repositories;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<UserEntity, String> {
}

package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.databases.postgres.mappers.TagPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.dtos.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TagPostgresService implements TagDatabaseService {

    private final TagPostgresRepository tagPostgresRepository;

    private final TagPostgresMapper mapper;

    public void saveAll(Collection<Tag> tags) {
        tagPostgresRepository.saveAll(mapper.mapEntities(tags));
    }
}

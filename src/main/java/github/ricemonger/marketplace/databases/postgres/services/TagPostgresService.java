package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
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

    private final TagPostgresRepository repository;

    private final TagPostgresMapper mapper;

    @Override
    public void saveAll(Collection<Tag> tags) {
        repository.saveAll(mapper.mapTagEntities(tags));
    }

    @Override
    public Collection<Tag> findAll() {
        return mapper.mapTags(repository.findAll());
    }

    @Override
    public Tag findByName(String name) {
        return mapper.mapTag(repository.findByName(name).orElse(new TagEntity()));
    }

    @Override
    public Tag findByValue(String value) {
        return mapper.mapTag(repository.findById(value).orElse(new TagEntity()));
    }
}

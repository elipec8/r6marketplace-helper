package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
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

    @Override
    public void saveAll(Collection<Tag> tags) {
        repository.saveAll(tags.stream().map(TagEntity::new).toList());
    }

    @Override
    public Collection<Tag> findAllByNames(Collection<String> tagNames) {
        return repository.findAllByNames(tagNames).stream().map(TagEntity::toTag).toList();
    }

    @Override
    public Collection<Tag> findAll() {
        return repository.findAll().stream().map(TagEntity::toTag).toList();
    }
}

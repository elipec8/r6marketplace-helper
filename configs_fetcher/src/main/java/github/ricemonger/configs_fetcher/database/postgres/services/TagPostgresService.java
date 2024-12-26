package github.ricemonger.configs_fetcher.database.postgres.services;

import github.ricemonger.configs_fetcher.database.postgres.repositories.TagPostgresRepository;
import github.ricemonger.configs_fetcher.database.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.configs_fetcher.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TagPostgresService implements TagDatabaseService {

    private final TagPostgresRepository tagRepository;

    private final TagEntityMapper tagEntityMapper;

    @Override
    @Transactional
    public void saveAll(Collection<Tag> tags) {
        tagRepository.saveAll(tags.stream().map(tagEntityMapper::createEntity).toList());
    }
}

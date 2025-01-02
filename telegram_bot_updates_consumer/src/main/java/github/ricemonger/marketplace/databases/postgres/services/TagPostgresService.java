package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagPostgresService implements TagDatabaseService {

    private final TagPostgresPostgresRepository tagRepository;

    private final TagEntityMapper tagEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAllByNames(Collection<String> tagNames) {
        return tagRepository.findAllByNames(tagNames).stream().map(tagEntityMapper::createDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAllByTagGroup(TagGroup tagGroup) {
        return tagRepository.findAllByTagGroup(tagGroup).stream().map(tagEntityMapper::createDTO).toList();
    }
}

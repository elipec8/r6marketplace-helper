package github.ricemonger.marketplace.graphs.database.neo4j.services;

import github.ricemonger.marketplace.graphs.database.neo4j.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AesPasswordEncoder AESPasswordEncoder;

}

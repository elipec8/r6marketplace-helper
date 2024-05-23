package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import github.ricemonger.marketplace.databases.neo4j.repositories.UbiUserNodeRepository;
import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UbiUserService {

    private final UbiUserNodeRepository ubiUserNodeRepository;

    private final AesPasswordEncoder AESPasswordEncoder;

    private final AuthorizationService authorizationService;

    public void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String emailToRemove) {
        ubiUserNodeRepository.deleteByLinkedTelegramUserChatIdAndEmail(chatId, emailToRemove);
    }

    public void deleteAllByLinkedTelegramUserChatId(String chatId) {
        ubiUserNodeRepository.deleteAllByLinkedTelegramUserChatId(chatId);
    }

    public List<UbiUserNode> reauthorizeAllUbiUsersAndGetUnauthorizedList(){
        List<UbiUserNode> entities = ubiUserNodeRepository.findAll();

        List<UbiUserNode> notifyList = new ArrayList<>();

        for(UbiUserNode entity: entities){
            try {
                reauthorizeUserOrThrow(entity);
            }
            catch(UbiUserAuthorizationClientErrorException e){
                notifyList.add(entity);
            }
        }

        return notifyList;
    }

    public void reauthorizeUserOrThrow(UbiUserNode entity) {
            AuthorizationDTO authorizationDTO = authorizationService.getUserAuthorizationDTO(entity.getEmail(), AESPasswordEncoder.decode(entity.getPassword()));

            entity.setUbiProfileId(authorizationDTO.getProfileId());
            entity.setUbiSessionId(authorizationDTO.getSessionId());
            entity.setUbiAuthTicket(authorizationDTO.getTicket());
            entity.setUbiSpaceId(authorizationDTO.getSpaceId());
            entity.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
            entity.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
            entity.setUbiTwoFactorAuthTicket(authorizationDTO.getTwoFactorAuthenticationTicket());

            ubiUserNodeRepository.save(entity);
    }

    public void createAndAuthorizeOrThrowForTelegramUser(TelegramLinkedUserNode telegramLinkedUserNode, String email, String password) throws UbiUserAuthorizationClientErrorException {
        UbiUserNode ubiUserNode = authorizeAndGetUbiUser(email, password);

        ubiUserNode.setLinkedTelegramUser(telegramLinkedUserNode);

        ubiUserNodeRepository.save(ubiUserNode);
    }

    private UbiUserNode authorizeAndGetUbiUser(String email, String password) throws UbiUserAuthorizationClientErrorException {
        UbiUserNode ubiUserNode = new UbiUserNode();

        AuthorizationDTO userAuthorizationDTO = authorizationService.getUserAuthorizationDTO(email, password);
        ubiUserNode.setUbiProfileId(userAuthorizationDTO.getProfileId());
        ubiUserNode.setUbiSessionId(userAuthorizationDTO.getSessionId());
        ubiUserNode.setUbiAuthTicket(userAuthorizationDTO.getTicket());
        ubiUserNode.setUbiSpaceId(userAuthorizationDTO.getSpaceId());
        ubiUserNode.setUbiRememberMeTicket(userAuthorizationDTO.getRememberMeTicket());
        ubiUserNode.setUbiRememberDeviceTicket(userAuthorizationDTO.getRememberDeviceTicket());
        ubiUserNode.setUbiTwoFactorAuthTicket(userAuthorizationDTO.getTwoFactorAuthenticationTicket());

        ubiUserNode.setEmail(email);
        ubiUserNode.setPassword(AESPasswordEncoder.encode(password));

        return ubiUserNode;
    }
}

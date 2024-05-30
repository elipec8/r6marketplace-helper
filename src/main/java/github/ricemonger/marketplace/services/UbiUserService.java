package github.ricemonger.marketplace.services;

import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.postgres.services.AesPasswordEncoder;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiUserService {

    private final UbiUserRepositoryService ubiUserRepositoryService;

    private final AesPasswordEncoder AESPasswordEncoder;

    private final AuthorizationService authorizationService;

    public void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String email) {
        ubiUserRepositoryService.deleteById(chatId, email);
    }

    public Collection<UbiUser> findAllByLinkedTelegramUserChatId(String chatId) {
        return ubiUserRepositoryService.findAllByChatId(chatId);
    }

    public void deleteAllByLinkedTelegramUserChatId(String chatId) {
        ubiUserRepositoryService.deleteAllByChatId(chatId);
    }

    public Collection<UbiUser> reauthorizeAllUbiUsersAndGetUnauthorizedList(){
        List<UbiUser> users = new ArrayList<>(ubiUserRepositoryService.findAll());

        List<UbiUser> unauthorizedUsers = new ArrayList<>();

        for(UbiUser user: users){
            try {
                reauthorizeUserOrThrow(user);
            }
            catch(UbiUserAuthorizationClientErrorException e){
                unauthorizedUsers.add(user);
            }
        }

        return unauthorizedUsers;
    }

    public AuthorizationDTO getAuthorizationDTOFromDbOrThrow(String chatId, String email) throws UbiUserAuthorizationClientErrorException{
        UbiUser user = ubiUserRepositoryService.findById(chatId, email);

        return buildAuthorizationDTO(user);
    }

    public void reauthorizeUserOrThrow(UbiUser user) {
        AuthorizationDTO authorizationDTO = authorizationService.getUserAuthorizationDTO(user.getEmail(), AESPasswordEncoder.decode(user.getPassword()));

        buildUbiUser(authorizationDTO, user.getEmail(), AESPasswordEncoder.decode(user.getPassword()));

        ubiUserRepositoryService.save(user);
    }

    public void createAndAuthorizeOrThrowForTelegramUser(String chatId, String email, String password) throws UbiUserAuthorizationClientErrorException {
        UbiUser user = authorizeAndGetUbiUser(email, password);

        user.setChatId(chatId);

        ubiUserRepositoryService.save(user);
    }

    private UbiUser authorizeAndGetUbiUser(String email, String password) throws UbiUserAuthorizationClientErrorException {
        UbiUser user = new UbiUser();

        AuthorizationDTO userAuthorizationDTO = authorizationService.getUserAuthorizationDTO(email, password);
        buildUbiUser(userAuthorizationDTO, email, password);

        return user;
    }

    private AuthorizationDTO buildAuthorizationDTO(UbiUser user) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId(user.getUbiProfileId());
        authorizationDTO.setSessionId(user.getUbiSessionId());
        authorizationDTO.setTicket(user.getUbiAuthTicket());
        authorizationDTO.setSpaceId(user.getUbiSpaceId());
        authorizationDTO.setRememberMeTicket(user.getUbiRememberMeTicket());
        authorizationDTO.setRememberDeviceTicket(user.getUbiRememberDeviceTicket());
        authorizationDTO.setTwoFactorAuthenticationTicket(user.getUbiTwoFactorAuthTicket());
        return authorizationDTO;
    }

    private UbiUser buildUbiUser(AuthorizationDTO authorizationDTO, String email, String password) {
        UbiUser user = new UbiUser();
        user.setUbiProfileId(authorizationDTO.getProfileId());
        user.setUbiSessionId(authorizationDTO.getSessionId());
        user.setUbiAuthTicket(authorizationDTO.getTicket());
        user.setUbiSpaceId(authorizationDTO.getSpaceId());
        user.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
        user.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
        user.setUbiTwoFactorAuthTicket(authorizationDTO.getTwoFactorAuthenticationTicket());

        user.setEmail(email);
        user.setPassword(AESPasswordEncoder.encode(password));
        return user;
    }

    public Collection<String> getOwnedItemsIds(String chatId, String email) {
        return ubiUserRepositoryService.getOwnedItemsIds(chatId, email);
    }
}

package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.CommonValuesDatabaseService;
import github.ricemonger.telegramBot.TelegramBotConfiguration;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.dtos.ConfigTrades;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    private final MainUserConfiguration mainUserConfiguration;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final TelegramBotConfiguration telegramBotConfiguration;

    public int getExpectedItemCount() {
        return commonValuesDatabaseService.getExpectedItemCount();
    }

    public void setExpectedItemCount(int newItemsAmount) {
        commonValuesDatabaseService.setExpectedItemCount(newItemsAmount);
    }

    public ConfigResolvedTransactionPeriod getConfigResolvedTransactionPeriod() {
        return commonValuesDatabaseService.getConfigResolvedTransactionPeriod();
    }

    public void setConfigResolvedTransactionPeriod(ConfigResolvedTransactionPeriod configResolvedTransactionPeriod) {
        commonValuesDatabaseService.setConfigResolvedTransactionPeriod(configResolvedTransactionPeriod);
    }

    public ConfigTrades getConfigTrades() {
        return commonValuesDatabaseService.getConfigTrades();
    }

    public void setConfigTrades(ConfigTrades configTrades) {
        commonValuesDatabaseService.setConfigTrades(configTrades);
    }

    public String getPaymentItemId() {
        return commonValuesDatabaseService.getPaymentItemId();
    }

    public String getMainUserAuthorizationToken() {
        return commonValuesDatabaseService.getMainUserAuthorizationToken();
    }

    public String getMainUserProfileId() {
        return commonValuesDatabaseService.getMainUserProfileId();
    }

    public String getMainUserSessionId() {
        return commonValuesDatabaseService.getMainUserSessionId();
    }

    public String getMainUserRememberMeTicket() {
        return commonValuesDatabaseService.getMainUserRememberMeTicket();
    }

    public void setMainUserAuthorization(AuthorizationDTO dto) {
        int expireTimeout = ubiServiceConfiguration.getExpireTimeout();
        commonValuesDatabaseService.setMainUserAuthorization(dto, expireTimeout);
    }

    public String getMainUserEmail() {
        return mainUserConfiguration.getEmail();
    }

    public String getMainUserPassword() {
        return mainUserConfiguration.getPassword();
    }

    public String getMainUserPlatform() {
        return mainUserConfiguration.getPlatform();
    }

    public String getGraphqlUrl() {
        return ubiServiceConfiguration.getGraphqlUrl();
    }

    public String getAuthorizationUrl() {
        return ubiServiceConfiguration.getAuthorizationUrl();
    }

    public String getContentType() {
        return ubiServiceConfiguration.getContentType();
    }

    public String getUserAgent() {
        return ubiServiceConfiguration.getUserAgent();
    }

    public String getUbiAppId() {
        return ubiServiceConfiguration.getUbiAppId();
    }

    public String getUbiGameSpaceId() {
        return ubiServiceConfiguration.getUbiSpaceId();
    }

    public String getUbiRegionId() {
        return ubiServiceConfiguration.getUbiRegionId();
    }

    public String getUbiLocaleCode() {
        return ubiServiceConfiguration.getUbiLocaleCode();
    }

    public Integer getExpireTimeout() {
        return ubiServiceConfiguration.getExpireTimeout();
    }

    public String getDateFormat() {
        return ubiServiceConfiguration.getDateFormat();
    }

    public Integer getMinimumUncommonPrice(){
        return ubiServiceConfiguration.getMinUncommonPrice();
    }

    public Integer getMaximumUncommonPrice(){
        return ubiServiceConfiguration.getMaxUncommonPrice();
    }

    public Integer getMinimumRarePrice(){
        return ubiServiceConfiguration.getMinRarePrice();
    }

    public Integer getMaximumRarePrice(){
        return ubiServiceConfiguration.getMaxRarePrice();
    }

    public Integer getMinimumEpicPrice(){
        return ubiServiceConfiguration.getMinEpicPrice();
    }

    public Integer getMaximumEpicPrice(){
        return ubiServiceConfiguration.getMaxEpicPrice();
    }

    public Integer getMinimumLegendaryPrice() {
        return ubiServiceConfiguration.getMinLegendaryPrice();
    }

    public Integer getMaximumLegendaryPrice() {
        return ubiServiceConfiguration.getMaxLegendaryPrice();
    }

    public Integer getMinimumMarketplacePrice() {
        return Math.min(ubiServiceConfiguration.getMinUncommonPrice(), Math.min(ubiServiceConfiguration.getMinRarePrice(), Math.min(ubiServiceConfiguration.getMinEpicPrice(), ubiServiceConfiguration.getMinLegendaryPrice())));
    }

    public Integer getMaximumMarketplacePrice() {
        return Math.max(ubiServiceConfiguration.getMaxUncommonPrice(), Math.max(ubiServiceConfiguration.getMaxRarePrice(), Math.max(ubiServiceConfiguration.getMaxEpicPrice(), ubiServiceConfiguration.getMaxLegendaryPrice())));
    }

    public Integer getMaximumTelegramMessageHeight() {
        return telegramBotConfiguration.getMessageHeight();
    }

    public Integer getMaximumTelegramMessageLimit() {
        return telegramBotConfiguration.getMessageLimit();
    }
}

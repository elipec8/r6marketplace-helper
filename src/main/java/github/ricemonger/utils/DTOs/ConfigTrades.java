package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigTrades {
    private int saleExpiresAfterMinutes;
    private int buySlots;
    private int sellSlots;
    private int buyLimit;
    private int sellLimit;
    private int resaleLockDurationInMinutes;
    private String paymentItemId;
    private int feePercentage;
    private boolean twoFactorAuthenticationRule;
    private boolean gameOwnershipRule;
}

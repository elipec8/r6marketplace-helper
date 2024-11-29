package github.ricemonger.marketplace.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfitCalculator {

    private final PriceCalculator priceCalculator;


}

package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.items.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfitCalculator {

    private final PriceCalculator priceCalculator;



}

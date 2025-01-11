package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemFancyPriceCalculator {

    private final CalculatorsCommonValuesService commonValuesService;

    public int getNextFancySellPrice(Item item) {
        int limitMaxPrice = commonValuesService.getMaximumPriceByRarity(item.getRarity());

        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (item.getMinSellPrice() == null || item.getMinSellPrice() <= limitMinPrice) {
            return limitMaxPrice;
        } else {
            return Math.max(limitMinPrice, item.getMinSellPrice() - 1);
        }
    }

    public int getPrevFancyBuyPriceByMaxBuyPrice(Item item) {
        return getPrevFancyBuyPrice(item, item.getMaxBuyPrice());
    }

    public int getPrevFancyBuyPrice(Item item, Integer buyPrice) {
        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (buyPrice == null || buyPrice <= limitMinPrice) {
            return limitMinPrice;
        } else {
            if (buyPrice < 200) {
                return ((buyPrice - 10) / 10) * 10;
            } else if (buyPrice < 1000) {
                return ((buyPrice - 50) / 50) * 50;
            } else if (buyPrice < 3000) {
                return ((buyPrice - 100) / 100) * 100;
            } else if (buyPrice < 10_000) {
                return ((buyPrice - 500) / 500) * 500;
            } else if (buyPrice < 50_000) {
                return ((buyPrice - 1000) / 1000) * 1000;
            } else if (buyPrice < 200_000) {
                return ((buyPrice - 5000) / 5000) * 5000;
            } else {
                return ((buyPrice - 10_000) / 10_000) * 10_000;
            }
        }
    }

    public int getCurrentFancyBuyPrice(Item item, Integer buyPrice) {
        Integer sellPrice = item.getMinSellPrice();
        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (buyPrice == null || buyPrice <= limitMinPrice) {
            return limitMinPrice;
        } else if (sellPrice != null && sellPrice >= 0) {
            if (sellPrice < 200) {
                if (buyPrice % 10 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 10) / 10) * 10;
                }
            } else if (sellPrice < 1000) {
                if (buyPrice % 50 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 50) / 50) * 50;
                }
            } else if (sellPrice < 3000) {
                if (buyPrice % 100 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 100) / 100) * 100;
                }
            } else if (sellPrice < 10_000) {
                if (buyPrice % 500 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 500) / 500) * 500;
                }
            } else if (sellPrice < 50_000) {
                if (buyPrice % 1000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 1000) / 1000) * 1000;
                }
            } else if (sellPrice < 200_000) {
                if (buyPrice % 5000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 5000) / 5000) * 5000;
                }
            } else {
                if (buyPrice % 10_000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 10_000) / 10_000) * 10_000;
                }
            }
        } else {
            if (buyPrice < 200) {
                if (buyPrice % 10 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 10) / 10) * 10;
                }
            } else if (buyPrice < 1000) {
                if (buyPrice % 50 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 50) / 50) * 50;
                }
            } else if (buyPrice < 3000) {
                if (buyPrice % 100 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 100) / 100) * 100;
                }
            } else if (buyPrice < 10_000) {
                if (buyPrice % 500 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 500) / 500) * 500;
                }
            } else if (buyPrice < 50_000) {
                if (buyPrice % 1000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 1000) / 1000) * 1000;
                }
            } else if (buyPrice < 200_000) {
                if (buyPrice % 5000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 5000) / 5000) * 5000;
                }
            } else {
                if (buyPrice % 10_000 == 0) {
                    return buyPrice;
                } else {
                    return ((buyPrice + 10_000) / 10_000) * 10_000;
                }
            }
        }
    }

    private int getNextFancyBuyPrice(Item item, Integer buyPrice) {
        Integer sellPrice = item.getMinSellPrice();
        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (buyPrice == null || buyPrice <= 0) {
            return limitMinPrice;
        } else if (sellPrice != null && sellPrice >= 0) {
            if (sellPrice < 200) {
                return ((buyPrice + 10) / 10) * 10;
            } else if (sellPrice < 1000) {
                return ((buyPrice + 50) / 50) * 50;
            } else if (sellPrice < 3000) {
                return ((buyPrice + 100) / 100) * 100;
            } else if (sellPrice < 10_000) {
                return ((buyPrice + 500) / 500) * 500;
            } else if (sellPrice < 50_000) {
                return ((buyPrice + 1000) / 1000) * 1000;
            } else if (sellPrice < 200_000) {
                return ((buyPrice + 5000) / 5000) * 5000;
            } else {
                return ((buyPrice + 10_000) / 10_000) * 10_000;
            }
        } else {
            if (buyPrice < 200) {
                return ((buyPrice + 10) / 10) * 10;
            } else if (buyPrice < 1000) {
                return ((buyPrice + 50) / 50) * 50;
            } else if (buyPrice < 3000) {
                return ((buyPrice + 100) / 100) * 100;
            } else if (buyPrice < 10_000) {
                return ((buyPrice + 500) / 500) * 500;
            } else if (buyPrice < 50_000) {
                return ((buyPrice + 1000) / 1000) * 1000;
            } else if (buyPrice < 200_000) {
                return ((buyPrice + 5000) / 5000) * 5000;
            } else {
                return ((buyPrice + 10_000) / 10_000) * 10_000;
            }
        }
    }
}

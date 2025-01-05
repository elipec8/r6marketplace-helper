package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradePriceAndTimeStats;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class PotentialTradeStatsCalculator {
    public static final int MINUTES_IN_A_MONTH = 43200;
    public static final int MINUTES_IN_A_WEEK = 10080;
    public static final int MINUTES_IN_A_DAY = 1440;
    public static final int MINUTES_IN_6_HOURS = 360;
    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final int TRADE_MANAGER_FIXED_RATE_MINUTES = 1;

    private final CommonValuesService commonValuesService;

    public PotentialTradeStats calculatePotentialSellTradeStatsByMaxBuyPrice(Item item) {
        return calculateSellTradeStats(item, item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES);
    }

    public PotentialTradeStats calculatePotentialSellTradeStatsByNextFancySellPrice(Item item) {
        PotentialTradePriceAndTimeStats priceAndTime = calculatePriceAndTimeForNextFancySellPriceSale(item);
        return calculateSellTradeStats(item, priceAndTime.price(), priceAndTime.time());
    }

    public PotentialTradeStats calculatePotentialBuyTradeStatsByMinSellPrice(Item item) {
        return calculateBuyTradeStats(item, item.getMinSellPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES);
    }

    public PotentialTradeStats calculatePotentialBuyTradeStatsForTime(Item item, Collection<ItemDaySalesStatsByItemId> resultingPerDayStats, Integer minutesToBuy) {
        TreeMap<Integer, Integer> sortedMonthPricesAndQuantities = new TreeMap<>(Comparator.naturalOrder());
        for (ItemDaySalesStatsByItemId dayStat : resultingPerDayStats) {
            for (Map.Entry<Integer, Integer> priceAndQuantity : dayStat.getPriceAndQuantity().entrySet()) {
                sortedMonthPricesAndQuantities.put(priceAndQuantity.getKey(), sortedMonthPricesAndQuantities.getOrDefault(priceAndQuantity.getKey(), 0) + priceAndQuantity.getValue());
            }
        }

        int basicRequiredSalesAmount = MINUTES_IN_A_MONTH / minutesToBuy;

        Iterator<Map.Entry<Integer, Integer>> iterator = sortedMonthPricesAndQuantities.entrySet().iterator();

        int totalAmount = 0;

        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();

            int price = entry.getKey();
            int quantity = entry.getValue();

            totalAmount += quantity;

            if (basicRequiredSalesAmount + getSameOrHigherPricesBuyOrdersAmount(item, price) <= totalAmount) {
                return calculateBuyTradeStats(item, getCurrentFancyBuyPrice(item, price), minutesToBuy);
            }
        }

        return calculateBuyTradeStats(item, null, minutesToBuy);
    }

    public PotentialTradeStats calculatePotentialSellTradeStatsForExistingTrade(UbiTrade existingTrade) {
        return calculateSellTradeStats(existingTrade.getItem(), existingTrade.getProposedPaymentPrice(),
                getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade));
    }

    public PotentialTradeStats calculatePotentialBuyTradeStatsForExistingTrade(UbiTrade existingTrade) {
        return calculateBuyTradeStats(existingTrade.getItem(), existingTrade.getProposedPaymentPrice(),
                getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade));
    }

    public Integer getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(UbiTrade ubiTrade) {
        int minutesTradeExists = (int) Duration.between(ubiTrade.getLastModifiedAt(), LocalDateTime.now()).toMinutes();

        Integer prognosedTradeSuccessMinutes = getPrognosedTradeSuccessMinutesByPriceOrNull(ubiTrade.getItem(), ubiTrade.getProposedPaymentPrice(), ubiTrade.getCategory());

        if (prognosedTradeSuccessMinutes != null) {
            return Math.max(prognosedTradeSuccessMinutes - minutesTradeExists, TRADE_MANAGER_FIXED_RATE_MINUTES);
        } else {
            return null;
        }
    }

    public Integer getPrognosedTradeSuccessMinutesByPriceOrNull(Item item, Integer proposedPaymentPrice, TradeCategory category) {
        if (category == TradeCategory.Buy) {
            if (item.getMinSellPrice() != null && proposedPaymentPrice >= item.getMinSellPrice()) {
                return TRADE_MANAGER_FIXED_RATE_MINUTES;
            } else if (item.getPriceToBuyIn1Hour() != null && proposedPaymentPrice >= item.getPriceToBuyIn1Hour()) {
                return MINUTES_IN_AN_HOUR;
            } else if (item.getPriceToBuyIn6Hours() != null && proposedPaymentPrice >= item.getPriceToBuyIn6Hours()) {
                return MINUTES_IN_6_HOURS;
            } else if (item.getPriceToBuyIn24Hours() != null && proposedPaymentPrice >= item.getPriceToBuyIn24Hours()) {
                return MINUTES_IN_A_DAY;
            } else if (item.getPriceToBuyIn168Hours() != null && proposedPaymentPrice >= item.getPriceToBuyIn168Hours()) {
                return MINUTES_IN_A_WEEK;
            } else if (item.getPriceToBuyIn720Hours() != null && proposedPaymentPrice >= item.getPriceToBuyIn720Hours()) {
                return MINUTES_IN_A_MONTH;
            } else {
                return null;
            }
        } else if (category == TradeCategory.Sell) {
            if (item.getMaxBuyPrice() != null && proposedPaymentPrice <= item.getMaxBuyPrice()) {
                return TRADE_MANAGER_FIXED_RATE_MINUTES;
            } else {
                int monthSalesPerDay = item.getMonthSalesPerDay() == null || item.getMonthSalesPerDay() <= 0 ? 1 : item.getMonthSalesPerDay();
                return MINUTES_IN_A_DAY / monthSalesPerDay;
            }
        } else {
            log.error("getPrognosedTradeSuccessMinutes:Trade category is Unknown or null for itemId: {}, proposedPrice: {} and tradeCategory: {} ",
                    item, proposedPaymentPrice, category);
            return null;
        }
    }


    public PotentialTradeStats calculateSellTradeStats(Item item, Integer price, Integer minutesToTrade) {
        if (price != null && price > 0) {
            if (item.getMinSellPrice() != null && item.getMinSellPrice() < price) {
                return new PotentialTradeStats(price, minutesToTrade, Long.MIN_VALUE);
            } else {
                int monthMedianPrice = item.getMonthMedianPrice() == null ? 0 : item.getMonthMedianPrice();

                if (minutesToTrade == null) {
                    return new PotentialTradeStats(price, null, null);
                }

                long tradePriority = getPriceFactor(price, 0.5) *
                                     getPriceDifferenceFactor(price, monthMedianPrice, 1) *
                                     getPriceRatioFactorPercent(price, monthMedianPrice, 1) *
                                     getTimeFactor(minutesToTrade, 0.66);

                if (price < monthMedianPrice) {
                    tradePriority = -tradePriority;
                }

                return new PotentialTradeStats(price, minutesToTrade, tradePriority);
            }
        } else {
            return new PotentialTradeStats(price, minutesToTrade, null);
        }
    }


    public PotentialTradeStats calculateBuyTradeStats(Item item, Integer price, Integer minutesToTrade) {
        long constant = commonValuesService.getMaximumMarketplacePrice();
        if (price != null && price > 0) {

            int monthMedianPrice = item.getMonthMedianPrice() == null ? 0 : item.getMonthMedianPrice();

            if (minutesToTrade == null) {
                return new PotentialTradeStats(price, null, null);
            }

            long tradePriority = (constant / getPriceFactor(price, 1.0)) *
                                 getPriceDifferenceFactor(price, monthMedianPrice, 1) *
                                 getPriceRatioFactorPercent(price, monthMedianPrice, 1) *
                                 getTimeToResellFactor(item, 1) *
                                 getTimeFactor(minutesToTrade, 0.4);

            if (item.getMonthMedianPrice() != null && price > monthMedianPrice) {
                tradePriority = -tradePriority;
            }

            return new PotentialTradeStats(price, minutesToTrade, tradePriority);
        } else {
            return new PotentialTradeStats(price, minutesToTrade, null);
        }
    }

    public int getSameOrHigherPricesBuyOrdersAmount(Item item, int price) {
        int currentBuyers = item.getBuyOrdersCount();
        // buyers prices proportions: 1% - maxBuyPrice
        // 4% - prevFancyPrice
        // 10% - maxBuyPrice * 0.66
        // 15% - maxBuyPrice * 0.5
        // 70% - limitMinPrice
        int maxBuyPrice = item.getMaxBuyPrice();

        float percentOfSameOrHigherPricesBuyOrdersAmount;

        if (price <= commonValuesService.getMinimumPriceByRarity(item.getRarity())) {
            percentOfSameOrHigherPricesBuyOrdersAmount = 1.0f;
        } else if (price <= maxBuyPrice * 0.5) {
            percentOfSameOrHigherPricesBuyOrdersAmount = 0.3f;
        } else if (price <= maxBuyPrice * 0.66) {
            percentOfSameOrHigherPricesBuyOrdersAmount = 0.15f;
        } else if (price <= getPrevFancyBuyPriceByMaxBuyPrice(item)) {
            percentOfSameOrHigherPricesBuyOrdersAmount = 0.05f;
        } else if (price <= maxBuyPrice) {
            percentOfSameOrHigherPricesBuyOrdersAmount = 0.01f;
        } else {
            percentOfSameOrHigherPricesBuyOrdersAmount = 0.0f;
        }

        return Math.round(currentBuyers * percentOfSameOrHigherPricesBuyOrdersAmount);
    }

    public PotentialTradePriceAndTimeStats calculatePriceAndTimeForNextFancySellPriceSale(Item item) {
        int monthSalesPerDay = item.getMonthSalesPerDay() == null || item.getMonthSalesPerDay() <= 0 ? 1 : item.getMonthSalesPerDay();
        int nextFancySellPrice = getNextFancySellPrice(item);
        int timeToSellByNextFancySellPrice;
        if (item.getMinSellPrice() != null && item.getMinSellPrice() == commonValuesService.getMinimumPriceByRarity(item.getRarity())) {
            int sellOrdersCount = item.getSellOrdersCount() == null || item.getSellOrdersCount() <= 0 ? 1 : item.getSellOrdersCount();
            timeToSellByNextFancySellPrice = sellOrdersCount * MINUTES_IN_A_DAY / monthSalesPerDay;
        } else {
            timeToSellByNextFancySellPrice = MINUTES_IN_A_DAY / monthSalesPerDay;
        }

        return new PotentialTradePriceAndTimeStats(nextFancySellPrice, timeToSellByNextFancySellPrice);
    }

    private long getPriceFactor(int price, double pow) {
        return (long) Math.pow(price, pow);
    }

    private long getPriceDifferenceFactor(int price, int medianPrice, double pow) {
        if (medianPrice == 0) {
            return 1;
        }

        long result = (long) Math.pow(price - medianPrice, pow);

        return Math.max(Math.abs(result), 1);
    }

    private long getPriceRatioFactorPercent(int price, int medianPrice, double pow) {
        if (medianPrice == 0) {
            return 1;
        }

        int differenceInPercent = ((price - medianPrice) * 100) / medianPrice;

        long result = (long) Math.pow(differenceInPercent, pow);

        return Math.max(Math.abs(result), 1);
    }

    private long getTimeToResellFactor(Item item, int pow) {
        int sales = item.getMonthSales() == null ? 0 : Math.max(item.getMonthSales(), 0);

        return (long) Math.pow(sales, pow);
    }

    private long getTimeFactor(int minutesToTrade, double pow) {
        minutesToTrade = Math.max(minutesToTrade, TRADE_MANAGER_FIXED_RATE_MINUTES);

        return MINUTES_IN_A_MONTH / (long) (Math.pow(minutesToTrade, pow));
    }

    private int getNextFancySellPrice(Item item) {
        int limitMaxPrice = commonValuesService.getMaximumPriceByRarity(item.getRarity());

        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (item.getMinSellPrice() == null || item.getMinSellPrice() <= 0) {
            return limitMaxPrice;
        } else {
            return Math.max(limitMinPrice, item.getMinSellPrice() - 1);
        }
    }

    private int getPrevFancyBuyPriceByMaxBuyPrice(Item item) {
        return getPrevFancyBuyPrice(item, item.getMaxBuyPrice());
    }

    private int getPrevFancyBuyPrice(Item item, Integer buyPrice) {
        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (buyPrice == null || buyPrice <= 0) {
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

    private int getCurrentFancyBuyPrice(Item item, Integer buyPrice) {
        Integer sellPrice = item.getMinSellPrice();
        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (buyPrice == null || buyPrice <= 0) {
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

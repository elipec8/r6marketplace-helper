package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import javax.script.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ItemTradePriorityByExpressionCalculator {

    public boolean isValidExpression(String tradePriorityExpression) {
        try {
            // Create a dummy item with default values
            Item dummyItem = new Item();
            dummyItem.setRarity(ItemRarity.EPIC);
            dummyItem.setType(ItemType.WeaponAttachmentSkinSet);
            dummyItem.setMaxBuyPrice(0);
            dummyItem.setBuyOrdersCount(0);
            dummyItem.setMinSellPrice(0);
            dummyItem.setSellOrdersCount(0);
            dummyItem.setLastSoldAt(LocalDateTime.now());
            dummyItem.setLastSoldPrice(0);
            dummyItem.setMonthAveragePrice(0);
            dummyItem.setMonthMedianPrice(0);
            dummyItem.setMonthMaxPrice(0);
            dummyItem.setMonthMinPrice(0);
            dummyItem.setMonthSalesPerDay(0);
            dummyItem.setMonthSales(0);
            dummyItem.setDayAveragePrice(0);
            dummyItem.setDayMedianPrice(0);
            dummyItem.setDayMaxPrice(0);
            dummyItem.setDayMinPrice(0);
            dummyItem.setDaySales(0);
            dummyItem.setPriceToBuyIn1Hour(0);
            dummyItem.setPriceToBuyIn6Hours(0);
            dummyItem.setPriceToBuyIn24Hours(0);
            dummyItem.setPriceToBuyIn168Hours(0);
            dummyItem.setPriceToBuyIn720Hours(0);

            calculateTradePriority(tradePriorityExpression, dummyItem, 0, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long calculateTradePriority(String tradePriorityExpression, Item item, Integer price, Integer time) {
        Map<String, Object> variables = new HashMap<>();
        populateVariables(variables, item, price, time);

        String[] statements = tradePriorityExpression.split("\\|");
        for (String stmt : statements) {
            stmt = stmt.trim();
            if (stmt.isEmpty()) continue;

            Long result = processStatement(stmt, variables);
            if (result != null) {
                return result;
            }
        }
        throw new IllegalArgumentException("No return statement encountered in expression.");
    }

    private void populateVariables(Map<String, Object> variables, Item item, Integer price, Integer time) {
        variables.put("rarity", item.getRarity() != null ? item.getRarity().name() : null);
        variables.put("type", item.getType() != null ? item.getType().name() : null);
        variables.put("maxBuyPrice", item.getMaxBuyPrice());
        variables.put("buyOrdersCount", item.getBuyOrdersCount());
        variables.put("minSellPrice", item.getMinSellPrice());
        variables.put("sellOrdersCount", item.getSellOrdersCount());
        variables.put("lastSoldAt", item.getLastSoldAt());
        variables.put("lastSoldPrice", item.getLastSoldPrice());
        variables.put("monthAveragePrice", item.getMonthAveragePrice());
        variables.put("monthMedianPrice", item.getMonthMedianPrice());
        variables.put("monthMaxPrice", item.getMonthMaxPrice());
        variables.put("monthMinPrice", item.getMonthMinPrice());
        variables.put("monthSalesPerDay", item.getMonthSalesPerDay());
        variables.put("monthSales", item.getMonthSales());
        variables.put("dayAveragePrice", item.getDayAveragePrice());
        variables.put("dayMedianPrice", item.getDayMedianPrice());
        variables.put("dayMaxPrice", item.getDayMaxPrice());
        variables.put("dayMinPrice", item.getDayMinPrice());
        variables.put("daySales", item.getDaySales());
        variables.put("priceToBuyIn1Hour", item.getPriceToBuyIn1Hour());
        variables.put("priceToBuyIn6Hours", item.getPriceToBuyIn6Hours());
        variables.put("priceToBuyIn24Hours", item.getPriceToBuyIn24Hours());
        variables.put("priceToBuyIn168Hours", item.getPriceToBuyIn168Hours());
        variables.put("priceToBuyIn720Hours", item.getPriceToBuyIn720Hours());
        variables.put("price", price);
        variables.put("time", time);
    }

    private Long processStatement(String statement, Map<String, Object> variables) {
        if (statement.startsWith("if ")) {
            String[] parts = statement.split("\\s+then\\s+", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid if-then statement: " + statement);
            }
            String condition = parts[0].substring(3).trim();
            String thenActionsStr = parts[1].trim();
            if (evaluateCondition(condition, variables)) {
                String[] thenActions = thenActionsStr.split("\\|");
                for (String action : thenActions) {
                    action = action.trim();
                    if (action.isEmpty()) continue;
                    Long result = processStatement(action, variables);
                    if (result != null) return result;
                }
            }
        } else if (statement.startsWith("return ")) {
            String expr = statement.substring(6).trim();
            return evaluateExpression(expr, variables);
        } else {
            processAssignment(statement, variables);
        }
        return null;
    }

    private void processAssignment(String assignment, Map<String, Object> variables) {
        String[] parts = assignment.split("=", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + assignment);
        }
        String varName = parts[0].trim();
        String expr = parts[1].trim();
        Long value = evaluateExpression(expr, variables);
        variables.put(varName, value);
    }

    private boolean evaluateCondition(String condition, Map<String, Object> variables) {
        try {
            condition = condition.replace("^", "**");
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Bindings bindings = engine.createBindings();
            bindings.putAll(variables);
            Object result = engine.eval(condition, bindings);
            if (result instanceof Boolean) {
                return (Boolean) result;
            } else {
                throw new IllegalArgumentException("Condition must evaluate to boolean: " + condition);
            }
        } catch (ScriptException e) {
            throw new IllegalArgumentException("Invalid condition: " + condition, e);
        }
    }

    private Long evaluateExpression(String expr, Map<String, Object> variables) {
        try {
            expr = expr.replace("^", "**");
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Bindings bindings = engine.createBindings();
            bindings.putAll(variables);
            Object result = engine.eval(expr, bindings);
            if (result instanceof Number) {
                return ((Number) result).longValue();
            } else {
                throw new IllegalArgumentException("Expression must evaluate to a number: " + expr);
            }
        } catch (ScriptException e) {
            throw new IllegalArgumentException("Invalid expression: " + expr, e);
        }
    }
}

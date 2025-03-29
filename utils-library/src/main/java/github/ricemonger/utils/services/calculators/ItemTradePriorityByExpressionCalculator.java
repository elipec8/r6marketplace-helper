package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import lombok.RequiredArgsConstructor;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ItemTradePriorityByExpressionCalculator {

    public boolean isValidExpression(String tradePriorityExpression) {
        try {
            // Creates a dummy item with default values
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
            dummyItem.setPriceToBuyIn1Hour(null);
            dummyItem.setPriceToBuyIn6Hours(null);
            dummyItem.setPriceToBuyIn24Hours(null);
            dummyItem.setPriceToBuyIn168Hours(null);
            dummyItem.setPriceToBuyIn720Hours(null);

            calculateTradePriority(tradePriorityExpression, dummyItem, 0, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long calculateTradePriority(String tradePriorityExpression, Item item, Integer price, Integer time) {
        try {
            Map<String, Object> context = new HashMap<>();
            populateContext(context, item, price, time);

            String[] statements = tradePriorityExpression.split("\\$");
            for (String stmt : statements) {
                stmt = stmt.trim();
                if (stmt.isEmpty()) continue;

                Long result = processStatement(stmt, context);
                if (result != null) {
                    return result;
                }
            }
            throw new IllegalArgumentException("No return statement encountered in expression.");
        } catch (Exception e) {
            log.error("Exception during trade Priority Expression evaluation: {} : ", tradePriorityExpression, e);
            return null;
        }
    }

    private void populateContext(Map<String, Object> context, Item item, Integer price, Integer time) {
        context.put("rarity", item.getRarity() != null ? item.getRarity().name() : null);
        context.put("type", item.getType() != null ? item.getType().name() : null);
        context.put("maxBuyPrice", item.getMaxBuyPrice());
        context.put("buyOrdersCount", item.getBuyOrdersCount());
        context.put("minSellPrice", item.getMinSellPrice());
        context.put("sellOrdersCount", item.getSellOrdersCount());
        context.put("lastSoldAt", item.getLastSoldAt());
        context.put("lastSoldPrice", item.getLastSoldPrice());
        context.put("monthAveragePrice", item.getMonthAveragePrice());
        context.put("monthMedianPrice", item.getMonthMedianPrice());
        context.put("monthMaxPrice", item.getMonthMaxPrice());
        context.put("monthMinPrice", item.getMonthMinPrice());
        context.put("monthSalesPerDay", item.getMonthSalesPerDay());
        context.put("monthSales", item.getMonthSales());
        context.put("dayAveragePrice", item.getDayAveragePrice());
        context.put("dayMedianPrice", item.getDayMedianPrice());
        context.put("dayMaxPrice", item.getDayMaxPrice());
        context.put("dayMinPrice", item.getDayMinPrice());
        context.put("daySales", item.getDaySales());
        context.put("priceToBuyIn1Hour", item.getPriceToBuyIn1Hour());
        context.put("priceToBuyIn6Hours", item.getPriceToBuyIn6Hours());
        context.put("priceToBuyIn24Hours", item.getPriceToBuyIn24Hours());
        context.put("priceToBuyIn168Hours", item.getPriceToBuyIn168Hours());
        context.put("priceToBuyIn720Hours", item.getPriceToBuyIn720Hours());
        context.put("price", price);
        context.put("time", time);
    }

    private Long processStatement(String statement, Map<String, Object> context) {
        statement = statement.trim();
        if (statement.startsWith("if ")) {
            String[] parts = statement.split("\\s+then\\s+", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid if-then statement: " + statement);
            }
            String condition = parts[0].substring(3).trim();
            String thenPart = parts[1].trim();
            if (evaluateCondition(condition, context)) {
                String[] thenStatements = thenPart.split("\\$");
                for (String stmt : thenStatements) {
                    stmt = stmt.trim();
                    if (stmt.isEmpty()) continue;
                    Long result = processStatement(stmt, context);
                    if (result != null) return result;
                }
            }
        } else if (statement.startsWith("return ")) {
            String expr = statement.substring(6).trim();
            return evaluateArithmeticExpression(expr, context);
        } else if (statement.contains("=")) {
            processAssignment(statement, context);
        } else {
            throw new IllegalArgumentException("Invalid statement: " + statement);
        }
        return null;
    }

    private void processAssignment(String assignment, Map<String, Object> context) {
        String[] parts = assignment.split("=", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + assignment);
        }
        String varName = parts[0].trim();
        String expr = parts[1].trim();
        Long value = evaluateArithmeticExpression(expr, context);
        context.put(varName, value);
    }

    private boolean evaluateCondition(String condition, Map<String, Object> context) {
        String[] operators = {"==", "!=", "<=", ">=", "<", ">"};
        for (String op : operators) {
            int idx = condition.indexOf(op);
            if (idx > 0) {
                String lhsExpr = condition.substring(0, idx).trim();
                String rhsExpr = condition.substring(idx + op.length()).trim();
                Object lhs = evaluateOperand(lhsExpr, context);
                Object rhs = evaluateOperand(rhsExpr, context);
                return compareValues(lhs, rhs, op);
            }
        }
        throw new IllegalArgumentException("No valid operator found in condition: " + condition);
    }

    private Object evaluateOperand(String operand, Map<String, Object> context) {
        if ((operand.startsWith("'") && operand.endsWith("'")) || (operand.startsWith("\"") && operand.endsWith("\""))) {
            return operand.substring(1, operand.length() - 1);
        }
        try {
            return evaluateArithmeticExpression(operand, context);
        } catch (IllegalArgumentException e) {
            Object value = context.get(operand);
            if (value == null && !context.containsKey(operand)) {
                throw new IllegalArgumentException("Variable not found: " + operand);
            }
            return value;
        }
    }

    private boolean compareValues(Object lhs, Object rhs, String operator) {
        if (lhs == null || rhs == null) {
            switch (operator) {
                case "==":
                    return lhs == rhs;
                case "!=":
                    return lhs != rhs;
                default:
                    throw new IllegalArgumentException("Operator " + operator + " not supported for null values");
            }
        } else if (lhs instanceof Number && rhs instanceof Number) {
            long a = ((Number) lhs).longValue();
            long b = ((Number) rhs).longValue();
            switch (operator) {
                case "==":
                    return a == b;
                case "!=":
                    return a != b;
                case "<":
                    return a < b;
                case ">":
                    return a > b;
                case "<=":
                    return a <= b;
                case ">=":
                    return a >= b;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        } else {
            String aStr = lhs.toString();
            String bStr = rhs.toString();
            switch (operator) {
                case "==":
                    return aStr.equals(bStr);
                case "!=":
                    return !aStr.equals(bStr);
                default:
                    throw new IllegalArgumentException("Operator " + operator + " not supported for non-numeric values");
            }
        }
    }

    private Long evaluateArithmeticExpression(String expr, Map<String, Object> context) {
        List<String> tokens = tokenizeArithmetic(expr);
        List<String> resolvedTokens = replaceVariables(tokens, context);
        List<String> postfix = shuntingYard(resolvedTokens);
        return evaluatePostfix(postfix);
    }

    private List<String> tokenizeArithmetic(String expr) {
        List<String> tokens = new ArrayList<>();
        int i = 0, len = expr.length();
        while (i < len) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < len && Character.isDigit(expr.charAt(i))) {
                    num.append(expr.charAt(i++));
                }
                tokens.add(num.toString());
            } else if (Character.isLetter(c)) {
                StringBuilder var = new StringBuilder();
                while (i < len && (Character.isLetterOrDigit(expr.charAt(i)) || expr.charAt(i) == '_')) {
                    var.append(expr.charAt(i++));
                }
                tokens.add(var.toString());
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '(' || c == ')') {
                tokens.add(Character.toString(c));
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
        return tokens;
    }

    private List<String> replaceVariables(List<String> tokens, Map<String, Object> context) {
        List<String> resolved = new ArrayList<>();
        for (String token : tokens) {
            if (Character.isLetter(token.charAt(0))) {
                Object value = context.get(token);
                if (value == null) {
                    resolved.add("null");
                } else if (value instanceof Number) {
                    resolved.add(Long.toString(((Number) value).longValue()));
                } else {
                    throw new IllegalArgumentException("Variable " + token + " is not a number: " + value);
                }
            } else {
                resolved.add(token);
            }
        }
        return resolved;
    }

    private List<String> shuntingYard(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("^", 4);
        precedence.put("*", 3);
        precedence.put("/", 3);
        precedence.put("%", 3);
        precedence.put("+", 2);
        precedence.put("-", 2);

        for (String token : tokens) {
            if (token.matches("\\d+") || token.equals("null")) {
                output.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && !stack.peek().equals("(") &&
                        precedence.getOrDefault(stack.peek(), 0) >= precedence.get(token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }

    private Long evaluatePostfix(List<String> postfix) {
        Deque<Long> stack = new ArrayDeque<>();
        for (String token : postfix) {
            if (token.equals("null")) {
                stack.push(null);
            } else if (token.matches("\\d+")) {
                stack.push(Long.parseLong(token));
            } else {
                Long b = stack.pop();
                Long a = stack.pop();
                if (a == null || b == null) {
                    stack.push(null);
                    continue;
                }
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                    case "%":
                        stack.push(a % b);
                        break;
                    case "^":
                        stack.push((long) Math.pow(a, b));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }
        return stack.isEmpty() ? null : stack.pop();
    }
}

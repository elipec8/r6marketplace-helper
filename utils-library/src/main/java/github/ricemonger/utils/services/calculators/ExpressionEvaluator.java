package github.ricemonger.utils.services.calculators;

import java.util.*;

public class ExpressionEvaluator {
    private final String expression;
    private final Map<String, Long> variables = new HashMap<>();

    public ExpressionEvaluator(String expression) {
        this.expression = expression;
    }

    public Long evaluate(Long a, Long b, Long c) {
        variables.clear();
        variables.put("a", a);
        variables.put("b", b);
        variables.put("c", c);

        String[] statements = expression.split("\\|");
        for (String stmt : statements) {
            stmt = stmt.trim();
            if (stmt.isEmpty()) continue;

            if (stmt.startsWith("if ")) {
                int thenIndex = stmt.indexOf(" then ");
                String condition = stmt.substring(3, thenIndex).trim();
                String action = stmt.substring(thenIndex + 6).trim();

                if (evaluateCondition(condition)) {
                    if (action.startsWith("return ")) {
                        return evaluateExpression(action.substring(7));
                    } else {
                        processAssignment(action);
                    }
                }
            } else if (stmt.startsWith("return ")) {
                return evaluateExpression(stmt.substring(7));
            } else {
                processAssignment(stmt);
            }
        }
        return null;
    }

    private boolean evaluateCondition(String condition) {
        String[] parts = condition.split("==");
        if (parts.length == 2) {
            String left = parts[0].trim();
            String right = parts[1].trim();
            Long leftVal = getValue(left);
            Long rightVal = getValue(right);
            return Objects.equals(leftVal, rightVal);
        }
        return false;
    }

    private Long getValue(String token) {
        if ("null".equals(token)) return null;
        if (variables.containsKey(token)) return variables.get(token);
        try {
            return Long.parseLong(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void processAssignment(String assignment) {
        String[] parts = assignment.split("=");
        String var = parts[0].trim();
        String expr = parts[1].trim();
        variables.put(var, evaluateExpression(expr));
    }

    private Long evaluateExpression(String expr) {
        try {
            List<String> postfix = infixToPostfix(expr);
            Stack<Long> stack = new Stack<>();
            for (String token : postfix) {
                if (isOperator(token)) {
                    Long b = stack.pop();
                    Long a = stack.pop();
                    if (a == null || b == null) return null;
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
                    }
                } else {
                    stack.push(getValue(token));
                }
            }
            return stack.isEmpty() ? null : stack.pop();
        } catch (Exception e) {
            return null;
        }
    }

    private List<String> infixToPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : tokenize(expr)) {
            if (isNumber(token) || isVariable(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) output.add(stack.pop());
                stack.pop();
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) output.add(stack.pop());
        return output;
    }

    private String[] tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        for (char c : expr.toCharArray()) {
            if (Character.isWhitespace(c)) {
                if (buffer.length() > 0) {
                    tokens.add(buffer.toString());
                    buffer.setLength(0);
                }
            } else if (isOperator(Character.toString(c)) || c == '(' || c == ')') {
                if (buffer.length() > 0) {
                    tokens.add(buffer.toString());
                    buffer.setLength(0);
                }
                tokens.add(Character.toString(c));
            } else {
                buffer.append(c);
            }
        }
        if (buffer.length() > 0) tokens.add(buffer.toString());
        return tokens.toArray(new String[0]);
    }

    private boolean isOperator(String token) {
        return "+-*/%".contains(token) && token.length() == 1;
    }

    private int precedence(String op) {
        if ("+-".contains(op)) return 1;
        if ("*/%".contains(op)) return 2;
        return -1;
    }

    private boolean isNumber(String token) {
        return token.matches("\\d+");
    }

    private boolean isVariable(String token) {
        return token.matches("[a-zA-Z]+");
    }
}

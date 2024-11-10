package com.example.workingcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView resultDisplay;
    private StringBuilder expression;
    private boolean isOpenParenthesis = true;
    private boolean resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultDisplay = findViewById(R.id.editText);
        expression = new StringBuilder();

        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        findViewById(R.id.btn0).setOnClickListener(v -> appendCharacter("0"));
        findViewById(R.id.btn1).setOnClickListener(v -> appendCharacter("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> appendCharacter("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> appendCharacter("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> appendCharacter("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> appendCharacter("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> appendCharacter("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> appendCharacter("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> appendCharacter("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> appendCharacter("9"));
        findViewById(R.id.btnDot).setOnClickListener(v -> appendCharacter("."));

        findViewById(R.id.btnPlus).setOnClickListener(v -> appendOperator("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> appendOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> appendOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> appendOperator("/"));

        findViewById(R.id.btnBrackets).setOnClickListener(v -> toggleParenthesis());

        findViewById(R.id.btnClear).setOnClickListener(v -> clearExpression());
        findViewById(R.id.btnDel).setOnClickListener(v -> removeLastCharacter());
        findViewById(R.id.btnEquals).setOnClickListener(v -> computeExpressionResult());
    }

    private void appendCharacter(String character) {
        if (resultShown) {
            expression.setLength(0);
            resultShown = false;
        }
        expression.append(character);
        resultDisplay.setText(expression.toString());
    }

    private void appendOperator(String operator) {
        if (resultShown) {
            resultShown = false;
        }
        expression.append(operator);
        resultDisplay.setText(expression.toString());
    }

    private void toggleParenthesis() {
        if (isOpenParenthesis) {
            appendCharacter("(");
        } else {
            appendCharacter(")");
        }
        isOpenParenthesis = !isOpenParenthesis;
    }

    private void clearExpression() {
        expression.setLength(0);
        resultDisplay.setText("0");
        resultShown = false;
    }

    private void removeLastCharacter() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            resultDisplay.setText(expression.length() > 0 ? expression.toString() : "0");
        }
    }

    private void computeExpressionResult() {
        try {
            double result = evaluateExpression(expression.toString());
            resultDisplay.setText(String.valueOf(result));
            expression.setLength(0);
            expression.append(result);
            resultShown = true;
        } catch (Exception e) {
            resultDisplay.setText("Error");
            expression.setLength(0);
        }
    }

    private double evaluateExpression(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (currentChar == ' ')
                continue;

            if (currentChar == '-' && (i == 0 || isOperator(expression.charAt(i - 1)) || expression.charAt(i - 1) == '(')) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentChar);
                i++;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            }
            else if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            }
            else if (currentChar == '(') {
                operators.push(currentChar);
            }
            else if (currentChar == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            }
            else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && hasPrecedence(currentChar, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(currentChar);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean isOperator(char operator) {
        return operator == '+' || operator == '-' || operator == '*' || operator == '/';
    }

    private boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')')
            return false;
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'))
            return false;
        return true;
    }

    private double applyOperator(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}

package com.example.workingcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView screenDisplay;
    private StringBuilder expressionInput;
    private boolean canOpenBracket = true; // Control whether to open or close brackets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenDisplay = findViewById(R.id.editText);
        expressionInput = new StringBuilder();

        // Set up button listeners for different inputs
        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        // Number buttons
        findViewById(R.id.btn1).setOnClickListener(v -> addToExpression("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> addToExpression("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> addToExpression("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> addToExpression("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> addToExpression("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> addToExpression("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> addToExpression("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> addToExpression("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> addToExpression("9"));
        findViewById(R.id.btn0).setOnClickListener(v -> addToExpression("0"));
        findViewById(R.id.btnDot).setOnClickListener(v -> addToExpression("."));

        // Operation buttons
        findViewById(R.id.btnPlus).setOnClickListener(v -> addToExpression("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> addToExpression("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> addToExpression("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> addToExpression("/"));

        // Bracket button
        findViewById(R.id.btnBrackets).setOnClickListener(v -> toggleBrackets());

        // Functional buttons
        findViewById(R.id.btnClear).setOnClickListener(v -> resetInput());
        findViewById(R.id.btnDel).setOnClickListener(v -> removeLastCharacter());
        findViewById(R.id.btnEquals).setOnClickListener(v -> computeResult());
    }

    private void addToExpression(String value) {
        expressionInput.append(value);
        screenDisplay.setText(expressionInput.toString());
    }

    private void toggleBrackets() {
        if (canOpenBracket) {
            addToExpression("(");
        } else {
            addToExpression(")");
        }
        canOpenBracket = !canOpenBracket;
    }

    private void resetInput() {
        expressionInput.setLength(0); // Clear the input
        screenDisplay.setText("0"); // Reset display to zero
    }

    private void removeLastCharacter() {
        if (expressionInput.length() > 0) {
            expressionInput.deleteCharAt(expressionInput.length() - 1);
            screenDisplay.setText(expressionInput.length() > 0 ? expressionInput.toString() : "0");
        }
    }

    private void computeResult() {
        try {
            double result = evaluateExpression(expressionInput.toString());
            screenDisplay.setText(String.valueOf(result));
            expressionInput.setLength(0);
            expressionInput.append(result); // Start fresh with result
        } catch (Exception e) {
            screenDisplay.setText("Error");
            expressionInput.setLength(0); // Clear input on error
        }
    }

    private double evaluateExpression(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            // Skip any whitespace
            if (currentChar == ' ')
                continue;

            // If the character is a digit or a decimal, parse it
            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--; // Adjust for the outer loop increment
                values.push(Double.parseDouble(sb.toString()));
            }
            // Handle opening parenthesis
            else if (currentChar == '(') {
                operators.push(currentChar);
            }
            // Handle closing parenthesis
            else if (currentChar == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Pop the opening parenthesis
            }
            // If an operator is encountered
            else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && precedence(currentChar, operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(currentChar);
            }
        }

        // Apply any remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean isOperator(char op) {
        return op == '+' || op == '-' || op == '*' || op == '/';
    }

    private boolean precedence(char currentOp, char topOp) {
        if (topOp == '(' || topOp == ')')
            return false;
        if ((currentOp == '*' || currentOp == '/') && (topOp == '+' || topOp == '-'))
            return false;
        return true;
    }

    private double applyOperation(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}

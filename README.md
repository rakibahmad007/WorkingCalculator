# Working Calculator App


## MIDTERM-PROJECT-2130227-RAKIB-AHMAD-CSE464-1

  A simple Android calculator app with basic arithmetic operations and parentheses handling.
  
Click the link to see the working video: https://drive.google.com/file/d/1Z16Vc9g-EQE-Cd-98Qnk4wmwzXGFA_6b/view?usp=sharing

**Features**
  - Basic Operations: Supports addition, subtraction, multiplication, and division.
  - Parentheses Support: Allows use of parentheses for complex expressions.
  - Decimal Point Handling: Supports decimal point entries.
  - Clear & Delete: Includes options to clear the entire expression or delete the last character.
  - Error Handling: Displays an error message for invalid operations (e.g., division by zero).
  - Result Retention: After computing a result, it can be used as the starting point for a new calculation.
    
**Code Structure**

  - onCreate Method: Initializes the display and buttons.
  - initializeButtonListeners Method: Sets listeners for numeric, operator, and function buttons.
  - appendCharacter Method: Adds a digit or decimal point to the current expression.
  - appendOperator Method: Adds an operator to the current expression.
  - toggleParenthesis Method: Adds an opening or closing parenthesis as needed.
  - clearExpression Method: Clears the expression display.
  - removeLastCharacter Method: Deletes the last character from the current expression.
  - computeExpressionResult Method: Evaluates and displays the result of the expression.
  - evaluateExpression Method: Parses and calculates the expression using a stack-based approach.
  - isOperator Method: Checks if a character is a valid operator.
  - hasPrecedence Method: Determines operator precedence for expression evaluation.
  - applyOperator Method: Executes the operation between two numbers.
    
**How to Use**

  1. Enter Numbers: Tap numeric buttons to input numbers.
  2. Choose Operations: Tap operator buttons for addition, subtraction, etc.
  3. Parentheses: Use parentheses for complex calculations.
  4. Compute Result: Press = to evaluate the expression.
  5. Clear or Delete: Use the clear button to reset or the delete button to remove the last entry.

**Error Handling**

  Displays "Error" for invalid expressions or division by zero.

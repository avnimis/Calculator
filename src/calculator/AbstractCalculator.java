package calculator;

import static java.lang.Math.abs;

/**
 * An Abstract that defines some helper methods for Calculator objects.
 */
public abstract class AbstractCalculator implements Calculator {

  protected final String result;
  protected final int num1;
  protected final int num2;
  protected final boolean firstNum;
  protected final char operator;
  protected final int intResult;
  protected final char last;

  /**
   * A function that creates an Calculator type object.
   * @param result - the string result.
   * @param num1 - the first number in the equation.
   * @param num2 - the second number in the equation.
   * @param firstNum - whether the current equation is on the first number.
   * @param operator - the operator used to evaluate the equation.
   * @param intResult - the result of the operation.
   * @param last - the last input.
   * @return - returns the new Calculator object.
   */
  protected abstract Calculator create(
          String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last);


  /**
   * A constructor that defaults the variables in the calculator to certain starting values.
   */
  protected AbstractCalculator() {
    this.result = "";
    this.num1 = 0;
    this.num2 = 0;
    this.firstNum = true;
    this.operator = 'x';
    this.intResult = 0;
    this.last = 'x';
  }


  /**
   * A constructor that takes in values for each variable.
   *
   * @param result    - the current String display of the calculator
   * @param num1      - the first number in the equation. 0 if not yet defined.
   * @param num2      - the second number in the equation. 0 if not yet defined.
   * @param firstNum  - whether the input is on the first number.
   * @param operator  - the current operator used to compute the result.
   * @param intResult - the current result of the operation.
   * @param last      - the last character that was input.
   */
  protected AbstractCalculator(
          String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last) {
    this.result = result;
    this.num1 = num1;
    this.num2 = num2;
    this.firstNum = firstNum;
    this.operator = operator;
    this.intResult = intResult;
    this.last = last;
  }

  /**
   * A helper method that calculates two numbers. Returns 0 if the answer overflows.
   *
   * @param operator - the operation to be performed on the numbers.
   * @param n1       - the first number.
   * @param n2       - the second number.
   * @return - returns the result of the calculation.
   */
  protected int calculate(char operator, int n1, int n2) {
    int result = 0;

    if (operator == '+') {
      if (n1 > 0 && n2 > 0) {
        if (Integer.MAX_VALUE - n1 < n2) {
          return 0;
        }
      } else if (n1 < 0 && n2 < 0) {
        if (Integer.MIN_VALUE - n1 < n1) {
          return 0;
        }
      }

      result = n1 + n2;
    } else if (operator == '-') {

      if (n2 < 0) {
        if (Integer.MAX_VALUE + n2 < n1) {
          return 0;
        }
      } else {
        if (Integer.MIN_VALUE + n2 > n1) {
          return 0;
        }
      }

      result = n1 - n2;
    } else if (operator == '*') {

      if ((abs(Integer.MAX_VALUE / n1) < n2) || (abs(Integer.MIN_VALUE / n1) < n2)) {
        return 0;
      }

      result = n1 * n2;
    }

    if (result <= Integer.MIN_VALUE || result >= Integer.MAX_VALUE) {
      result = 0;
    }

    return result;
  }

  /**
   * Checks if the character is a valid operator, +,-, or *.
   *
   * @param c - the character to be checked.
   * @return - true if the character is operator, false if not.
   */
  protected boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*';
  }


  /**
   * A helper method that deals with if the input is a digit.
   *
   * @param c - the input.
   * @param result - the string result.
   * @param n1 - the first number.
   * @param n2 - the second number.
   * @param firstNum - whether it's the first number.
   * @param operator - the operator.
   * @param intResult - the result of the equation.
   * @param last - the last input.
   * @return - returns a new Calculator object.
   */
  protected Calculator digit(
          char c, String result, int n1, int n2, boolean firstNum,
          char operator, int intResult, char last) {

    // if a number is typed after hitting equals, clears the calculator and starts anew
    if (last == '=') {
      return input('C').input(c);
    }

    if (isOperator(last)) {
      firstNum = false;
      n2 = 0;
    }


    if (firstNum) {
      // check for input overflow
      if (n1 <= (Integer.MIN_VALUE / 10) || n1 >= (Integer.MAX_VALUE / 10)) {
        if (n1 == (Integer.MIN_VALUE / 10) || n1 == (Integer.MAX_VALUE / 10)) {
          if (Character.getNumericValue(c) > 7) {
            throw new IllegalArgumentException();
          }
        } else {
          throw new IllegalArgumentException();
        }
      }

      n1 = n1 * 10 + Character.getNumericValue(c);
      result = result + c;

    } else {
      //check for input overflow
      if (n2 <= (Integer.MIN_VALUE / 10) || n2 >= (Integer.MAX_VALUE / 10)) {
        if (n2 == (Integer.MIN_VALUE / 10) || n2 == (Integer.MAX_VALUE / 10)) {
          if (Character.getNumericValue(c) > 7) {
            throw new IllegalArgumentException();
          }
        } else {
          throw new IllegalArgumentException();
        }
      }

      n2 = n2 * 10 + Character.getNumericValue(c);
      result = result + c;
    }

    return create(result, n1, n2, firstNum, operator, intResult, c);

  }


  @Override
  public String getResult() {
    return this.result;
  }


}

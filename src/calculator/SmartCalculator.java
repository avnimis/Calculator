package calculator;

import static java.lang.Character.isDigit;

/**
 * A class representing a SmartCalculator, an implementation of Calculator that
 * supports more functions.
 */
public class SmartCalculator extends AbstractCalculator {

  @Override
  protected Calculator create(String result, int num1, int num2, boolean firstNum,
                              char operator, int intResult, char last) {
    return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, last);
  }

  /**
   * An enum class representing the type of possible inputs.
   */
  private enum Input {
    DIGIT, EQUALS, OPERATOR, CLEAR
  }

  /**
   * A public constructor creating a default SmartCalculator object.
   */
  public SmartCalculator() {
    super();
  }

  private SmartCalculator(
          String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last) {

    super(result, num1, num2, firstNum, operator, intResult, last);
  }


  private Calculator charIsEquals(
          char c, String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last) throws IllegalArgumentException {

    if (last == 'x') {
      return new SmartCalculator();
    }

    if (isOperator(last)) {
      num2 = num1;
      intResult = calculate(operator, num1, num2);
      num1 = intResult;
      result = String.valueOf(intResult);
    }

    if (last == '=') {

      intResult = calculate(operator, num1, num2);
      num1 = intResult;
      firstNum = true;
      result = String.valueOf(intResult);

    }

    if (isDigit(last)) {

      if (firstNum) {

        return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, c);

      } else {
        intResult = calculate(operator, num1, num2);
        result = String.valueOf(intResult);
        num1 = intResult;
        firstNum = true;
      }

    }

    return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, c);

  }


  private Calculator operator(
          char c, String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last) {

    if (c == '+' && last == 'x') {
      return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, 'x');
    }

    if (isOperator(last)) {

      result = result.replace(operator, c);
      operator = c;
      return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, c);


    }

    if (last == '=') {
      result = result + c;
      operator = c;
      return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, c);
    }


    if ((last == 'x') && (c == '-' || c == '*')) {
      throw new IllegalArgumentException(
              "Invalid input: operator cannot be called twice or before any numbers.");
    }

    if (firstNum) {
      operator = c;
      result = result + c;
    } else {
      intResult = calculate(this.operator, num1, num2);
      num1 = intResult;
      num2 = 0;
      result = String.valueOf(intResult) + c;
      operator = c;
    }

    return new SmartCalculator(result, num1, num2, firstNum, operator, intResult, c);
  }


  @Override
  public Calculator input(char c) throws IllegalArgumentException {

    Input input;

    if (isDigit(c)) {
      input = Input.DIGIT;
    } else if (isOperator(c)) {
      input = Input.OPERATOR;
    } else if (c == '=') {
      input = Input.EQUALS;
    } else if (c == 'C') {
      input = Input.CLEAR;
    } else {
      throw new IllegalArgumentException("Invalid Input");
    }


    String nResult = this.result;
    char nLast = this.last;
    int nNum1 = this.num1;
    int nNum2 = this.num2;
    char nOperator = this.operator;
    int nIntResult = this.intResult;
    boolean nFirstNum = this.firstNum;


    if (nLast == 'x') {
      if (c == '*' || c == '-' || c == 'C') {
        throw new IllegalArgumentException("Invalid Input, type a number in first.");
      }
      nFirstNum = true;
    } else if (nLast == '=') {
      nFirstNum = true;
    }

    // the default for sc is to clear, which creates a new smart calculator.
    Calculator sc = new SmartCalculator();

    switch (input) {
      case DIGIT:
        sc = digit(c, nResult, nNum1, nNum2, nFirstNum, nOperator, nIntResult, nLast);
        break;
      case EQUALS:
        sc = charIsEquals(c, nResult, nNum1, nNum2, nFirstNum, nOperator, nIntResult, nLast);
        break;
      case OPERATOR:
        sc = operator(c, nResult, nNum1, nNum2, nFirstNum, nOperator, nIntResult, nLast);
        break;
      // because the default is taken care of in initializing sc, this default case does nothing.
      default:
        break;
    }

    return sc;
  }

}



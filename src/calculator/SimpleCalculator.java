package calculator;


/**
 * A simple calculator that implements the Calculator interface.
 * Supports addition, subtraction, and multiplication.
 * Supports negative results, but not negative inputs.
 */
public class SimpleCalculator extends AbstractCalculator {


  @Override
  protected Calculator create(String result, int num1, int num2, boolean firstNum,
                              char operator, int intResult, char last) {
    return new SimpleCalculator(result, num1, num2, firstNum, operator, intResult, last);
  }

  /**
   * Constructs a simple calculator.
   */
  public SimpleCalculator() {
    super();
  }

  private SimpleCalculator(
          String result, int num1, int num2, boolean firstNum,
          char operator, int intResult, char last) {

    super(result, num1, num2, firstNum, operator, intResult, last);
  }


  @Override
  public Calculator input(char c) throws IllegalArgumentException {

    String nResult = this.result;
    char nLast = this.last;
    int nNum1 = this.num1;
    int nNum2 = this.num2;
    char nOperator = this.operator;
    int nIntResult = this.intResult;
    boolean nFirstNum = this.firstNum;

    if (this.last == 'x') {
      nFirstNum = true;

      if (!Character.isDigit(c)) {
        throw new IllegalArgumentException("Enter a number first");
      }
    }
    if (this.last == '=') {
      nFirstNum = true;
    }

    if (!Character.isDigit(c) && !isOperator(c) && c != '=' && c != 'C') {
      throw new IllegalArgumentException("Invalid input");
    }

    // Clear the result
    if (c == 'C') {
      return new SimpleCalculator();
    } else if (Character.isDigit(c) && nLast == '=') {
      return new SimpleCalculator().input(c);
    } else {
      nLast = c;
    }

    // if c is =
    if (c == '=') {

      if (isOperator(this.last)) {
        throw new IllegalArgumentException(
                "Invalid input: equals only after two numbers are inputted.");
      }


      if (this.last != '=' && !nFirstNum) {
        nIntResult = calculate(nOperator, nNum1, nNum2);
        nNum1 = nIntResult;
        nNum2 = 0;
        nResult = String.valueOf(nNum1);
        nFirstNum = true;

      }

      // if c is an operator
    } else if (isOperator(c)) {

      if (isOperator(this.last) || this.last == 'x') {
        throw new IllegalArgumentException(
                "Invalid input: operator cannot be called twice or before any numbers.");
      }

      boolean first = nFirstNum;

      if (first) {
        nOperator = c;
        nFirstNum = false;
        nResult = nResult + c;
      } else {
        nIntResult = calculate(this.operator, nNum1, nNum2);
        nNum1 = nIntResult;
        nNum2 = 0;
        nResult = String.valueOf(nIntResult) + c;
        nOperator = c;
      }


    } else if (Character.isDigit(c)) {

      return digit(c, nResult, nNum1, nNum2, nFirstNum, nOperator, nIntResult, nLast);

    }


    return new SimpleCalculator(nResult, nNum1, nNum2, nFirstNum, nOperator, nIntResult, nLast);
  }


}

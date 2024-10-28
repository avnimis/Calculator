package calculator;


/**
 * An interface that represents a Calculator.
 */
public interface Calculator {

  /**
   * A method that takes in a character and adjusts the calculator accordingly.
   * Only supports valid inputs.
   *
   * @param c - the character entered into the calculator
   * @return - returns a new modified Calculator object
   * @throws IllegalArgumentException - throws exceptions on invalid inputs
   */
  Calculator input(char c) throws IllegalArgumentException;

  /**
   * A getter method to see the current result of the inputs.
   *
   * @return - returns a string representation of the current calculator screen.
   */
  String getResult();


}

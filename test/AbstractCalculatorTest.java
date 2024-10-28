import org.junit.Test;

import calculator.Calculator;

import static org.junit.Assert.assertEquals;

/**
 * An abstract class that tests Calculators.
 */
public abstract class AbstractCalculatorTest {


  Calculator sc;

  @Test
  public void testGetResult() {
    assertEquals("", sc.getResult());
    sc = sc.input('1').input('2');
    assertEquals("12", sc.getResult());

    sc = sc.input('+');
    assertEquals("12+", sc.getResult());

    sc = sc.input('1').input('2');
    assertEquals("12+12", sc.getResult());

    sc = sc.input('=');
    assertEquals("24", sc.getResult());

  }

  @Test
  public void testNoMutation() {
    Calculator calc = sc.input('1');
    assertEquals("", sc.getResult());
    assertEquals("1", calc.getResult());
  }

  @Test
  public void testNumberInput() {
    sc = sc.input('1');
    assertEquals("1", sc.getResult());
    sc = sc.input('2');
    assertEquals("12", sc.getResult());
  }

  @Test
  public void testNumberInputOverflow() {
    sc = sc.input('1').input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1');
    try {
      sc.input('1');
    } catch (IllegalArgumentException e) {
      assertEquals("1111111111", sc.getResult());
    }
  }

  @Test
  public void testNumberAfterEqualsClears() {
    sc = sc.input('1')
            .input('+')
            .input('1')
            .input('=')
            .input('6');
    assertEquals("6", sc.getResult());
  }

  @Test
  public void testNumberSwitchesAfterOperation() {
    sc = sc.input('1').input('+').input('2');
    assertEquals("1+2", sc.getResult());
    sc = sc.input('=')
            .input('4').input('5')
            .input('+')
            .input('6');
    assertEquals("45+6", sc.getResult());
  }

  @Test
  public void testClear() {

    sc = sc.input('1').input('2')
            .input('+')
            .input('2').input('3')
            .input('=');
    sc = sc.input('C');
    assertEquals("", sc.getResult());
  }

  @Test
  public void testClearAfterDigit() {
    sc = sc.input('1').input('C');
    assertEquals("", sc.getResult());
  }

  @Test
  public void testClearAfterOperation() {
    sc = sc.input('1').input('+').input('C');
    assertEquals("", sc.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testClearFirstInput() {
    sc = sc.input('C');
  }

  @Test
  public void testEqualsAfterFirstNumber() {
    sc = sc.input('1').input('2')
            .input('=');
    assertEquals("12", sc.getResult());
  }

  @Test
  public void testAddInBounds() {
    sc = sc.input('1').input('2')
            .input('+')
            .input('2').input('3')
            .input('=');
    assertEquals("35", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('1').input('2')
            .input('+')
            .input('1').input('2').input('3')
            .input('=');
    assertEquals("135", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('1').input('1').input('2')
            .input('+')
            .input('2').input('3')
            .input('=');
    assertEquals("135", sc.getResult());
  }

  @Test
  public void testSubtractInBounds() {
    sc = sc.input('2').input('3')
            .input('-')
            .input('1').input('2')
            .input('=');
    assertEquals("11", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('2').input('3')
            .input('-')
            .input('1').input('1').input('2')
            .input('=');
    assertEquals("-89", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('1').input('2').input('3')
            .input('-')
            .input('1').input('2')
            .input('=');
    assertEquals("111", sc.getResult());
  }

  @Test
  public void testMultiplyInBounds() {
    sc = sc.input('1').input('0')
            .input('*')
            .input('2').input('2')
            .input('=');
    assertEquals("220", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('9')
            .input('*')
            .input('1').input('1').input('2')
            .input('=');
    assertEquals("1008", sc.getResult());

    sc = sc.input('C');
    sc = sc.input('1').input('2').input('3')
            .input('*')
            .input('1').input('2')
            .input('=');
    assertEquals("1476", sc.getResult());
  }

  @Test
  public void testAddOutOfBounds() {
    sc = sc.input('2').input('1').input('4').input('7').input('4').input('8')
            .input('3').input('6').input('4').input('0');
    sc = sc.input('+').input('9').input('=');

    assertEquals("0", sc.getResult());
  }

  @Test
  public void testSubtractOutOfBounds() {
    sc = sc.input('1').input('-').input('1').input('0').input('-')
            .input('2').input('1').input('4').input('7').input('4').input('8')
            .input('3').input('6').input('4').input('7');
    sc = sc.input('=');
    assertEquals("0", sc.getResult());
  }

  @Test
  public void testMultiplyOutOfBounds() {
    sc = sc.input('1').input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1')
            .input('*')
            .input('1').input('1').input('1').input('1')
            .input('=');
    assertEquals("0", sc.getResult());
  }

  @Test
  public void testMultipleOperations() {
    sc = sc.input('1').input('2')
            .input('+')
            .input('2').input('3')
            .input('-');
    assertEquals("35-", sc.getResult());

    sc = sc.input('2').input('3')
            .input('*');
    assertEquals("12*", sc.getResult());

    sc = sc.input('1').input('2')
            .input('=');
    assertEquals("144", sc.getResult());
  }

  @Test
  public void testOperationsAfterEquals() {
    sc = sc.input('1').input('2')
            .input('+')
            .input('5')
            .input('=')
            .input('+');
    assertEquals("17+", sc.getResult());

    sc = sc.input('1').input('2')
            .input('=')
            .input('-');
    assertEquals("29-", sc.getResult());

    sc = sc.input('1').input('2')
            .input('=')
            .input('*');
    assertEquals("17*", sc.getResult());
  }

}

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.SmartCalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the SmartCalculator class.
 */
public class SmartCalculatorTest extends AbstractCalculatorTest {


  @Test
  public void testConstructor() {
    boolean works;

    Calculator calc1 = new SmartCalculator();
    Calculator calc2 = new SmartCalculator();

    works = true;
    assertTrue(works);
  }

  @Before
  public void setUpBeforeClass() {
    this.sc = new SmartCalculator();
  }

  @Test
  public void testEqualsEquals() {
    sc = sc.input('1').input('2')
            .input('-')
            .input('7')
            .input('=');
    assertEquals("5", sc.getResult());

    sc = sc.input('=');
    assertEquals("-2", sc.getResult());

    sc = sc.input('=');
    assertEquals("-9", sc.getResult());
  }

  @Test
  public void testOperationEqual() {
    sc = sc.input('1').input('2')
            .input('*')
            .input('=');
    assertEquals("144", sc.getResult());
    sc = sc.input('=');
    assertEquals("1728", sc.getResult());

    sc = sc.input('C')
            .input('1').input('2')
            .input('+')
            .input('=');
    assertEquals("24", sc.getResult());

    sc = sc.input('C')
            .input('1').input('2')
            .input('-')
            .input('=');
    assertEquals("0", sc.getResult());
  }

  @Test
  public void testPlusFirstInput() {
    sc = sc.input('+')
            .input('1').input('2')
            .input('-')
            .input('3');
    assertEquals("12-3", sc.getResult());
    sc = sc.input('=');
    assertEquals("9", sc.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeFirst() {
    sc = sc.input('-');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiplicationFirst() {
    sc = sc.input('*');
  }

  @Test
  public void testOperationOperation() {
    sc = sc.input('1').input('2')
            .input('+')
            .input('-')
            .input('3');
    assertEquals("12-3", sc.getResult());

    sc = sc.input('=');
    assertEquals("9", sc.getResult());
  }


}

import calculator.Calculator;
import calculator.SimpleCalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the SimpleCalculator class.
 */
public class SimpleCalculatorTest extends AbstractCalculatorTest {

  @Test
  public void testConstructor() {
    boolean works;

    Calculator calc1 = new SimpleCalculator();
    Calculator calc2 = new SimpleCalculator();

    works = true;
    assertTrue(works);
  }

  @Before
  public void setUpBeforeClass() {
    this.sc = new SimpleCalculator();
  }



  // not like smartCalc
  @Test(expected = IllegalArgumentException.class)
  public void testEqualsAfterOperation() {
    sc = sc.input('1').input('2')
            .input('-')
            .input('=');
  }

  // not like smartCalc
  @Test
  public void testEqualsEquals() {
    sc = sc.input('1').input('2')
            .input('+')
            .input('4')
            .input('=')
            .input('=');
    assertEquals("16", sc.getResult());
  }



  // not like smart calc
  @Test(expected = IllegalArgumentException.class)
  public void testOperationFirstInput() {
    sc.input('+');
  }

  // not like smart calc
  @Test(expected = IllegalArgumentException.class)
  public void testOperationAfterOperation() {
    sc.input('1').input('+').input('-');
  }



}

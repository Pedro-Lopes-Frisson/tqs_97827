/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ico0
 */
public class DipTest {
  
  private Dip instance;
  
  
  public DipTest() {
  }
  
  @BeforeEach
  public void setUp() {
    instance = new Dip( new int[]{10, 20, 30, 40, 50}, new int[]{1, 2} );
  }
  
  @AfterEach
  public void tearDown() {
    instance = null;
  }
  
  
  @Test
  public void testConstructorFromBadArrays() {
    IllegalArgumentException illegalArgumentException = assertThrows( IllegalArgumentException.class,
      () -> new Dip( new int[]{1, 2, 3, 4}, new int[]{1, 2} ) );
    
    IllegalArgumentException illegalArgumentException1 = assertThrows( IllegalArgumentException.class,
      () -> new Dip( new int[]{1, 2, 3, 4, 5}, new int[]{1} ) );
    
  }
  
  @DisplayName("Test out of range values for stars")
  @Test
  void outOfRangeStars() {
    // Test for out of range values for numbers (stars)
    IllegalArgumentException illegalArgumentException = assertThrows( IllegalArgumentException.class,
      () -> new Dip( new int[]{1, 2, 3, 4, 5}, new int[]{1, 13} ) );
  
    // Test for out of range values for numbers (squares)
    IllegalArgumentException illegalArgumentException1 = assertThrows( IllegalArgumentException.class,
      () -> new Dip( new int[]{1, 2, 3, 4, 51}, new int[]{1, 12} ) );
  }
  
  @Test
  public void testFormat() {
    // note: correct the implementation of the format(), not the test...
    String result = instance.format();
    assertEquals( "N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. " );
  }
  
}

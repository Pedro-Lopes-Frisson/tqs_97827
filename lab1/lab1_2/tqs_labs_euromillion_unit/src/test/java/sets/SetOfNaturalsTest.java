/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {
  private SetOfNaturals setA;
  private SetOfNaturals setB;
  private SetOfNaturals setC;
  private SetOfNaturals setD;
  private SetOfNaturals setE;

  @BeforeEach
  public void setUp() {
    setA = new SetOfNaturals();
    setB = SetOfNaturals.fromArray( new int[]{10, 20, 30, 40, 50, 60} );

    setC = new SetOfNaturals();
    for (int i = 5 ; i < 50 ; i++) {
      setC.add( i * 10 );
    }
    setD = SetOfNaturals.fromArray( new int[]{30, 40, 50, 60, 10, 20} );
    setE = SetOfNaturals.fromArray( new int[]{10, 20} );
  }

  @AfterEach
  public void tearDown() {
    setA = setB = setC = setD = null;
  }

  @Test
  public void testAddElement() {

    setA.add( 99 );
    assertTrue( setA.contains( 99 ), "add: added element not found in set." );
    assertEquals( 1, setA.size() );

    setB.add( 11 );
    assertTrue( setB.contains( 11 ), "add: added element not found in set." );
    assertEquals( 7, setB.size(), "add: elements count not as expected." );
  }

  @Test
  public void testAddBadArray() {
    int[] elems = new int[]{10, 20, - 30};
    int[] elems2 = new int[]{10, 20, 20};

    // must fail with exception
    assertThrows( IllegalArgumentException.class, () -> setA.add( elems ) );
    assertThrows( IllegalArgumentException.class, () -> setA.add( elems2 ), "add: duplicated values in elements list" );
  }


  @Test
  public void testIntersectForNoIntersection() {
    assertFalse( setA.intersects( setB ), "no intersection but was reported as existing" );

  }

  @Test
  public void testIntersectForFullIntersection() {
    assertTrue( setB.intersects( setD ), "their intersection should originate a set of the same numbers" );
    assertTrue( setD.intersects( setB ), "their intersection should originate a set of the same numbers" );

  }

  @Test
  public void testIntersectForPartialIntersection() {
    assertTrue( setB.intersects( setE ), "their intersection should originate a set of the same numbers" );
    assertTrue( setE.intersects( setB ), "their intersection should originate a set of the same numbers" );

  }

  @DisplayName( "Test set of naturals size" )
  @Test
  public void testSize(){
    assertEquals( 0, (new SetOfNaturals()).size(), "Should be empty at creation" );
    assertEquals( 2, setE.size(), "Should display a size of 2" );
  }

}

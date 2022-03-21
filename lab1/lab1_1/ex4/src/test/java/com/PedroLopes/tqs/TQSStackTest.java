package com.PedroLopes.tqs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TQSStackTest {
  TQSStack<String> tqsStack;

  @org.junit.jupiter.api.BeforeEach
  void setUp() {
    tqsStack = new TQSStack();
  }

  @org.junit.jupiter.api.AfterEach
  void tearDown() {
    tqsStack = null;
  }

  @DisplayName("A Stack is empty on construction")
  @Test
  void emptyAtCreation() {
    assertTrue( tqsStack.isEmpty() );
  }

  @DisplayName("A Stack has size 0 on construction")
  @Test
  void zeroSizeAtConstruction() {
    assertEquals( tqsStack.size(), 0 );
  }

  @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
  @org.junit.jupiter.api.Test
  void push() {
    String s = "Hello 1";
    String s1 = "Hello 2";
    String s2 = "Hello 3";
    int n = 3;

    tqsStack.push( s );
    tqsStack.push( s1 );
    tqsStack.push( s2 );

    assertFalse( tqsStack.isEmpty() );
    assertEquals( tqsStack.size(), 3 );
  }

  @DisplayName("If one pushes x then pops, the value popped is x.")
  @org.junit.jupiter.api.Test
  void pop() {
    String x = "Hello 1";

    tqsStack.push( x );

    String sPop = tqsStack.pop();
    assertEquals( x, sPop );
  }

  @DisplayName("testing queue emptiness at creation and non emptiness at element pushing")
  @org.junit.jupiter.api.Test
  void isEmpty() {
    assertTrue( tqsStack.isEmpty() );

    tqsStack.push( "Hello" );
    assertFalse( tqsStack.isEmpty() );

  }

  @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
  @org.junit.jupiter.api.Test
  void peek() {

    String x = "Hello 1";

    tqsStack.push( x );

    String sPeek = tqsStack.peek();
    assertEquals( x, sPeek );
    assertEquals( tqsStack.size(), 1 );

  }

  @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
  @Test
  void popSize() {

    String s = "Hello 1";
    String s1 = "Hello 2";
    String s2 = "Hello 3";

    tqsStack.push( s );
    tqsStack.push( s1 );
    tqsStack.push( s2 );
    tqsStack.pop();
    tqsStack.pop();
    tqsStack.pop();
    assertTrue( tqsStack.isEmpty() );
    assertEquals( tqsStack.size(), 0 );
  }

  @DisplayName("Popping from an empty stack does throw a NoSuchElementException ")
  @Test
  void exceptionThrownAtPop() {
    NoSuchElementException thrown = assertThrows( NoSuchElementException.class, () -> tqsStack.pop() );
  }

  @DisplayName("Peeking from an empty stack does throw a NoSuchElementException ")
  @Test
  void exceptionThrownAtPeek() {
    NoSuchElementException thrown = assertThrows( NoSuchElementException.class, () -> tqsStack.peek() );
  }

  @DisplayName("Asserting the stack size")
  @org.junit.jupiter.api.Test
  void size() {
    String s = "Hello 1";
    String s1 = "Hello 2";
    String s2 = "Hello 3";

    tqsStack.push( s );
    tqsStack.push( s1 );
    tqsStack.push( s2 );

    assertEquals( tqsStack.size(), 3 );
    tqsStack.pop();
    assertEquals( tqsStack.size(), 2 );
  }

  @DisplayName("For bounded stacks only:pushing onto a full stack does throw an IllegalStateException")
  void TestBoundedBuffer() {
    TQSStack<Integer> tqsStackBounded = new TQSStack<>( 2 );
    tqsStackBounded.push( 1 );
    tqsStackBounded.push( 1 );

    IllegalArgumentException thrown = assertThrows( IllegalArgumentException.class, () -> tqsStackBounded.push( 2 ) );

  }

}

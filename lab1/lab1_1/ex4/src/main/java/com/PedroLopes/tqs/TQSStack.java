package com.PedroLopes.tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TQSStack<T> {
  private LinkedList<T> collection;
  private int size;
  private int full;
  private boolean bounded;
  
  public TQSStack() {
    collection = new LinkedList<>();
    bounded = false;
  }
  
  public TQSStack( int i ) {
    collection = new LinkedList<>();
    size = i;
    full = 0;
    bounded = true;
  }
  
  public void push( T e ) {
    if ( full < size || ! bounded ) {
      collection.push( e );
      full++;
    }
    else {throw new IllegalArgumentException( "Stack can't grow bigger than " + size );}
  }
  
  public T pop() {
    full--;
    return collection.pop();
  }
  
  public boolean isEmpty() {
    return collection.isEmpty();
  }
  
  public T peek() {
    if ( collection.size() == 0 ) {throw new NoSuchElementException( "Peeking an empty list" );}
    return collection.peek();
  }
  
  public Integer size() {
    return collection.size();
  }
}

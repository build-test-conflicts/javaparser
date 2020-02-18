package com.github.javaparser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class RangeTest {
  @Test void aRangeContainsItself(){
    Range r=Range.range(1,1,3,10);
    assertTrue(r.contains(r));
  }
  @Test void aRangeDoesNotStrictlyContainsItself(){
    Range r=Range.range(1,1,3,10);
    assertFalse(r.strictlyContains(r));
  }
  @Test void overlappingButNotContainedRangesAreNotOnContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,7,10);
    assertFalse(r1.contains(r2));
    assertFalse(r2.contains(r1));
  }
  @Test void overlappingButNotContainedRangesAreNotOnStrictlyContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,7,10);
    assertFalse(r1.strictlyContains(r2));
    assertFalse(r2.strictlyContains(r1));
  }
  @Test void unrelatedRangesAreNotOnContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(5,1,7,10);
    assertFalse(r1.contains(r2));
    assertFalse(r2.contains(r1));
  }
  @Test void unrelatedRangesAreNotOnStrictlyContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(5,1,7,10);
    assertFalse(r1.strictlyContains(r2));
    assertFalse(r2.strictlyContains(r1));
  }
  @Test void strictlyContainedRangesOnContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,3,4);
    assertTrue(r1.contains(r2));
    assertFalse(r2.contains(r1));
  }
  @Test void strictlyContainedRangesOnStrictlyContains(){
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,3,4);
    assertTrue(r1.strictlyContains(r2));
    assertFalse(r2.strictlyContains(r1));
  }
  @Test void containsConsiderLines(){
    Range r1=Range.range(22,9,22,29);
    Range r2=Range.range(26,19,26,28);
    assertFalse(r1.contains(r2));
    assertFalse(r2.contains(r1));
  }
  @Test void lineCountIsReturned(){
    Range r1=Range.range(1,1,5,2);
    Range r2=Range.range(26,5,57,6);
    assertEquals(5,r1.getLineCount());
    assertEquals(32,r2.getLineCount());
  }
  public RangeTest(){
  }
}

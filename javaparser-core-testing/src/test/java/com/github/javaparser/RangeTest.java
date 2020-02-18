package com.github.javaparser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
class RangeTest {
  @Test void aRangeContainsItself() throws IOException {
    Range r=Range.range(1,1,3,10);
    assertEquals(true,r.contains(r));
  }
  @Test void aRangeDoesNotStrictlyContainsItself() throws IOException {
    Range r=Range.range(1,1,3,10);
    assertEquals(false,r.strictlyContains(r));
  }
  @Test void overlappingButNotContainedRangesAreNotOnContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,7,10);
    assertEquals(false,r1.contains(r2));
    assertEquals(false,r2.contains(r1));
  }
  @Test void overlappingButNotContainedRangesAreNotOnStrictlyContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,7,10);
    assertEquals(false,r1.strictlyContains(r2));
    assertEquals(false,r2.strictlyContains(r1));
  }
  @Test void unrelatedRangesAreNotOnContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(5,1,7,10);
    assertEquals(false,r1.contains(r2));
    assertEquals(false,r2.contains(r1));
  }
  @Test void unrelatedRangesAreNotOnStrictlyContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(5,1,7,10);
    assertEquals(false,r1.strictlyContains(r2));
    assertEquals(false,r2.strictlyContains(r1));
  }
  @Test void strictlyContainedRangesOnContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,3,4);
    assertEquals(true,r1.contains(r2));
    assertEquals(false,r2.contains(r1));
  }
  @Test void strictlyContainedRangesOnStrictlyContains() throws IOException {
    Range r1=Range.range(1,1,3,10);
    Range r2=Range.range(2,1,3,4);
    assertEquals(true,r1.strictlyContains(r2));
    assertEquals(false,r2.strictlyContains(r1));
  }
  @Test void containsConsiderLines(){
    Range r1=Range.range(22,9,22,29);
    Range r2=Range.range(26,19,26,28);
    assertEquals(false,r1.contains(r2));
    assertEquals(false,r2.contains(r1));
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

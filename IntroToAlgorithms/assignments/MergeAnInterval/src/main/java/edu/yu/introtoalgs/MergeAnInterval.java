package edu.yu.introtoalgs;

import java.util.*;

public class MergeAnInterval {

  /** An immutable class, holds a left and right integer-valued pair that
   * defines a closed interval
   */
  public static class Interval implements Comparable<Interval>{
    public final int left;
    public final int right;

    /** Constructor
     * 
     * @param left the left endpoint of the interval
     * @param right the right endpoint of the interval
     */
    public Interval(int l, int r) {
      if (l >= r) throw new IllegalArgumentException("left endpoint must be strictly less than right endpoint");
      this.left = l;
      this.right = r;
    }

	@Override
	public int compareTo(Interval that) {
		// TODO Auto-generated method stub
		if (this.left < that.left) return -1;
		if (this.left > that.left) return 1; 
		return 0;
	}
	
	@Override
	public String toString() {
		return "[" + left + "," + right + "]";
	}
	
	// CODE ADDED AFTER PROFESSOR RACHMANUS EXTENSION ________________________
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + left;
		result = prime * result + right;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interval other = (Interval) obj;
		if (left != other.left)
			return false;
		if (right != other.right)
			return false;
		return true;
	}
	// END CODE ADDED AFTER PROFESSOR RACHMANUS EXTENSION _____________________

	
  } // Interval class

  /** Merges the new interval into an existing set of disjoint intervals.
   *
   * @param intervals the existing set of intervals
   * @param newInterval the interval to be added
   * @return a new set of disjoint intervals containing the original intervals
   * and the new interval, merging the new interval if necessary into existing
   * interval(s), to preseve the "disjointedness" property.
   */
  public static Set<Interval> merge
    (final Set<Interval> intervals, Interval toMerge)
  {
	  if (intervals == null || toMerge == null) throw new IllegalArgumentException("Must pass non-null args");
	  
	  Set<Interval> result = new HashSet<>();
	  if (intervals.size() == 0) {
		  result.add(toMerge);
		  return result;
	  }
	  
	  // set up left-edges ordered list and right-edges ordered list
	  List<Integer> leftEdges = new ArrayList<>();
	  List<Integer> rightEdges = new ArrayList<>();
	  for (Interval interval: intervals) {
		  leftEdges.add(interval.left);
		  rightEdges.add(interval.right);
	  }
	  Collections.sort(leftEdges);
	  Collections.sort(rightEdges);
	  
	  int toAddLeft, toAddRight;
	  
	  int leftBelongsAt = Collections.binarySearch(leftEdges, toMerge.left);
	  int rightBelongsAt = Collections.binarySearch(rightEdges, toMerge.right);
	  int indexOfLowerLeftEdge = leftBelongsAt;
	  int indexOfHigherRightEdge = rightBelongsAt;
	  
	  // find toAddLeft
	  toAddLeft = toMerge.left;
	  if (leftBelongsAt < 0 && toAddLeft > leftEdges.get(0)) { // left falls between 2 left-edges (it didn't fall on an edge, and isn't smallest edge)
		  // if returns -x, that means it is between list.get(x-2) and list.get(x-1)
		  // to check for overlap, we see if it is <= the RE corresponding to list.get(x-2)
		  indexOfLowerLeftEdge = (-1 * leftBelongsAt) - 2;
		  if (toMerge.left <= rightEdges.get(indexOfLowerLeftEdge)) {
			  toAddLeft = leftEdges.get(indexOfLowerLeftEdge);
		  }
	  }
	  
	  // find toAddRight
	  toAddRight = toMerge.right;
	  if (rightBelongsAt < 0 && toAddRight < rightEdges.get(rightEdges.size()-1)) { // right falls between 2 right-edges (it didn't fall on an edge, and isn't largest edge)
		  // if returns -x, that means it is between list.get(x-2) and list.get(x-1)
		  // to check for overlap, we see if it is >= the LE corresponding to list.get(x-1)
		  indexOfHigherRightEdge = (-1 * rightBelongsAt) - 1;
		  if (toMerge.right >= leftEdges.get(indexOfHigherRightEdge)) {
			  toAddRight = rightEdges.get(indexOfHigherRightEdge);
		  }
	  }	  
	  
	  for (int i = 0; i < leftEdges.size(); i++) {
		  if (toAddLeft > leftEdges.get(i) || toAddRight < leftEdges.get(i)) {
			  result.add(new Interval(leftEdges.get(i), rightEdges.get(i)));
		  }
	  }
	  result.add(new Interval(toAddLeft, toAddRight));
	  return result;
  }
}

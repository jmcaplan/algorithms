package edu.yu.da;

/** Skeleton file for the MaxRectangle assignment.
 *
 * @author Avraham Leff
 */

public class MaxRectangle {

    public static class Answer {
	final int max;
	final int left;
	final int right;
	final int height;

	/** Constructor: do not change signature, or implementation.
	 */
	public Answer(final int max, final int left, final int right,
		      final int height)
	{
	    assert max >= 0: "max must be non-negative: "+max;
	    assert left >= 0: "left must be non-negative: "+left;
	    assert right >= 0: "right must be non-negative: "+right;
	    assert height >= 0: "height must be non-negative: "+height;

	    this.max = max;
	    this.left = left;
	    this.right = right;
	    this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + left;
		result = prime * result + max;
		result = prime * result + right;
		return result;
	}

	@Override
	public String toString() {
		return "Answer [max=" + max + ", left=" + left + ", right=" + right + ", height=" + height + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (height != other.height)
			return false;
		if (left != other.left)
			return false;
		if (max != other.max)
			return false;
		if (right != other.right)
			return false;
		return true;
	}

	// you may add as many methods as you want
	
    }

    /** Returns the area of the biggest rectangle that can be constructed from
     * the "heights" representation.
     *
     * @param heights an array of integers which implicitly specify one or more
     * rectangles per the assignment's requirements doc.
     * @return an Answer "struct" containing the area of the biggest
     * rectangle that can be constructed, the left and right
     * x-coordinates of the selected rectangle, and the height of that
     * rectangle.
     * @throws IllegalArgumentException if null array or fewer than 2 elements
     */
    public static Answer get(final int[] heights) {
    	int leftIndex = -1, rightIndex = -1, maxArea = 0;
    	int l = 0, r = heights.length - 1;
    	while (l != r) {
    		int area = Math.min(heights[l], heights[r]) * (r-l);
			if (area > maxArea) {
				maxArea = area;
				leftIndex = l;
				rightIndex = r;
			}
			if (heights[l] > heights[r]) r-- ; 
			else l++;
    	}
    	int finalHeight = Math.min(heights[leftIndex], heights[rightIndex]);
    	return new Answer(maxArea, leftIndex, rightIndex, finalHeight);
    }
    
    /** Returns the area of the biggest rectangle that can be constructed from
     * the "heights" representation.
     *
     * @param heights an array of integers which implicitly specify one or more
     * rectangles per the assignment's requirements doc.
     * @return an Answer "struct" containing the area of the biggest
     * rectangle that can be constructed, the left and right
     * x-coordinates of the selected rectangle, and the height of that
     * rectangle.
     * @throws IllegalArgumentException if null array or fewer than 2 elements
     */
    public static Answer bruteForceGet(final int[] heights) {
    	int leftIndex = -1, rightIndex = -1, maxArea = 0;
    	for(int i = 0; i < heights.length; i++) {
    		for (int j = i + 1; j < heights.length; j++) {
    			int area = Math.min(heights[i], heights[j]) * (j-i);
    			if (area > maxArea) {
    				maxArea = area;
    				leftIndex = i;
    				rightIndex = j;
    			}
    		}
    	}
    	int finalHeight = Math.min(heights[leftIndex], heights[rightIndex]);
    	return new Answer(maxArea, leftIndex, rightIndex, finalHeight);
    }
    
    

    // add as many methods as you want
}
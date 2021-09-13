package edu.yu.introtoalgs;

import java.util.List;
import java.util.Random;

public class SequentialSecondSmallestElement {
	private int[] array;
	
	/** Constructor .
	 *
	 * @param array input that we ’ll search
	 * for the second smallest element ,
	 * cannot be null .
	 * @param fractionToApplySequentialCutoff
	 * a fraction of the original number of
	 * array elements : when the remaining
	 * elements dip below this fraction , the
	 * fork - join algorithm will process using
	 * a sequential algorithm . Cannot be
	 * less than 0.0 (fork - join processing
	 * for all but arrays of size 1) and
	 * cannot exceed 1.0 (no fork - join
	 * processing will take place at all).
	 */
	public SequentialSecondSmallestElement( final int [] array) {
		this.array = array;
	}
	
	
	
	
	/** Returns the second smallest
	 * unique element of the input array .
	 *
	 * Example : if array is [1 , 7 , 4 , 3 , 6] ,
	 * then secondSmallest ( array ) == 3.
	 *
	 * Example : if array is [6 , 1 , 4 , 3 , 5 ,
	 * 2 , 1] , secondSmallest ( array ) == 2.
2
	 *
	 * @return second smallest unique element
	 * of the input
	 * @throws IllegalArgumentException if
	 * the input doesn’t contain a minimum of
	 * two unique elements .
	 */
	public int secondSmallest () {
		int smallest = Integer.MAX_VALUE;
		int secondSmallest = Integer.MAX_VALUE;
		for (int i = 0; i < this.array.length; i++) {
			int current = array[i];
			if (current < smallest) {
				secondSmallest = smallest;
				smallest = current;
			}
			else if (current != smallest && current < secondSmallest) {
				secondSmallest = current;
			}
		}
		return secondSmallest;
	}
	
	// experiment to prove this implementation is O(n)
	public static void main(String[] args) {
		String results = "";
		long t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;
		for (int i = (int)Math.pow(2, 22); i < Math.pow(2, 29); i *= 2 ) {
			System.out.println("\nInitializing array of size " + i + "...\n");
			int[] array = new int[i];
			Random random = new Random();
			for (int k = 0; k < i; k++) {
				array[k] = random.nextInt(100);
			}
			System.out.println("\nRunning alg for array of size " + i + "...\n");
			SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(array);
				t0 = System.currentTimeMillis();
				ssse.secondSmallest();
				t1 = System.currentTimeMillis();
				t2 = System.currentTimeMillis();
				ssse.secondSmallest();
				t3 = System.currentTimeMillis();
				t4 = System.currentTimeMillis();
				ssse.secondSmallest();
				t5 = System.currentTimeMillis();
				t6 = System.currentTimeMillis();
				ssse.secondSmallest();
				t7 = System.currentTimeMillis();
				t8 = System.currentTimeMillis();
				ssse.secondSmallest();
				t9 = System.currentTimeMillis();
				t10 = System.currentTimeMillis();
				ssse.secondSmallest();
				t11 = System.currentTimeMillis();
			long avgTime = ( (t1-t0)+(t3-t2)+(t5-t4)+(t7-t6)+(t9-t8)+(t11-t10) ) / 6;
			results += String.format("\nInput size %d took %d ms on average\n", i, avgTime);
		}
		System.out.println(results);
	}
}

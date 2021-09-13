package edu.yu.introtoalgs;

import java.util.concurrent.RecursiveTask;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


public class SecondSmallestElementFJ {
	private int[] array;
	private int threshold;
	
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
	public SecondSmallestElementFJ
	( final int [] array ,
			final double
			fractionToApplySequentialCutoff ) {
		if (fractionToApplySequentialCutoff < 0.0 || fractionToApplySequentialCutoff > 1.0) {
			throw new IllegalArgumentException("fractfractionToApplySequentialCutoff must be"
					+ "between 0.0 and 1.0 inclusive, user input " + fractionToApplySequentialCutoff);
		}
		this.array = array;
		this.threshold = (int)Math.ceil(array.length * fractionToApplySequentialCutoff);
		if (threshold == 0) threshold++;
	}
	
	class TwoSmallestFJTask extends RecursiveTask<Integer[]> {
		int[] array;
		int low;
		int high;
		
		public TwoSmallestFJTask(int[] array, int low, int high) {
			this.array = array;
			this.low = low;
			this.high = high;
		}
		
		@Override
		protected Integer[] compute() {
			if (this.high - this.low <= threshold) {
				return computeSequentialLowestTwo(array, low, high);
			}
			else {
				TwoSmallestFJTask left = 
						new TwoSmallestFJTask(array, low, (high+low)/2);
				TwoSmallestFJTask right = 
						new TwoSmallestFJTask(array, (high+low)/2, high);
				left.fork();
				Integer[] rightTuple = right.compute();
				Integer[] leftTuple = left.join();
				return mergeTwoTuples(leftTuple, rightTuple);
			}
		}
	}
	
	private Integer[] computeSequentialLowestTwo(int[] array, int low, int high) {
		int smallest = Integer.MAX_VALUE;
		int secondSmallest = Integer.MAX_VALUE;
		for (int i = low; i < high; i++) {
			int current = array[i];
			if (current < smallest) {
				secondSmallest = smallest;
				smallest = current;
			}
			else if (current != smallest && current < secondSmallest) {
				secondSmallest = current;
			}
		}
		return new Integer[] {smallest,secondSmallest};
	}
	
	private Integer[] mergeTwoTuples(Integer[] a, Integer[] b) {
		int smallest, secondSmallest;
		if (a[0] < b[0]) {
			smallest = a[0];
			//secondSmallest = Math.min(a[1], b[0]);
			secondSmallest = (a[1] < b[0]) ? a[1] : b[0];
		}
		else if (b[0] < a[0]) {
			smallest = b[0];
			//secondSmallest = Math.min(b[1], a[0]);
			secondSmallest = (b[1] < a[0]) ? b[1] : a[0];
		}
		else {
			smallest = a[0];
			//secondSmallest = Math.min(a[1], b[1]);
			secondSmallest = (a[1] < b[1]) ? a[1] : b[1];

		}
		return new Integer[] {smallest, secondSmallest};
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
		int parallelism = Runtime.getRuntime().availableProcessors() * 1;
		ForkJoinTask<Integer[]> task = 
				new TwoSmallestFJTask(this.array, 0, this.array.length);
		final ForkJoinPool fjPool = new ForkJoinPool(parallelism);
		Integer[] globalSmallestTwo = fjPool.invoke(task);
		fjPool.shutdown();
		if (globalSmallestTwo[1] == Integer.MAX_VALUE || globalSmallestTwo[0] == Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Did not find two unique elements");
		}
		return globalSmallestTwo[1];
	}
	
	// Runs a thorough experiment comparing sequential and FJ algs, for different input sizes and threshold values
	public static void main(String[] args) {
		String finalResults = "";
		int[] pows2 = {15, 22, 26, 28};
		double[] threshs = {1, 0.9, 0.5, 0.25, 0.1, 0.01, 0.001, 0.0};
		for (int pow2: pows2) {
			long t0, t1, t2, t3;
			int result;
			finalResults += "\nStarting results for pow2 = "+pow2 + " ...";
			System.out.println("Initializing giant array...");
			long ti0, ti1;
			ti0 = System.currentTimeMillis();
			int N = (int)Math.pow(2, pow2);
			int[] bigArr = new int[N];
			Random random = new Random();
			for (int i = 0; i < N; i++) {
				if (i == N/4) System.out.println("\nA quarter done...\n");
				else if (i == N/2) System.out.println("Halfway there...\n");
				bigArr[i] = random.nextInt(100);
			}
			ti1 = System.currentTimeMillis();
			System.out.println("Finished initialization, took " + (ti1-ti0) +"ms\n");
			// run sequential once
			SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(bigArr);
			System.out.println("Starting Sequential version...");
			t2 = System.currentTimeMillis();
			result = ssse.secondSmallest();
			t3 = System.currentTimeMillis();
			System.out.println("Finished Sequential version after "+ (t3-t2) +" ms\nResult was: "+result);
			// run FJ with different thresholds
			for (double thresh: threshs) {
				System.out.println("\n\nEXPERIMENT WITH POW2 OF "+pow2+" and thresh of "+thresh+"...\n\n");
				
				SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(bigArr, thresh);
								
				System.out.println("Starting FJ version...");
				t0 = System.currentTimeMillis();
				result = ssefj.secondSmallest();
				t1 = System.currentTimeMillis();
				System.out.println("Finished FJ version after "+ (t1-t0) +" ms\nResult was: "+result);
				System.out.println();
				
				double ratio = (t3-t2)/((double)(t1-t0));
				finalResults += (String.format("\nPow2: %d, Thresh: %f, FJ time: %d, Seq time: %d, Ratio: %f\n", pow2, thresh, (t1-t0), (t3-t2), ratio ));
			}
		}
		System.out.println(finalResults);	
	}
}

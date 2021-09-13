package edu.yu.da.test;

import edu.yu.da.*;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class TemplateTest {
	
	private boolean PRINTING_ON = false;
	
	private final int[] US_Denominations = {0,1,5,10,25,100};
	
	public void runTestAndAssert(int[] denominations, int N, int[] expectedPayout) {
		if (PRINTING_ON) System.out.println(String.format(
				"\n*****************\nRunning Test with denominations=%s, N=%d:", 
				Arrays.toString(denominations),
				N));
		int[][] c = DPMakingChange.makeChange(denominations, N);
		if (PRINTING_ON) System.out.println("\nc:\n");
		if (PRINTING_ON) for (int[] row : c) System.out.println(Arrays.toString(row));
		
		int[] payout = DPMakingChange.payOut(c, denominations, N);
		if (PRINTING_ON) System.out.println("\nPayout:\n");
		if (PRINTING_ON) System.out.println(Arrays.toString(payout));
		
		assertTrue("payout did not have same number of coins as expected", 
				    sameNumCoins(payout, expectedPayout));
		assertTrue("payout did not add up to target change", 
				    isValidChange(payout, denominations, N));
	}
	
	public long runTimeTest(int n, int N) {
		// setup
		int[] denominations = new int[n];
		for(int i = 0; i < n; i++) denominations[i] = i + 1;
		
		// time test
		long t0 = System.currentTimeMillis();
		DPMakingChange.payOut(DPMakingChange.makeChange(denominations, N), denominations, N);
		long t1 = System.currentTimeMillis();
		return t1-t0;
	}
	
	public int[] greedyUSChange(int N) {
		int[] result = new int[US_Denominations.length];
		for(int i = result.length - 1; i > 0; i--) {
			N -= (result[i] = N/US_Denominations[i]) * US_Denominations[i];
		}
		return result;
	}
	
	@Test
	public void performanceTest() {
		int n = 2, N = 4;
		double[] times = new double[12];
		for(int i = 0; i < times.length; i++) {
			int trys = 5; double totalTime = 0;
			for(int j = 0; j < trys; j++) totalTime += runTimeTest(n, N);
			times[i] = totalTime/trys;
			n *= 2;
			N *= 2;
		}
		if (PRINTING_ON) System.out.println("\nPerformance Times:\n" + Arrays.toString(times));
		double[] ratios = new double[times.length];
		for(int i = 1; i < times.length; i++) ratios[i] = times[i] / times[i-1];
		if (PRINTING_ON) System.out.println("\nPerformance Ratios:\n" + Arrays.toString(ratios));
	}
	
	private boolean isValidChange(int[] payout, int[] denominations, int N) {
		int change = 0;
		for (int i = 0; i < payout.length; i++) change += (payout[i] * denominations[i]);
		return change == N;
	}

	private boolean sameNumCoins(int[] payout, int[] expectedPayout) {
		int payoutCoins = 0, expectedPayoutCoins = 0;
		for (int i = 0; i < payout.length; i++) {
			payoutCoins += payout[i];
			expectedPayoutCoins += expectedPayout[i];
		}
		return payoutCoins == expectedPayoutCoins;
	}

	@Test
	public void professorTrace() {
		int[] denominations = {0,1,7,10};
		int N = 15;
		int[] expectedPayout = {0,1,2,0};
		runTestAndAssert(denominations, N, expectedPayout);
	}
	
	@Test
	public void easyTrace() {
		int[] denominations = {0,5,3,2};
		int N = 14;
		int[] expectedPayout = {0,2,0,2};
		runTestAndAssert(denominations, N, expectedPayout);
	}
	
	@Test
	public void oneCoinWorksTrace() {
		int[] denominations = {0,5};
		int N = 15;
		int[] expectedPayout = {0,3};
		runTestAndAssert(denominations, N, expectedPayout);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDenominationsFails() {
		int[] denominations = {0};
		int N = 15;
		int[] expectedPayout = null;
		runTestAndAssert(denominations, N, expectedPayout);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noValidPayoutFails() {
		int[] denominations = {0, 5};
		int N = 11;
		int[] expectedPayout = null;
		runTestAndAssert(denominations, N, expectedPayout);
	}
	
	@Test
	public void testAgainstGreedyUS() {
		Random r = new Random(613);
		for(int i = 0; i < 100; i++) {
			int N = r.nextInt(900) + 100;
			runTestAndAssert(US_Denominations, N, greedyUSChange(N));	
		}
	}
	
}

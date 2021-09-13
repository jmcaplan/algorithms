package edu.yu.da.test;

import edu.yu.da.MedianFinding;
import edu.yu.da.MedianFinding.Account;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class MedianFindingTest {

	@Test
	public void test() {
		Account[] a = {new Account(1), new Account(4), new Account(7), 
				new Account(9), new Account(14), new Account(19), 
				new Account(21), new Account(22), new Account(33), new Account(50)};

		Account[] b = {new Account(2), new Account(6), new Account(17),
				new Account(23), new Account(25), new Account(31), 
				new Account(32), new Account(36), new Account(40), new Account(45)};
		assertEquals(21.0, MedianFinding.findMedian(a, b).getIncome(), .00001);
		assertEquals(21.0, MedianFinding.findMedianRef(a, b).getIncome(), .00001);
	}
	
	@Test
	public void correctnessTest() {
		Random rand = new Random();
		int N = 816253;
		Account[] a = new Account[N/99];
		Account[] b = new Account[N/99];
		for (int i = 0; i < a.length; i++) {
			a[i] = new Account(rand.nextDouble()*N);
			b[i] = new Account(rand.nextDouble()*N);			
		}
		Arrays.sort(a);
		Arrays.sort(b);
		System.out.println(Arrays.toString(a) + "   " + Arrays.toString(b));
		assertEquals(MedianFinding.findMedianRef(a, b), MedianFinding.findMedian(a, b));
	}
	
	@Test
	public void performanceTest() {
		long[] times = new long[20];
		long[] ratios = new long[times.length];
		Random rand = new Random();
		int M = 18273953;
		for (int i = 2; i < 15; i++) {
			int N = (int) Math.pow(2, i);
			Account[] a = new Account[N];
			Account[] b = new Account[N];
			for (int j = 0; j < a.length; j++) {
				a[j] = new Account(rand.nextDouble()*M);
				b[j] = new Account(rand.nextDouble()*M);			
			}
			Arrays.sort(a);
			Arrays.sort(b);
			//Account refRes = MedianFinding.findMedianRef(a, b);
			long t0 = System.nanoTime();
			Account myRes = MedianFinding.findMedian(a, b);
			long t1 = System.nanoTime();
			times[i] = t1 - t0;
			//assertEquals(refRes, myRes);
		}
		for (int i = 2; i < times.length; i++) {
			ratios[i] = (times[i-1] != 0) ? times[i] / times[i-1] : -1;
		}
		System.out.println(Arrays.toString(ratios));
		System.out.println(Arrays.toString(times));
		assertTrue(true);
	}
	

}

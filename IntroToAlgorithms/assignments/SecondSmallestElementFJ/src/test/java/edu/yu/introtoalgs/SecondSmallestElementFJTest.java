package edu.yu.introtoalgs.test;

import java.util.Random;
import edu.yu.introtoalgs.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class SecondSmallestElementFJTest {
     
	@Test
    public void simpleTest1(){
		int[] arr = {6 , 1 , 4 , 3 , 5 ,2 , 1};
		SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(arr);
		assertTrue(ssse.secondSmallest() == 2);
    }
	
	@Test (expected = IllegalArgumentException.class)
	public void noTwoUnique (){
		int[] arr = new int[1000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 613;
		}
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 0.2);
		ssefj.secondSmallest();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void illegalThresholdBelow (){
		int[] arr = {1,2};
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, -0.1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void illegalThresholdAbove (){
		int[] arr = {1,2};
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 1.1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void arraySize1(){
		int[] arr = {0};
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 0.2);
		ssefj.secondSmallest();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void arraySize0(){
		int[] arr = {};
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 0.2);
		ssefj.secondSmallest();
	}
	
	@Test 
	public void onlyTwoUnique(){
		int[] arr = new int[1000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 613;
		}
		arr[613] = 42;
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 0.2);
		assertTrue(ssefj.secondSmallest() == 613);
		arr[613] = 614;
		SecondSmallestElementFJ ssefj2 = new SecondSmallestElementFJ(arr, 0.2);
		assertTrue(ssefj2.secondSmallest() == 614);
	}
	
	@Test
    public void simpleFJTest1(){
		int[] arr = {6 , 1 , 4 , 3 , 5 ,2 , 1};
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(arr, 0.2);
		assertTrue(ssefj.secondSmallest() == 2);
    }
	
	
	@Test
    public void simpleTest2(){
		int[] arr = {1 , 7 , 4 , 3 , 6};
		SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(arr);
		assertTrue(ssse.secondSmallest() == 3);
    }
	
	@Test
    public void loadTest(){
		Integer[] arr = new Integer[1000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i%10;
		}
		List<Integer> list = Arrays.asList(arr);
		Collections.shuffle(list);
		list.toArray(arr);
		int[] primArr = new int[1000000];
		for (int i = 0; i < arr.length; i++) {
			primArr[i] = arr[i];
		}
		SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(primArr);
		assertTrue(ssse.secondSmallest() == 1);
    }
	
	@Test
    public void loadFJTest(){
		Integer[] arr = new Integer[1000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i%10;
		}
		List<Integer> list = Arrays.asList(arr);
		Collections.shuffle(list);
		list.toArray(arr);
		int[] primArr = new int[1000000];
		for (int i = 0; i < arr.length; i++) {
			primArr[i] = arr[i];
		}
		SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(primArr, 0.5);
		assertTrue(ssefj.secondSmallest() == 1);
    }
	
	@Test
	public void largeInputAccuracyTest() {
		System.out.println("Running test with large input size and multiple iterations, may take a few seconds...");
		for (int x = 0; x < 10; x++) {
			int N = (int)Math.pow(2, 24);
			int[] bigArr = new int[N];
			Random random = new Random();
			for (int i = 0; i < N; i++) {
				bigArr[i] = random.nextInt(100);
			}
			SecondSmallestElementFJ ssefj = new SecondSmallestElementFJ(bigArr, 0.1);
			SequentialSecondSmallestElement ssse = new SequentialSecondSmallestElement(bigArr);
			assertTrue(ssefj.secondSmallest() == ssse.secondSmallest()); 
		}
		System.out.println("Done with lengthy test!");
	}
	
	
}

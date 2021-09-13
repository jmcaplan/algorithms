package edu.yu.introtoalgs.test;

import edu.yu.introtoalgs.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;


public class IndexMinPQTest {
     
	@Test
    public void simpleIteratorTest(){
        System.out.println("\n\n************** "
        		+ "\nVisual confirmation of simple iterator test...\n"); 
		int N = 10;
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);
        for (int i = 9; i >= 0; i--) {
        	pq.insert(i, i*10);
        }
        for (Integer i: pq)
        	System.out.println(i + " ");
    }
	
	@Test
	public void iteratorTest() {
		int N = 10723;
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);
	    Integer[] arr = new Integer[N];
	    Random rand = new Random();
	    for (int i = 0; i < N; i++) {
	    	int num = rand.nextInt(N);
	    	arr[i] = num;
	    	pq.insert(i, num);
	    }
	    // create a sorted copy of the Integer[]
	    Integer[] arrCopy = Arrays.copyOf(arr, arr.length);
	    Arrays.sort(arrCopy);
	    int count = 0;
	    for (Integer i : pq) {
	    	// check that as we iterate through the keys in the PQ,
	    	// they correspond to the same random Integers
	    	// as in the sorted array-copy values
	    	assertEquals(arr[i], arrCopy[count++]);
	    }	
	}
	
	@Test
    public void keyOfTest(){
		int N = 613;
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);
        Integer[] arr = new Integer[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
        	int num = rand.nextInt(N);
        	arr[i] = num;
        	pq.insert(i, num);
        }
        for (int i = 0; i < N; i++) assertEquals(arr[i], pq.keyOf(i));
    }
	
	@Test
    public void sizeAndEmptyTest(){
		// do 613 insertions, 365 deletions, should be left with 248 :)
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(1000);
        Integer[] arr = new Integer[613];
        Random rand = new Random();
        for (int i = 0; i < 613; i++) {
        	int num = rand.nextInt(1000);
        	arr[i] = num;
        	pq.insert(i, num);
        }
        for (int i = 0; i < 365; i++) {
        	pq.delMin();
        }
        assertEquals(248, pq.size());
        assertFalse(pq.isEmpty());
        
        // now clear it out
        for (int i = 0; i < 248; i++) {
        	pq.delMin();
        }
        assertTrue(pq.isEmpty());
    }
	
	@Test
    public void heapSortTest(){
        System.out.println("\n\n************** "
        		+ "\nTimes, because I am curious...\n");
		for (int i = 10; i < 20; i++) heapSortRunner((int)Math.pow(2, i));
    }
	
	public void heapSortRunner(int N) {
		long t0, t1, t2, t3;
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(N);
        Integer[] arr = new Integer[N];
        Random rand = new Random();
        t0 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
        	int num = rand.nextInt(N);
        	arr[i] = num;
        	pq.insert(i, num);
        }
        t1 = System.currentTimeMillis();
        Arrays.sort(arr);
        t2 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
        	assertEquals(pq.minKey(), arr[i]);
        	pq.delMin();
        }
        t3 = System.currentTimeMillis();
        System.out.println(String.format("N = %d | Insertion time: %d ms, Sort Time: %d ms", N, t1-t0, t3-t2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void indexOutOfRangeBelowTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.insert(-1, 1);
	}	
	
	@Test (expected = IllegalArgumentException.class)
	public void indexOutOfRangeAboveTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.insert(614, 1);
	}	
	
	@Test (expected = IllegalArgumentException.class)
	public void insertUsedIndexTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.insert(1, 1);
		pq.insert(1, 2);
	}	
	
	@Test (expected = IllegalArgumentException.class)
	public void insertNullKeyTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.insert(1, null);
	}	
	
	@Test (expected = IllegalStateException.class)
	public void keyOfUnusedIndexTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.keyOf(1);
	}	
	
	@Test (expected = IllegalStateException.class)
	public void delMinOnEmptyTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.delMin();
	}
	
	@Test (expected = IllegalStateException.class)
	public void minIndexOnEmptyTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.minIndex();
	}
	
	@Test (expected = IllegalStateException.class)
	public void minKeyOnEmptyTest() {
		IndexMinPQ<Integer> pq = new IndexMinPQ<>(613);
		pq.minKey();
	}
	
	/*
	
	@Test (expected = IllegalArgumentException.class)
	public void coordOutOfRangeBelowTest() {
		
	}	
	*/
}

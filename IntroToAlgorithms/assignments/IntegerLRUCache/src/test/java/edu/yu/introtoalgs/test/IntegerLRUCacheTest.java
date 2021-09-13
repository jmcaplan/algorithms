package edu.yu.introtoalgs.test;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.yu.introtoalgs.IntegerLRUCache;;

public class IntegerLRUCacheTest {
     
	@Test
    public void getOnNotCachedTest(){
		IntegerLRUCache cache = new IntegerLRUCache(4);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		assertTrue(cache.get(613) == null);
    }
	
	@Test
    public void putPreviousValueReturnedTest(){
		IntegerLRUCache cache = new IntegerLRUCache(4);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		assertTrue(20 == cache.put(2, 25));
    }
	
	@Test
    public void simplePutAndRetrieveTest(){
		IntegerLRUCache cache = new IntegerLRUCache(4);
		cache.put(1, 10);
		assertTrue(cache.get(1) == 10);
    }
	
	@Test
    public void simpleNodeCountTest(){
		IntegerLRUCache cache = new IntegerLRUCache(5);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		cache.put(4, 40);
		cache.put(5, 50);
		assertEquals(cache.nodeCount, 5);
    }
	
	@Test
    public void simpleLRUClearingTest(){
		IntegerLRUCache cache = new IntegerLRUCache(3);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		cache.put(4, 40);
		cache.put(5, 50);
		assertEquals(3, cache.nodeCount);
		String expected = "Map: {3=(3:30), 4=(4:40), 5=(5:50)}\n" + 
				"Queue (MRU on the left): (5:50),(4:40),(3:30),";
		assertEquals(expected, cache.toString());
    }
	
	@Test
	public void simpleLRURClearingWithUpdateTest(){
		IntegerLRUCache cache = new IntegerLRUCache(3);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		cache.put(4, 40);
		cache.put(5, 50);
		cache.put(1, 15);
		assertEquals(3, cache.nodeCount);
		String expected = "Map: {1=(1:15), 4=(4:40), 5=(5:50)}\n" + 
				"Queue (MRU on the left): (1:15),(5:50),(4:40),";
		assertEquals(expected, cache.toString());
    }
	
	@Test
	public void simpleRemoveTest(){
		IntegerLRUCache cache = new IntegerLRUCache(3);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		cache.remove(2);
		assertEquals(2, cache.nodeCount);
		String expected = "Map: {1=(1:10), 3=(3:30)}\n" + 
				"Queue (MRU on the left): (3:30),(1:10),";
		assertEquals(expected, cache.toString());
    }
	
	@Test
    public void removeReturnsValueTest(){
		IntegerLRUCache cache = new IntegerLRUCache(2);
		cache.put(1, 10);
		assertTrue(cache.remove(1) == 10);
    }
	
	@Test
    public void removeNoOpReturnsNullTest(){
		IntegerLRUCache cache = new IntegerLRUCache(2);
		assertNull(cache.remove(1));
    }
	
	@Test
    public void removeNonIntegerThrowsIAETest(){
		IntegerLRUCache cache = new IntegerLRUCache(2);
		try {
			cache.remove(new Double(1));
			assertTrue(false);
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
    }
	
	@Test
	public void loadTest() {
		IntegerLRUCache cache = new IntegerLRUCache(80);
		for (int i = 0; i < 100; i++) {
			cache.put(i, -1*i);
		}
		assertEquals(80, cache.nodeCount);
		assertEquals(null, cache.get(19));
	}
	
	@Test
	public void getReordersQueueTest(){
		IntegerLRUCache cache = new IntegerLRUCache(3);
		cache.put(1, 10);
		cache.put(2, 20);
		cache.put(3, 30);
		String expectedBefore = "Map: {1=(1:10), 2=(2:20), 3=(3:30)}\n" + 
				"Queue (MRU on the left): (3:30),(2:20),(1:10),";
		String actualBefore = cache.toString();
		cache.get(2);
		String expectedAfter = "Map: {1=(1:10), 2=(2:20), 3=(3:30)}\n" + 
				"Queue (MRU on the left): (2:20),(3:30),(1:10),";
		String actualAfter = cache.toString();
		assertEquals(expectedBefore, actualBefore);
		assertEquals(expectedAfter, actualAfter);
    }
	

	
}

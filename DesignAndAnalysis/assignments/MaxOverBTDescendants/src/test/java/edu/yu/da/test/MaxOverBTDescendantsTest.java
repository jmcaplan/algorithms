package edu.yu.da.test;

import edu.yu.da.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaxOverBTDescendantsTest {

	@Test
	public void singleNodeTest() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(10, 3, 3.0);
		double[] result = mobtd.maxOverAllBTDescendants();
		assertEquals(10, result.length);
		assertEquals(3.0, result[3], 0.001);
	}
	
	@Test
	public void tinyTestUpdateParent() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(10, 3, 3.0);
		mobtd.addChild(3, 2, 20.0);
		double[] result = mobtd.maxOverAllBTDescendants();
		assertEquals(10, result.length);
		assertEquals(20.0, result[3], 0.001);
		// making sure cleanup worked
		result = mobtd.maxOverAllBTDescendants();
		assertEquals(10, result.length);
		assertEquals(20.0, result[3], 0.001);
	}
	
	@Test
	public void tinyTestDoNotUpdateParent() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(10, 3, 3.0);
		mobtd.addChild(3, 2, 2.0);
		double[] result = mobtd.maxOverAllBTDescendants();
		assertEquals(10, result.length);
		assertEquals(3.0, result[3], 0.001);
		// making sure cleanup worked
		result = mobtd.maxOverAllBTDescendants();
		assertEquals(10, result.length);
		assertEquals(3.0, result[3], 0.001);
	}
	
	@Test
	public void sanityTest() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(16, 5, 100);
		mobtd.addChild(5, 3, 110);
		mobtd.addChild(3, 11, 23);
		mobtd.addChild(11, 8, 22);
		mobtd.addChild(11, 0, 120);
		mobtd.addChild(0, 12, 30);
		mobtd.addChild(12, 4, 40);
		mobtd.addChild(12, 9, 25);
		mobtd.addChild(8, 15, 21);
		mobtd.addChild(5, 1, 80);
		mobtd.addChild(1, 10, 50);
		mobtd.addChild(1, 2, 90);
		mobtd.addChild(10, 6, 55);
		mobtd.addChild(10, 7, 56);
		double[] result = mobtd.maxOverAllBTDescendants();
		double[] expected = {120, 90, 90, 120, 40, 120, 55, 56, 22,25, 56, 120, 40, 0, 0, 21};
		for (int i = 0; i < Math.max(result.length, expected.length); i++) 
			assertEquals(expected[i], result[i], 0.001);
		// make sure cleanup works
		result = mobtd.maxOverAllBTDescendants();
		for (int i = 0; i < Math.max(result.length, expected.length); i++) 
			assertEquals(expected[i], result[i], 0.001);
	}
	
	@Test
	public void sanityTest2() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(16, 5, 100);
		mobtd.addChild(5, 3, 110);
		mobtd.addChild(3, 11, 23);
		mobtd.addChild(11, 8, 22);
		mobtd.addChild(11, 0, 12); // only change from above, 12 not 120
		mobtd.addChild(0, 12, 30);
		mobtd.addChild(12, 4, 40);
		mobtd.addChild(12, 9, 25);
		mobtd.addChild(8, 15, 21);
		mobtd.addChild(5, 1, 80);
		mobtd.addChild(1, 10, 50);
		mobtd.addChild(1, 2, 90);
		mobtd.addChild(10, 6, 55);
		mobtd.addChild(10, 7, 56);
		double[] result = mobtd.maxOverAllBTDescendants();
		double[] expected = {40, 90, 90, 110, 40, 110, 55, 56, 22,25, 56, 40, 40, 0, 0, 21};
		for (int i = 0; i < Math.max(result.length, expected.length); i++) 
			assertEquals(expected[i], result[i], 0.001);
		// make sure cleanup works
		result = mobtd.maxOverAllBTDescendants();
		for (int i = 0; i < Math.max(result.length, expected.length); i++) 
			assertEquals(expected[i], result[i], 0.001);
	}
	
	@Test
	public void errorChecking() {
		MaxOverBTDescendants mobtd = new MaxOverBTDescendants(16, 5, 100);
		mobtd.addChild(5, 3, 110);
		mobtd.addChild(3, 11, 23);
		mobtd.addChild(11, 8, 22);
		mobtd.addChild(11, 0, 12); // only change from above, 12 not 120
		mobtd.addChild(0, 12, 30);
		mobtd.addChild(12, 4, 40);
		mobtd.addChild(12, 9, 25);
		mobtd.addChild(8, 15, 21);
		mobtd.addChild(5, 1, 80);
		mobtd.addChild(1, 10, 50);
		mobtd.addChild(1, 2, 90);
		mobtd.addChild(10, 6, 55);
		mobtd.addChild(10, 7, 56);
	}

}

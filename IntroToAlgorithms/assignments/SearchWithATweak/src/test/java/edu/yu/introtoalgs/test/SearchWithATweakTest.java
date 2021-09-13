package edu.yu.introtoalgs.test;

import edu.yu.introtoalgs.SearchWithATweak;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchWithATweakTest {
    	
	/////// Find First Instance Tests ///////
	
	List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
	List<Integer> list2 = Arrays.asList(1, 2, 3, 3, 5);	
	List<Integer> list3 = Arrays.asList(1, 3, 3, 4, 5);
	List<Integer> list4 = Arrays.asList(1, 2, 3, 5, 5);	
	List<Integer> list5 = Arrays.asList(1, 1, 3, 4, 5);	
	List<Integer> list6 = Arrays.asList(1, 2, 3, 4, 5, 6);	
	List<Integer> list7 = Arrays.asList(1, 2, 2, 2, 3, 4, 4, 5, 5, 6, 8, 9, 11, 20, 32, 60, 60, 60, 613, 613);	
	
	List<Integer> list21 = Arrays.asList(10, 9, 8, 7, 7, 7, 7, 7, 7, 7, 7);
	List<Integer> list22 = Arrays.asList(7, 7, 7, 7, 7, 7, 7, 7, 4, 3, 2, 1, -25);
	List<Integer> list23 = Arrays.asList(20, 18, 17, 15, 7, 7, 7, 7, 7, 7, 7, 7, 4, 3, 2, 1, -25);
	
	@Test
	public void improvementTest1() {
		assertEquals(3, SearchWithATweak.findFirstInstance(list21, 7));
		Collections.reverse(list21);
		assertEquals(0, SearchWithATweak.findFirstInstance(list21, 7));
	}
	
	@Test
	public void improvementTest2() {
		assertEquals(0, SearchWithATweak.findFirstInstance(list22, 7));
		Collections.reverse(list22);
		assertEquals(5, SearchWithATweak.findFirstInstance(list22, 7));
	}
	
	@Test
	public void improvementTest3() {
		assertEquals(4, SearchWithATweak.findFirstInstance(list23, 7));
		Collections.reverse(list23);
		assertEquals(5, SearchWithATweak.findFirstInstance(list23, 7));
	}
	
	
	
	@Test
	public void loadTest1() {
		assertEquals(0, SearchWithATweak.findFirstInstance(list7, 1));
		Collections.reverse(list7);
		assertEquals(19, SearchWithATweak.findFirstInstance(list7, 1));
	}
	
	@Test
	public void loadTest2() {
		assertEquals(18, SearchWithATweak.findFirstInstance(list7, 613));
		Collections.reverse(list7);
		assertEquals(2, SearchWithATweak.findFirstInstance(list7, 60));
	}
	
	@Test
    public void simpleOddInputTest(){
        //assertEquals(0, SearchWithATweak.findFirstInstance(list1, 1));
        assertEquals(1, SearchWithATweak.findFirstInstance(list1, 2));
        assertEquals(2, SearchWithATweak.findFirstInstance(list1, 3));
        assertEquals(3, SearchWithATweak.findFirstInstance(list1, 4));
        assertEquals(4, SearchWithATweak.findFirstInstance(list1, 5));
    }
	
	@Test
    public void simpleEvenInputTest(){
        assertEquals(0, SearchWithATweak.findFirstInstance(list6, 1));
        assertEquals(1, SearchWithATweak.findFirstInstance(list6, 2));
        assertEquals(2, SearchWithATweak.findFirstInstance(list6, 3));
        assertEquals(3, SearchWithATweak.findFirstInstance(list6, 4));
        assertEquals(4, SearchWithATweak.findFirstInstance(list6, 5));
        assertEquals(5, SearchWithATweak.findFirstInstance(list6, 6));
    }
	
	@Test
	public void keyAbsentTest() {
		assertEquals(-1, SearchWithATweak.findFirstInstance(list1, 6));
	}
	
	@Test
	public void rightDuplicateTest() {
		assertEquals(2, SearchWithATweak.findFirstInstance(list2, 3));
	}
	
	@Test
	public void leftDuplicateTest() {
		assertEquals(1, SearchWithATweak.findFirstInstance(list3, 3));
	}
	
	@Test
	public void rightAwayFromCenterDuplicateTest() {
		assertEquals(3, SearchWithATweak.findFirstInstance(list4, 5));
	}
	
	@Test
	public void leftAwayFromCenterDuplicateTest() {
		assertEquals(0, SearchWithATweak.findFirstInstance(list5, 1));
	}
	
	
	/////// Element Equal to Index Tests ///////
	
	List<Integer> list8 = Arrays.asList(0, 1, 2, 3, 4);
	List<Integer> list9 = Arrays.asList(0, 1, 2, 3, 4, 5);
	    // more odd cases:
	List<Integer> list10 = Arrays.asList(0, 10, 20, 30, 40); // leftmost is match
	List<Integer> list11 = Arrays.asList(-1, 0, 1, 2, 4); // rightmost is match
	List<Integer> list12 = Arrays.asList(-1, 1, 20, 30, 40); // mid-left is match
	List<Integer> list13 = Arrays.asList(-1, 0, 1, 3, 40); // mid-right is match
		// more even cases:
	List<Integer> list14 = Arrays.asList(0, 10, 20, 30, 40, 50); // leftmost is match
	List<Integer> list15 = Arrays.asList(-1, 0, 1, 2, 3, 5); // rightmost is match
	List<Integer> list16 = Arrays.asList(-1, 1, 20, 30, 40, 50); // far-left is match
	List<Integer> list17 = Arrays.asList(-1, 0, 2, 30, 40, 50); // left-mid is match
	List<Integer> list18 = Arrays.asList(-1, 0, 1, 3, 40, 50); // right-mid is match
	List<Integer> list19 = Arrays.asList(-1, 0, 1, 2, 4, 50); // far-right is match

	List<Integer> list20 = Arrays.asList(-10, -9, -7, -5, -2, -1, 1, 2, 4, 5, 7, 8, 11, 13, 15, 19, 21, 22, 23, 30);	
	
	@Test
	public void simpleOddEETest() {
		assertEquals(2, SearchWithATweak.elementEqualToItsIndex(list8));
	}
	
	@Test
	public void simpleEvenEETest() {
		assertEquals(2, SearchWithATweak.elementEqualToItsIndex(list9));
	}
	
	@Test
	public void noMatchesEETest() {
		assertEquals(-1, SearchWithATweak.elementEqualToItsIndex(list1));
	}
	
	@Test
	public void oddLeftmostMatchTest() {
		assertEquals(0, SearchWithATweak.elementEqualToItsIndex(list10));
	}
	
	@Test
	public void oddRightmostMatchTest() {
		assertEquals(4, SearchWithATweak.elementEqualToItsIndex(list11));
	}
	
	@Test
	public void oddMidleftMatchTest() {
		assertEquals(1, SearchWithATweak.elementEqualToItsIndex(list12));
	}
	
	@Test
	public void oddMidrightMatchTest() {
		assertEquals(3, SearchWithATweak.elementEqualToItsIndex(list13));
	}
	
	@Test
	public void evenLeftmostMatchTest() {
		assertEquals(0, SearchWithATweak.elementEqualToItsIndex(list14));
	}
	
	@Test
	public void evenFarleftMatchTest() {
		assertEquals(1, SearchWithATweak.elementEqualToItsIndex(list16));
	}
	
	@Test
	public void evenLeftmidMatchTest() {
		assertEquals(2, SearchWithATweak.elementEqualToItsIndex(list17));
	}
	
	@Test
	public void evenRIghtmidMatchTest() {
		assertEquals(3, SearchWithATweak.elementEqualToItsIndex(list18));
	}
	
	@Test
	public void evenFarrightMatchTest() {
		assertEquals(4, SearchWithATweak.elementEqualToItsIndex(list19));
	}
	
	@Test
	public void evenRightmostMatchTest() {
		assertEquals(5, SearchWithATweak.elementEqualToItsIndex(list15));
	}	
	
	@Test 
	public void loadTest() {
		assertEquals(13, SearchWithATweak.elementEqualToItsIndex(list20));
		Collections.reverse(list20);
		assertEquals(8, SearchWithATweak.elementEqualToItsIndex(list20));
	}
	
}
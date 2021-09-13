package edu.yu.da.test;

import edu.yu.da.*;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class DPWordWrappingTest {

	
	@Test
	public void professorTest() {
		final int lineLength = 5 ;
		String [] a = {"a" , "b" } ;
		final int expected = 4 ;
		final DPWordWrapping dp = new DPWordWrapping ( a , lineLength ) ;
		final int actual = dp.minimumPenalty();
		assertEquals( "Mismatch on expected minimum penalty" , expected ,actual);
		final Map<Integer, List<String>> layout = dp . getLayout( ) ;
		assertEquals( "Verifying number of lines in layout" , 1 , layout . size ( ) ) ;
		assertEquals( "Verifying layout of line #0" , List.of( "a" , "b" ), layout.get(0));
	}
	
	public void generalTest(final int lineLength, String[] words, final int expectedPenalty, 
		
		final int expectedNumLines, final Map<Integer, List<String>> expectedLayout) {
		final DPWordWrapping dp = new DPWordWrapping(words, lineLength);
		final int actualPenalty = dp.minimumPenalty();
		final Map<Integer, List<String>> layout = dp.getLayout();
		assertEquals( "Mismatch on expected minimum penalty" , expectedPenalty, actualPenalty);
		assertEquals( "Verifying number of lines in layout" , expectedNumLines , layout.size());
		assertEquals( "Verifying layout", expectedLayout, layout);
	}
	
	@Test
	public void simpleTest() {
		final int lineLength = 5;
		String[] words = {"I", "had", "a", "ll", "dreid"};
		final int expectedPenalty = 1;
		final int expectedNumLines = 3;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("I", "had"));
		expectedLayout.put(1, List.of("a", "ll"));
		expectedLayout.put(2, List.of("dreid"));

		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}
	
	@Test
	public void harderTest() {
		final int lineLength = 5;
		String[] words = {"I", "had", "a", "lil", "dreid", "eye", "made", "it", "ow", "of", "ca"};
		final int expectedPenalty = 5;
		final int expectedNumLines = 7;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("I", "had"));
		expectedLayout.put(1, List.of("a", "lil"));
		expectedLayout.put(2, List.of("dreid"));
		expectedLayout.put(3, List.of("eye"));
		expectedLayout.put(4, List.of("made"));
		expectedLayout.put(5, List.of("it", "ow"));
		expectedLayout.put(6, List.of("of", "ca"));

		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}
	
	@Test
	public void harderBFTest() {
		final int lineLength = 5;
		String[] words = {"I", "had", "a", "lil", "dreid", "eye", "made", "it", "ow", "of", "ca"};
		final int expectedPenalty = 5;
		final int expectedNumLines = 7;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("I", "had"));
		expectedLayout.put(1, List.of("a", "lil"));
		expectedLayout.put(2, List.of("dreid"));
		expectedLayout.put(3, List.of("eye"));
		expectedLayout.put(4, List.of("made"));
		expectedLayout.put(5, List.of("it", "ow"));
		expectedLayout.put(6, List.of("of", "ca"));
		Answer actual = DPWordWrapping.bf(words, lineLength);
		System.out.println(actual);
		assertEquals(actual, new Answer(expectedPenalty, expectedLayout));
	}
	
	
	@Test
	public void greedyCounterExampleTest() {
		final int lineLength = 5;
		String[] words = {"aaa", "a", "aa"};
		final int expectedPenalty = 5;
		final int expectedNumLines = 2;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("aaa"));
		expectedLayout.put(1, List.of("a", "aa"));

		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}
	
	@Test
	public void greedyCounterExample2Test() {
		final int lineLength = 18;
		String[] words = {"happy", "birthday", "to", "you"}; // 5 8, 2 3 -> 16+144=160 | 5 8 2, 3 -> 1+225=226
		final int expectedPenalty = 160;
		final int expectedNumLines = 2;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("happy", "birthday"));
		expectedLayout.put(1, List.of("to", "you"));

		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}
	
	
	
	@Test
	public void exampleTest() {
		final int lineLength = 9;
		String[] words = {"happy", "birthday", "to", "you"};
		final int expectedPenalty = 26;
		final int expectedNumLines = 3;
		final Map<Integer, List<String>> expectedLayout = new HashMap<>();
		expectedLayout.put(0, List.of("happy"));
		expectedLayout.put(1, List.of("birthday"));
		expectedLayout.put(2, List.of("to", "you"));

		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}
	
	@Test
	public void timeTest() {
		double[] times = new double[20];
		for(int i = 10; i < 17; i++) {
			int m = (int) Math.pow(2, i), n=m;
			times[i] = runTimeTest(m, n);
		}
		double[] ratios = new double[times.length];
		for(int i = 1; i < ratios.length; i++) ratios[i] = times[i] / times[i-1];
		System.out.println("Times: " + Arrays.toString(times));
		System.out.println("Ratios: " + Arrays.toString(ratios));
	}
	
	public long runTimeTest(int m, int n) {
		String[] words = new String[n]; // 5 8, 2 3 -> 16+144=160 | 5 8 2, 3 -> 1+225=226
		Random r = new Random();
		for (int i = 0; i < words.length; i++) {
			words[i] = String.valueOf(r.nextInt(100000000));
		}
		long t0 = System.currentTimeMillis();
		DPWordWrapping dp = new DPWordWrapping(words, m);
		long t1 = System.currentTimeMillis();
		return t1-t0;
	}
	
	/*
	@Test
	public void bigTest() {
		final int lineLength = 6;
		String[] words = new String[12];
		Random r = new Random();
		for (int i = 0; i < words.length; i++) {
			words[i] = String.valueOf(r.nextInt(900));
		}
		Answer bfAns = DPWordWrapping.bf(words, lineLength);
		final int expectedPenalty = bfAns.penalty;
		final Map<Integer, List<String>> expectedLayout = bfAns.layout;
		int expectedNumLines = expectedLayout.size();
		generalTest(lineLength, words, expectedPenalty, 
				expectedNumLines, expectedLayout);
	}*/
	
	@Test
	public void wordTooLongTest() {
		DPWordWrapping dpShort = new DPWordWrapping(new String[] {"a", "aa", "aaa", "a"}, 3);
		DPWordWrapping dpLong = new DPWordWrapping(new String[] {"a", "aa", "aaaa", "a"}, 3);
		int shortMin = dpShort.minimumPenalty();
		int longMin = dpLong.minimumPenalty();
		assertNotEquals(shortMin, Integer.MAX_VALUE);
		assertEquals(longMin, Integer.MAX_VALUE);
		
	}
	
	/*
	@Test
	public void combosTest() {
		Set<int[]> set = new HashSet<>();
		DPWordWrapping.getCombos(10, 4, set);
		//for( int[] arr: set) System.out.println(Arrays.toString(arr));
		//System.out.println("set size: "+set.size());
	}
	*/
	
	

}

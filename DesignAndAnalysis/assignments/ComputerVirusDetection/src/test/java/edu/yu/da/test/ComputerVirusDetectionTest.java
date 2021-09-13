package edu.yu.da.test;

import edu.yu.da.*;
import edu.yu.da.ComputerVirusDetection.Virus;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class ComputerVirusDetectionTest {

	@Test
	public void noneFoundOdd() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker()),				
				new Virus("aaaaaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker())				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals(-1, actual);
	}
	
	@Test
	public void noneFoundEven() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker()),				
				new Virus("aaaaaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker())				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals(-1, actual);
	}
	
	@Test
	public void foundOdd() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker()),				
				new Virus("aaaaaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker())				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals("aaa", viruses[actual].getCode());
	}
	
	@Test
	public void foundEven() {
		Virus[] viruses = {
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aa", new SimpleVirusChecker()),				
				new Virus("aaaaaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker()),				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals("aaa", viruses[actual].getCode());
	}
	
	@Test
	public void bfTestNoneFoundOdd() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals(-1, actual);
	}
	
	@Test
	public void bfTestNoneFoundEven() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("a", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker())			
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals(-1, actual);
	}
	
	@Test
	public void bfTestFoundOdd() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals("aaa", viruses[actual].getCode());
	}
	
	@Test
	public void bfTestFoundEven() {
		Virus[] viruses = {
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),
				new Virus("aaa", new SimpleVirusChecker()),				
				new Virus("aaa", new SimpleVirusChecker())		
		};
		int actual = ComputerVirusDetection.mostPrevalent(viruses, null);
		assertEquals("aaa", viruses[actual].getCode());
	}
	
	@Test
	public void bigTestFound() {
		Random rand = new Random();
		int N = 5001;
		Virus[] viruses = new Virus[N];
		for(int i = 0; i < viruses.length; i++) {
			int num = rand.nextInt(2);
			viruses[i] = new Virus(""+num, new SimplerVirusChecker());
		}
		int actualIndex = ComputerVirusDetection.mostPrevalent(viruses, null);
		int expectedIndex = ComputerVirusDetection.bruteForceMostPrevalent(viruses, null);
		System.out.println(Arrays.toString(viruses));
		System.out.println("actual: "+((actualIndex != -1) ? viruses[actualIndex].getCode() : "none"));
		System.out.println("expected: "+((expectedIndex != -1) ? viruses[expectedIndex].getCode() : "none"));
		if (actualIndex == -1) {
			if (expectedIndex == -1) assertTrue(true);
			else fail();
		}
		else {
			String actual = viruses[actualIndex].getCode();
			String expected = viruses[expectedIndex].getCode();
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void bigTestNotFound() {
		Random rand = new Random();
		int N = 5000;
		Virus[] viruses = new Virus[N];
		for(int i = 0; i < viruses.length; i++) {
			int num = rand.nextInt(100);
			viruses[i] = new Virus(""+num, new SimplerVirusChecker());
		}
		int actualIndex = ComputerVirusDetection.mostPrevalent(viruses, null);
		int expectedIndex = ComputerVirusDetection.bruteForceMostPrevalent(viruses, null);
		System.out.println(Arrays.toString(viruses));
		System.out.println("actual: "+((actualIndex != -1) ? viruses[actualIndex].getCode() : "none"));
		System.out.println("expected: "+((expectedIndex != -1) ? viruses[expectedIndex].getCode() : "none"));
		if (actualIndex == -1) {
			if (expectedIndex == -1) assertTrue(true);
			else fail();
		}
		else {
			String actual = viruses[actualIndex].getCode();
			String expected = viruses[expectedIndex].getCode();
			assertEquals(expected, actual);
		}
	}

}

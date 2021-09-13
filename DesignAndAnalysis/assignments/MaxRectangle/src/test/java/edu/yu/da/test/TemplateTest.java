package edu.yu.da.test;

import edu.yu.da.*;
import edu.yu.da.MaxRectangle.Answer;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class TemplateTest {

	@Test
	public void simpleBFTest() {
		MaxRectangle mr = new MaxRectangle();
		int[] input = {1,3,2,5,4,2};
		Answer expected = new Answer(9, 1, 4, 3);
		Answer actual = MaxRectangle.bruteForceGet(input);
		assertEquals(expected, actual);
	}
	
	@Test
	public void bigBFTest() {
		MaxRectangle mr = new MaxRectangle();
		int[] input = {20, 60, 3, 2, 41, 4, 44, 17, 8, 29, 15}; // 232, 1, 9
		Answer expected = new Answer(232, 1, 9, 29);
		Answer actual = MaxRectangle.bruteForceGet(input);
		assertEquals(expected, actual);
	}
	
	@Test
	public void proofOfConceptTest() {
		MaxRectangle mr = new MaxRectangle();
		Random rand = new Random();
		int[] input = new int[10308];
		for (int i = 0; i < input.length; i++) {
			input[i] = rand.nextInt(10000);
		}
		Answer expected = MaxRectangle.bruteForceGet(input);
		Answer actual = MaxRectangle.get(input);
		assertEquals(expected, actual);
	}

}

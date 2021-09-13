package edu.yu.introtoalgs;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchWithATweak 
{
	
	/** Searches the specified sorted list of Integers for
	* the specified key. The list must be sorted prior to
	1
	* making this call: otherwise , the results are
	* undefined. If the list contains multiple elements
	* with the specified value , will return the index of
	* the first instance of that value.
	*
	* @param list the list to be searched: the list is
	* assumed to be sorted
	* @param key the value to be searched for
	* @return Index of the search key, if it is contained
	* in the list; otherwise , returns -1.
	*/
	public static int findFirstInstance(final List <Integer> list, final int key) {
		int result = -1;
		result = isAscending(list) ? Collections.binarySearch(list, key)
							       : Collections.binarySearch(list, key, Comparator.reverseOrder());
		if (result < 0) return -1; // JDK binary search returns negative number when not found
		result = findEarlierInstance(list, key, result);
		// since findEarlierInstance also performs log(n), the total performance
		// of findFirstInstance is 2log(n), which is O(log(n))
		return result;
	}
	
	/*
	 * Searches the part of the list to the left of the instance that was found, to see
	 * if there are other occurrences of the given key that are earlier in the list.
	 * 
	 * Performs in at most log(n), worst case being that the instance passed
	 * is at the end of the list.
	 */
	private static int findEarlierInstance(List <Integer> list, int key, int instance) {
		int lBound = 0;
		int guess;
		while (lBound < instance) {
			guess = lBound + (instance - lBound)/2;
			if (list.get(guess) == key) {
				instance = guess;
			}
			else {
				lBound = guess + 1;
			}
		}
		return instance;
	}
	
	
	/** Searched the specified sorted list of distinct
	* Integers and returns an index i with the property
	* that the value of the ith element is itself i.
	*
	*
	* @param list the list to be searched: the list is
	* assumed to be sorted and contains distinct values
	* @return Index satisfying the specified property if any
	* such elements exist; otherwise , return -1.
	* If multiple elements
	* possess the desired property , any of these elements
	* may be returned.
	*/
	public static int elementEqualToItsIndex(final List<Integer> list) {
		int guess;
		int lBound = 0;
		int rBound = list.size() - 1;
		while (lBound <= rBound) { 
			guess = lBound + (rBound - lBound)/2; 
			if (list.get(guess) == guess) return guess;
			else if (shouldMoveLeft(list, guess)) {
				rBound = guess - 1;
			}
			else lBound = guess + 1; 
		}
		return -1;
	}
	
	/*
	 * Determines whether the given sorted list is ascending or descending.
	 */
	private static boolean isAscending(List<Integer> list) {
		return list.get(0) < list.get(list.size() - 1);
	}
	
	/*
	 * Determines which half of the previous section (left or right)
	 * the binary search should continue searching.
	 *  The calculation depends on whether the sorted list is
	 *  ascending or descending.
	 */
	private static boolean shouldMoveLeft(List<Integer> list, int guess) {
		if (isAscending(list)) return list.get(guess) > guess; 
			// if ascending, if the value at the index is greater than it,
			// all elements to the right cannot have the desired property
		else return list.get(guess) < guess;
			// if descending, if the value at the index is less than it,
			// all elements to the right cannot have the desired property
	}

}

package edu.yu.da;

/** A skeleton class that defines an API for a dynamic programming solution for
 * the problem of "what is the minimal number of coins needed to make change
 * for a specified amount".
 *
 * @author Avraham Leff
 */

public class DPMakingChange {
  public static final int PSEUDO_INFINITY = 100000;

  public static int ifZeroPseudo(int i) { 
	  return (i == 0) ? PSEUDO_INFINITY : i;
  }
  
  
  /** Given a set of coin denominations and an amount of change that must be
   * made from these denominations, determines the optimal (as defined by
   * "minimal number") coins that should be used to make change.  There are an
   * unlimited number of coins available for each of the specified
   * denominations.  If change cannot be made given these parameters, this fact
   * must be reflected by appropriate use of the PSEUDO_INFINITY constant (see
   * below).
   *
   * @param denominations A non-null array of coin denominations (units are
   * assumed to be cents), of size n+1, indexed from 1..n.
   *
   * Note: denominations[0] is only a place-holder element, and should be
   * ignored by the method implementation.  The denominations need not be
   * sorted in any way.  The parameter must have length >= 2.
   *
   * The semantics are undefined if the client supplies a parameter containing
   * non-unique denominations.
   * @param N A non-negative amount of change to be calculated (same units as
   * the denominations parameter)
   * @return an int[][] array c such that c[i, j] holds the minimum number of
   * coins required to make chane for an amount "j" using only coins d_1,
   * ... d_i.  The optimal number of coins must be available at
   * c[denominations.length -1][N].
   *
   * Note: denominations c[i][0] = 0 for all denominations i.
   *
   * Note: if change cannot be made change for amount "j" using coins d_1, ...,
   * d_i, the implementation must set c[i, j] = PSEUDO_INFINITY.
   */
  public static int[][] makeChange(final int denominations[], final int N) {
	  if (denominations.length < 2) throw new IllegalArgumentException("Must have at least 1 non-zero denomination");
	  
	  int[][] c = new int[denominations.length][N+1];
	  
	  // ignore denominations[0]
	  for(int i = 1; i < c.length; i++) {
		  // new i'th denomination we are "throwing into the mix"
		  int denom = denominations[i];
		  
		  // for change smaller than the denom, no new ways to make change, copy i-1
		  for (int j = 1; j < denom; j++) c[i][j] = ifZeroPseudo(c[i-1][j]);
		  // for denom, the best is just using one of that coin!
		  c[i][denom] = 1;
		  
		  // otherwise, we see if using this denom is better than without it
		  for (int j = denom+1; j < c[0].length; j++) {
			  // best without i is prev row with same j
			  int bestWithoutI = ifZeroPseudo(c[i-1][j]);
			  
			  // best with some i is the best that gets you denom away from the target, +1 for another denom
			  int withSomeI = ifZeroPseudo(c[i][j-denom]) + 1;
			  
			  // set to overall best
			  c[i][j] = Math.min(bestWithoutI, withSomeI);
		  }
	  }
	  return c;
  } // makeChange

  /** Calculates the actual "payout" of coins returned for a given makeChange()
   * problem.
   *
   * The semantics of this method are undefined if the client doesn't supply
   * parameter values passed to, and returned from, a valid invocation of
   * makeChange().
   * 
   * @param c, the matrix returned by makeChange().
   * @param denominations the corresponding parameter to makeChange().
   * @param N the corresponding parameter to makeChange().
   * @return An array a_1, ... a_n of non-negative integers such that a_1 x
   * d_1, ..., + a_n x d_n = N.  The values of a_1, + ... + a_n are minimal for
   * the given denominations and amount of change that needs to be made.
   * @see #makeChange
   */
  public static int[]
    payOut(final int c[][], final int[] denominations, final int N)
  {
	  int[] payout = new int[denominations.length];
	  int i = c.length-1;
	  int j = c[0].length-1;
	  if(c[i][j]==PSEUDO_INFINITY) throw new IllegalArgumentException("Cannot make change with input");
	  
	  while (i > 0 && j > 0) {
		  if (c[i][j] == c[i-1][j]) i--;
		  else {
			  j -= denominations[i];
			  payout[i]++;
		  }
	  }
	  return payout;
  } // payOut
    
}
package edu.yu.da;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedianFinding {
  public static void main(String[] args) {
  }

  /** Immutable account class for pedagogic purposes only since it's not very
   * useful.
   */
  public static class Account implements Comparable<Account> {
    /** Initializes the account with an income field.
     */
    public Account(final double accountIncome) {
      income = accountIncome;
    }

    public double getIncome() { return income; }

    public int compareTo(final Account that) {
        if (this.income > that.getIncome()) return 1;
        if (this.income < that.getIncome()) return -1;
    	return 0;
    }
    
    public String toString() {return "Acc. - $"+income;};
    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(income);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(income) != Double.doubleToLongBits(other.income))
			return false;
		return true;
	}



	private final double income;
  } // static inner class
  
  public static Account findMedianRef(final Account[] set1, final Account[] set2) {
	  Account[] combo = new Account[set1.length + set2.length];
	  for (int i = 0; i < set1.length; i++) {
		  combo[i] = set1[i];
	  }
	  for (int i = 0; i < set2.length; i++) {
		  combo[i + set1.length] = set2[i];
	  }
	  Arrays.sort(combo);
	  return combo[(combo.length/2) - 1];
  }

  /** Finds the median account (with respect to account income) over two sets
   * of accounts.  The two sets are of the same length.
   *
   * @param set1 A sorted, in ascending order, with respect to account
   * income, non-null array of accounts.  If the array is not sorted correctly,
   * the results of the method are undefined.
   * @param set2 A sorted, in ascending order, with respect to account
   * income, non-null array of accounts.  If the array is not sorted correctly,
   * the results of the method are undefined.
   * @return Account record that has the median income with respect to
   * all accounts in the union of set1 and set2.
   */
  public static Account findMedian(final Account[] set1, final Account[] set2) {
	    return findMedian(set1, set2, 0, set1.length - 1, 0, set2.length - 1);
  } // findMedian
  
  private static Account findMedian(final Account[] a, final Account[] b, int alo, int ahi, int blo, int bhi) {
	  int span = ahi - alo;
      if (span <= 1) {
          return solve4(
              new Account[] {
                  a[alo], 
                  a[ahi],
                  b[blo],
                  b[bhi]
              });
      }
      
      int amid = alo + (span/2);
      
      int bmid = blo + (span/2);
      if (span % 2 == 1) bmid++;

      //System.out.println("    amid is "+amid+", bmid is "+bmid);
      if (a[amid].compareTo(b[bmid]) < 0) {
          return findMedian(a, b, amid, ahi, blo, bmid);
      }
      else {
          return findMedian(a, b, alo, amid, bmid, bhi);
      }

  } // findMedian
  
  public static Account solve4(Account[] arr) {
      /*System.out.println(String.format("Array to solve is: %s %s %s %s",
    		  arr[0].toString(),
    		  arr[1].toString(),
    		  arr[2].toString(),
    		  arr[3].toString()));
    		  */
      Arrays.sort(arr);
      return arr[1];
  }

  private final static Logger logger = LogManager.getLogger(MedianFinding.class);
}
package edu.yu.da;

import java.util.Objects;
import java.util.*;

/** A skeleton class to structure implementations of solutions to the "Computer
 * Virus Detection" problem.
 *
 * @author Avraham Leff
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputerVirusDetection {
  public final static int NO_VIRUS_QUALIFIES = -1;
  
  // Implement this interface any way that you like: I will test
  // your code with my implementation
  public interface VirusChecker {
    /** Returns true iff one Virus is "equal" to another Virus
     */
    boolean areEqual(final Virus virus1, final Virus virus2);
  }

  // You may not change this inner class in any way!
  public static class Virus {
    /** Constructor
     *
     * @param code a String containing the code for the virus
     */
    public Virus(final String code, final VirusChecker virusChecker) {
      Objects.requireNonNull(code, "code can't be null");
      if (code.isEmpty()) {
        throw new IllegalArgumentException("Empty 'code' parameter");
      }
      Objects.requireNonNull(virusChecker, "Virus checker can't be null");

      this.code = code;
      this.virusChecker = virusChecker;
    }

    public String getCode() { return code; }

    @Override
    public String toString() {
      return "Virus[code="+code+"]";
    }
    
    @Override
    public boolean equals(Object o) {
      if (o == this) { 
        return true; 
      } 
  
      if (!(o instanceof Virus)) { 
        return false; 
      }

      final Virus that = (Virus) o;
      return virusChecker.areEqual(this, that);      
    }

    @Override
    public int hashCode() {
      // Every virus has its own hashCode value, regardless of whether or not
      // it's equivalent to another
      return System.identityHashCode(code);
    }

    private final String code;
    private final VirusChecker virusChecker;
  }

  /** Returns the index of any virus instance that is a member of the "most
   * prevalent virus class" iff one exists, NO_VIRUS_QUALIFIES if none
   * qualifies.
   *
   * @param viruses Array of viruses to be evaluated
   * @param checker Implements the VirusChecker interface, all virus instances
   * in the viruses parameter have been initialized with this instance.
   * @return index of a virus that, with respect to the specified VirusChecker,
   * determines a "most prevalent virus" equivalence class; or
   * NO_VIRUS_QUALIFIES if no virus set in the input qualifies as a "most
   * prevalent virus".
   *
   * Note: assuming the a "most prevalent virus" exists, multiple indices
   * typically qualify as the return value.
   */
  public static int
    mostPrevalent(final Virus[] viruses, final VirusChecker checker)
  {
	  CanonicalVirus cv = find(viruses, 0, viruses.length-1);
	  return (cv == null) ? NO_VIRUS_QUALIFIES : cv.getIndex();
  }
  
  private static CanonicalVirus find(final Virus[] viruses, int lo, int hi) {
	  int N = hi - lo + 1;
	  int mid = lo + ((hi - lo)/2);
	  if (N < 3) return bruteForceFind(viruses, lo, hi);
	  
	  CanonicalVirus cvl = find(viruses, lo, mid);
	  CanonicalVirus cvr = find(viruses, mid+1, hi);
	  
	  if (cvl != null) {
		  int rcount = 0;
		  for (int i = mid+1; i <= hi; i++) {
			  if (viruses[i].equals(cvl.getVirus())) rcount++;
		  }
		  int total = cvl.getCount() + rcount;
		  if (total > N/2) {
			  cvl.setCount(total);
			  return cvl;
		  }
	  }
	  if (cvr != null) {
		  int lcount = 0;
		  for (int i = lo; i <= mid; i++) {
			  if (viruses[i].equals(cvr.getVirus())) lcount++;
		  }
		  int total = cvr.getCount() + lcount;
		  if (total > N/2) {
			  cvr.setCount(total);
			  return cvr;
		  }
	  }
	  return null;
  }

  // public for testing!!
  public static int
  bruteForceMostPrevalent(final Virus[] viruses, final VirusChecker checker)
{
	  CanonicalVirus cv = bruteForceFind(viruses, 0, viruses.length-1);
	  return (cv == null) ? NO_VIRUS_QUALIFIES : cv.getIndex();
}
  
  private static CanonicalVirus bruteForceFind(final Virus[] viruses, int lo, int hi) {
	int N = hi - lo + 1;
	for (int i = lo; i <= hi; i++) { // really don't need to go past mid!
		CanonicalVirus cv = new CanonicalVirus(viruses[i], 1, i);
		for (int j = i+1; j <= hi; j++) { 
			if (viruses[j].equals(cv.getVirus())) cv.incrementCount();
		}
		if (cv.getCount() > N/2) return cv;
	}
	return null;
}

public static void main(String[] args) {
  }

  private final static Logger logger = LogManager.getLogger(ComputerVirusDetection.class);
}
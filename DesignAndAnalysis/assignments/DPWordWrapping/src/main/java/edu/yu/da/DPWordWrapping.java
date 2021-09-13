package edu.yu.da;

/** Skeleton class for implementing a dynamic programming solution for
 * minimizing a "word wrap penalty" function for a sequence of text.  
 *
 * @author Avraham Leff
 */

import java.util.*;

public class DPWordWrapping {

  private static final boolean PRINTING_ON = false;
  private int minPenalty;
  private Map<Integer, List<String>> layout;
	
	/** No-argument constructor.
   *
   * @param words a non-null, non-empty, sequence of words
   * @param lineLength a positive value that defines the line length for all
   * lines in the computed display
   * @see requirements doc for details
   */
  public DPWordWrapping(final String[] words, final int lineLength) {
	if (PRINTING_ON) System.out.println("\n\n----- Initializing DPWordWrapping for lineLength="+lineLength
			+", and words="+Arrays.toString(words)+"...\n");
    // check if any word is too long to fit on a line
	for(String s: words) if (s.length() > lineLength) { minPenalty = Integer.MAX_VALUE; return; }
	
	int[] bestIForJ = new int[words.length];
    for(int i = 0; i < bestIForJ.length; i++) bestIForJ[i] = i;
    
    
    /* ****************************
     * calculating the min penalty
     ******************************/
	// opt_j holds optimal solution for the subproblem with the words 0...j 
	int[] opt = new int[words.length];
	  
    opt[0] = (lineLength - words[0].length()) * (lineLength - words[0].length());
    for(int j = 1; j < opt.length; j++) opt[j] = Integer.MAX_VALUE;
    
    // j represents the sub-prob of j words
    for(int j = 1; j < opt.length; j++) {
  	  int charsConsidered = -1; // b/c first word we add does not have assoc. space
  	  
  	  // we march left from j, trying to stuff more words on the same line with j as
  	  // long as we don't exceed the lineLength, and then add the optimal solution
  	  // to the subproblem just to the left of the words on the line to find local total penalty.
  	  // We find the min of all those possibilities and stick it into opt_j
  	  for(int i = j; i >= 0; i--) {
  		  charsConsidered += words[i].length() + 1;
  		  int diff = lineLength - charsConsidered;
  		  if (diff < 0) break; // we've exceeded the lineLength, can stop checking
  		  int p = (diff*diff) + ((i > 0) ? opt[i-1] : 0); 
  		  // local penalty is diff^2 + opt_i-1, or 0 if i-1 is off the cliff
  		  if ((p) < opt[j]) {
  			  opt[j] = p;
  			  bestIForJ[j] = i;
  		  }
  	  }
    }
    if (PRINTING_ON) System.out.println("Opt: "+Arrays.toString(opt));
    if (PRINTING_ON) System.out.println("Best i for j: "+Arrays.toString(bestIForJ));
	this.minPenalty = opt[opt.length - 1];
	
	
	/* ****************************
     * calculating the layout
     ******************************/
	 Map<Integer, List<String>> layout = new HashMap<>();
	  int j = bestIForJ.length - 1;
	  Stack<Integer> lineEndingIndices = new Stack<>();
	  lineEndingIndices.push(j);
	  while (j >= 0) lineEndingIndices.push(j = bestIForJ[j] - 1);
	  lineEndingIndices.pop(); // remove the negative line ending
	  if (PRINTING_ON) System.out.println("Stack: " + Arrays.toString(lineEndingIndices.toArray()));
	  int l = 0; int line = 0;
	  while (lineEndingIndices.size() > 0) {
		  int r = lineEndingIndices.pop();
		  List<String> lineStrings = new LinkedList<>();
		  for(int i = l; i <= r; i++) lineStrings.add(words[i]);
		  layout.put(line++, lineStrings);
		  l = r + 1;
	  }
	  if (PRINTING_ON)System.out.println(layout);
	  this.layout = layout;
  }


  /** Using the rules and constraints defined in the requirements document,
   * return the value of the optimal total penalty for the specified sequence
   * of words and constant line length parameter supplied to the constructor.
   *
   * @return the optimal total penalty to layout the words
   */
  public int minimumPenalty() {
	  return this.minPenalty;
  }

  /** Returns the optimal layout for the specified sequence of words and
   * constant line parameter supplied to the constructor.  If no optimal total
   * penalty value can be computed per the requirements doc, the layout is undefined.
   *
   * @return layout A Map which associates with each line in the layout
   * (0..n-1), a sequence of words that conforms to the rules specified in the
   * requirements doc.
   */
  public Map<Integer, List<String>> getLayout() {
	  return this.layout;
  }
  
  public static Answer bf(final String[] words, final int lineLength) { 
	  Answer bestAnswer = new Answer(Integer.MAX_VALUE, null);
	  for(int lines = 1; lines < words.length; lines++) {
		  Set<int[]> combos = new HashSet<>();
		  getCombos(words.length, lines-1, combos); // splits = lines -1
		  for(int[] combo: combos) {
			  Answer ans = getComboAns(combo, words, lineLength);
			  if (ans.penalty < bestAnswer.penalty) {
				  bestAnswer = ans;
				  System.out.println("Local best answer for combo " + Arrays.toString(combo) + " is:\n"+ans);
			  }
		  }
		  //int bestForLines = bestForLines(lines, words, lineLength);
	  }
	  return bestAnswer;
  }


	private static Answer getComboAns(int[] combo, String[] words, int lineLength) {
		int penalty = 0; Map<Integer, List<String>> layout = new HashMap<>();
		int l = 0, r = 0, line = 0;
		while (l < words.length) {
			while (r < words.length-1 && combo[r] != 1) r++;
			int charsOnLine = -1;
			List<String> lineWords = new LinkedList<>();
			for (int i = l; i <= r; i++) {
				charsOnLine += words[i].length() + 1;
				lineWords.add(words[i]);
			}
			int diff = lineLength - charsOnLine;
			if (diff < 0) return new Answer(Integer.MAX_VALUE, null);
			penalty += (diff*diff);
			layout.put(line, lineWords);
			l = ++r;
			line++;
		}
		return new Answer(penalty, layout);
	}


	public static int[] getCombos(int N, int n, Set<int[]> set) {
		if (n == 0) {
			set.add(new int[N]);
			return null;
		}
		return getCombos(n, n, 0, new int[N], set);
	}
	
	private static int[] getCombos(int N, int n, int left, int[] list, Set<int[]> res) {
		if (left == list.length || n == 0) return list;
		int[] usedList = Arrays.copyOf(list, list.length);
		int[] unusedList = Arrays.copyOf(list, list.length);
		int[] cand;
		
		usedList[left]=1;
		cand = getCombos(N, n-1, left+1, usedList, res);
		if (hasN(cand, N)) res.add(cand);
		
		if (list.length - (left) >= n) {
			unusedList[left]=0;
			cand = getCombos(N, n, left+1,unusedList, res);
			if (hasN(cand, N)) res.add(cand);
		}
		
		return Arrays.copyOf(list, list.length);
	}


	private static boolean hasN(int[] cand, int N) {
		int total = 0;
		for(int i: cand) total+=i;
		return total == N;
	}
	
	// a combo of n splits out of N possibilities is the combo using the current s and the answer to 
	// C(N,n) = C(N-1, n
}

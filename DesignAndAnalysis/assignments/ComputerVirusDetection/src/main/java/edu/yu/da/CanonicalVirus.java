package edu.yu.da;

import edu.yu.da.ComputerVirusDetection.Virus;

public class CanonicalVirus {
  private Virus virus;
  private int count;
  private int index;

public CanonicalVirus(Virus virus, int count, int index) {
	  this.virus = virus;
	  this.count = count;
	  this.index = index;
  }

	public Virus getVirus() {
		return virus;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void incrementCount() {
		this.count++;
	}
	
	 public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}	  
	  
}
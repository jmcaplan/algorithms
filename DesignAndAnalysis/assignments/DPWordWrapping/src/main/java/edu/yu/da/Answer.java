package edu.yu.da;

import java.util.List;
import java.util.Map;

public class Answer {
	public int penalty;
	public Map<Integer, List<String>> layout;
	
	public Answer(int minPenalty, Map<Integer, List<String>> layout) {
		this.penalty = minPenalty;
		this.layout = layout;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((layout == null) ? 0 : layout.hashCode());
		result = prime * result + penalty;
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
		Answer other = (Answer) obj;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		if (penalty != other.penalty)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [penalty=" + penalty + ", layout:\n" + layout + "]";
	}
	
	
	
	
}

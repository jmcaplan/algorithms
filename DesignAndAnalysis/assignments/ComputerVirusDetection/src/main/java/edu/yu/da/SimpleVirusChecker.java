package edu.yu.da;

import edu.yu.da.ComputerVirusDetection.Virus;
import edu.yu.da.ComputerVirusDetection.VirusChecker;

public class SimpleVirusChecker implements VirusChecker {

	@Override
	public boolean areEqual(Virus virus1, Virus virus2) {
		return virus1.getCode().length() == virus2.getCode().length();
	}

}

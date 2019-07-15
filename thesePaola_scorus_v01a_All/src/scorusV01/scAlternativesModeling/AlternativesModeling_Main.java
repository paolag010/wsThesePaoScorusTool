package scorusV01.scAlternativesModeling;

import java.util.Map;

import scorusV01.scAlternativesModeling.AlternativesModeling;

public class AlternativesModeling_Main{
	
	public static void main(String[] args) {
		AlternativesModeling_Main m = new AlternativesModeling_Main();
		//m.execScGenConfiguration();
		m.execScGeneration();
	}	
	
	public Map< Integer, String > execScGeneration(){
		AlternativesModeling scorusScGen = new AlternativesModeling();
		return scorusScGen.createFMS_Config();
	}
	
}
package scorusV01.scMetricsEvaluation;

import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import scorusV01.scDerivation.Derivation;
import scorusV01.scDerivation.Derivation_Main;

public class MetricsEvaluation_Main {
	
	public static void main(String[] args) {
		
		MetricsEvaluation_Main m = new MetricsEvaluation_Main();
		
		m.evaluationAllMetricsTest();
	}	
	
	public void evaluationAllMetricsTest(){
		
       	Derivation_Main scorusScGenDerMain = new Derivation_Main();
       	Derivation scorusScGenDer = new Derivation();
       	scorusScGenDer.createMapTypes();
       	
       	MetricsEvaluation metricsEval = new MetricsEvaluation();
       	
        DefaultTreeModel ajsTreeTest = scorusScGenDerMain.execDerivationMetricsTest();
        Map<String, List<String>> mapT = scorusScGenDer.getTypes();
       	
        String metricColExistenceResults = metricsEval.evaluateColExistence(mapT,ajsTreeTest);
        
        scorusScGenDerMain.execDerivationMetTestShow(ajsTreeTest,metricColExistenceResults);        
	}
	
}



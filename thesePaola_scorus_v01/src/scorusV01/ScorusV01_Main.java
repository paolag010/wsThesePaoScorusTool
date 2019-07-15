package scorusV01;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

import fr.familiar.variable.FeatureModelVariable;
import scorusV01.scAlternativesModeling.AlternativesModeling;
import scorusV01.scDerivation.Derivation;
import scorusV01.scGraphics.FramesResults;
import scorusV01.scMetricsEvaluation.MetricsEvaluation;


public class ScorusV01_Main {

	FramesResults scorusHWgraphics = new FramesResults();
	AlternativesModeling scorusScGenConf = new AlternativesModeling();
	Derivation scorusScGenDer = new Derivation();
	MetricsEvaluation scorusScMet = new MetricsEvaluation();

	
	public static void main(String[] args) {
		ScorusV01_Main scorus = new ScorusV01_Main();
		scorus.execDemo();
	}
	
	public void execDemo()
	{		
		System.out.println("*******************************************************");
		System.out.println("************** scGEN - CONFIGURATION ******************");
		System.out.println("*******************************************************\n");
		
		FeatureModelVariable fms = scorusScGenConf.createFMS(null);
		Map< Integer, Set<String> > fmsConfs = scorusScGenConf.getConfigurationsFMS(fms);
		Map< Integer, String > fmsConfsStructured = scorusScGenConf.getConfigurationsFMSTrees(fms);	
		
		scorusHWgraphics.showConfigurations(fmsConfs, "Configurations FM");
		//scorusHWgraphics.showConfigurationsStructured(fmsConfsStructured, "Configurations FM (Structured)");
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scGEN - DERIVATION *********************");
		System.out.println("********************************************************\n");
		
		scorusScGenDer.createMapTypes();		
		Map<String, Object[]> resultsDerivation = scorusScGenDer.derivateSetConfigurations(fmsConfsStructured);
		Map<String, List<String>> mapTypes = scorusScGenDer.getTypes();
		
		//Show schemas (tree + ajschema)
		//for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
		//	Object[] info = entry.getValue();
		//	scorusHWgraphics.showTreeAJS((DefaultTreeModel)info[0], (String)info[1], entry.getKey());	
		//}
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scMetrics - EVALUATION *********************");
		System.out.println("********************************************************\n");
		
		Map< String, String > resultsMetrics =  scorusScMet.evaluateAllMetrics(resultsDerivation,mapTypes);
		
		//Show schemas (tree + ajschema) et metrics
		for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
			Object[] infoDerivation = entry.getValue();
			String infoMetrics = resultsMetrics.get(entry.getKey());			
			scorusHWgraphics.showTreeAJSMetrics((DefaultTreeModel)infoDerivation[0], (String)infoDerivation[1], entry.getKey(), infoMetrics);	
		}

	}
	
}



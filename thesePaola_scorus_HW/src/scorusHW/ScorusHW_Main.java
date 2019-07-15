package scorusHW;

import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import scorusHW.scGen.configuration.ScorusHW_scGen_Configuration_Main;
import scorusHW.scGen.derivation.ScorusHW_scGen_Derivation_Main;
import scorusHW.scGraphics.ScorusHW_graphics;
import scorusHW.scMet.ScorusHW_scMet_Main;



public class ScorusHW_Main {

	
	public static void main(String[] args) {
		ScorusHW_Main scorus = new ScorusHW_Main();
		scorus.execDemo();
	}
	
	public void execDemo()
	{
		ScorusHW_graphics scorusHWgraphics = new ScorusHW_graphics();
		
		System.out.println("*******************************************************");
		System.out.println("************** scGEN - CONFIGURATION ******************");
		System.out.println("*******************************************************\n");		
		
		
		ScorusHW_scGen_Configuration_Main scorusScGenConf = new ScorusHW_scGen_Configuration_Main();
		Map< Integer, String > confs = scorusScGenConf.execScGenConfTrees();
		
		
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scGEN - DERIVATION *********************");
		System.out.println("********************************************************\n");
		
		ScorusHW_scGen_Derivation_Main scorusScGenDer = new ScorusHW_scGen_Derivation_Main();
		Map<String, Object[]> resultsDerivation = scorusScGenDer.execDerivation(confs);
		Map<String, List<String>> mapTypes = scorusScGenDer.getTypes();
		
		
		/*for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
			Object[] info = entry.getValue();
			scorusHWgraphics.showTreeAJS((DefaultTreeModel)info[0], (String)info[1], entry.getKey());	
		}*/
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scMetrics - EVALUATION *********************");
		System.out.println("********************************************************\n");
		
		ScorusHW_scMet_Main scorusScMet = new ScorusHW_scMet_Main();
		Map< String, String > resultsMetrics =  scorusScMet.execMetrics(resultsDerivation,mapTypes);
		
		//show info
		for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
			Object[] infoDerivation = entry.getValue();
			String infoMetrics = resultsMetrics.get(entry.getKey());
			
			scorusHWgraphics.showTreeAJSMetrics((DefaultTreeModel)infoDerivation[0], (String)infoDerivation[1], entry.getKey(), infoMetrics);	
		}
		

	}
	
}



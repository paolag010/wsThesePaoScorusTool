package scorusHW.scMet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import scorusHW.scGen.derivation.ScorusHW_scGen_Derivation_Main;


public class ScorusHW_scMet_Main {
	
	
	public static void main(String[] args) {
		ScorusHW_scMet_Main m = new ScorusHW_scMet_Main();
		m.execMetricsTest();
	}
	
	
	public void execMetricsTest(){

       	ScorusHW_scGen_Derivation_Main scorusScGenDer = new ScorusHW_scGen_Derivation_Main();
       	
        DefaultTreeModel ajsTreeTest = scorusScGenDer.execDerivationMetTest();
        Map<String, List<String>> mapT = scorusScGenDer.getTypes();
       	
        String metricColExistenceResults = this.evaluateMetricColExistence(mapT,ajsTreeTest);
        
        scorusScGenDer.execDerivationMetTestShow(ajsTreeTest,metricColExistenceResults);        
	}
	
	
	public Map< String, String > execMetrics(Map<String, Object[]> scsInfo, Map<String, List<String>> mapT){
		
		Map< String, String > scMetricsResults = new HashMap< String, String >();
		
		for (Map.Entry<String, Object[]> entry : scsInfo.entrySet()) {
			DefaultTreeModel ajsTree = (DefaultTreeModel)entry.getValue()[0];			
	        
			String metricColExistenceResults = this.evaluateMetricColExistence(mapT,ajsTree);
	        
	        scMetricsResults.put(entry.getKey(), metricColExistenceResults);
		}       
		
		return scMetricsResults;
	}
	
	
	public String evaluateMetricColExistence(Map<String, List<String>> mapT , DefaultTreeModel ajsTree ){
		
		String evaluationResults = "*** Metric: ColExistence ***\n\n";
		
		
		for (Map.Entry<String, List<String>> type : mapT.entrySet()) {
			int evaluationResult = evaluateMetricColExistence(type.getKey(), ajsTree);
			
			evaluationResults += "colExistence( " + type.getKey() + " ) = " + evaluationResult + "\n";
		}
		
		return evaluationResults;
	}
	
	
	public int evaluateMetricColExistence(String typeName, DefaultTreeModel ajsTree){
		
		int value = 0;
		String typeNode = typeName + "_l0";
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) ajsTree.getRoot();		
		DefaultMutableTreeNode colByClass = (DefaultMutableTreeNode) ajsTree.getChild(root, 0);
		int numCol = ajsTree.getChildCount(colByClass);
		
		for (int i = 0; i < numCol; i++) 
		{	
			String nameNode =  ( (DefaultMutableTreeNode) ajsTree.getChild(colByClass, i) ).getNextNode().toString()  ;
			if (typeNode.equals(nameNode))
				value = 1;
		}
		
		return value;
	}
	
}



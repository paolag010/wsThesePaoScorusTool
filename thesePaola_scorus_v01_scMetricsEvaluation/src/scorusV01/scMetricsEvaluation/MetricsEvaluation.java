package scorusV01.scMetricsEvaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MetricsEvaluation {
			
	public Map< String, String > evaluateAllMetrics(Map<String, Object[]> scsInfo, Map<String, List<String>> mapT){
		
		Map< String, String > scMetricsResults = new HashMap< String, String >();
		
		for (Map.Entry<String, Object[]> entry : scsInfo.entrySet()) {
			DefaultTreeModel ajsTree = (DefaultTreeModel)entry.getValue()[0];			
	        
			String metricColExistenceResults = evaluateColExistence(mapT,ajsTree);
	        
	        scMetricsResults.put(entry.getKey(), metricColExistenceResults);
		}       
		
		return scMetricsResults;
	}
		
	public String evaluateColExistence(Map<String, List<String>> mapT , DefaultTreeModel ajsTree ){
		
		String evaluationResults = "*** Metric: ColExistence ***\n\n";
		
		
		for (Map.Entry<String, List<String>> type : mapT.entrySet()) {
			int evaluationResult = evaluateColExistence(type.getKey(), ajsTree);
			
			evaluationResults += "colExistence( " + type.getKey() + " ) = " + evaluationResult + "\n";
		}
		
		return evaluationResults;
	}
		
	public int evaluateColExistence(String typeName, DefaultTreeModel ajsTree){
		
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

	public int evaluateDocExistence(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;		
		return value;
	}	
	
	public int evaluateColDepth(String typeCollection, DefaultTreeModel ajsTree){
		int value = 0;
		findDephtPath(null);
		return value;
	}		
	
	private int findDephtPath(DefaultTreeModel path){
		int value = 0;		
		return value;
	}		

	public int evaluateGlobalDepth(DefaultTreeModel ajsTree){
		int value = 0;		
		return value;
	}
	
	public int evaluateDocDepthInCol(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;		
		findDocDephtPath(null,null,null);
		return value;
	}	
	
	private int findDocDephtPath(DefaultTreeModel path, String typeName, DefaultTreeModel ajsTree){
		int value = 0;		
		return value;
	}		
		
	public int evaluateMaxDocDepth(String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateMinDocDepth(String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateDocWidth(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateNbrAtomicAttributes(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateNbrDocAttributes(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateNbrArrayAtomicAttributes(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateNbrArrayDocAttributes(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}
	
	public int evaluateRefLoad(String typeCollection, DefaultTreeModel ajsTree){
		int value = 0;
		return value;
	}	
	
	public int evaluateDocCopiesInCol(String typeCollection, String typeName, DefaultTreeModel ajsTree){
		int value = 0;
		findCardinalityValue(null, null);
		return value;
	}	
	
	private int findCardinalityValue(String associationName, String rolName){
		int value = 0;		
		return value;
	}		
	
	public int docTypeCopies(String typeName){
		int value = 0;
		return value;
	}
	
	
}

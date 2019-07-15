package scorusHW.scGen.derivation;


import java.awt.Color;
import java.awt.GridLayout;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import scorusHW.scGraphics.ScorusHW_graphics;

public class ScorusHW_scGen_Derivation_Main {
	

	
	Hashtable<String, String[]> hashTypes = new Hashtable<String, String[]>();
	Map<String, List<String>> mapTypes = new HashMap<String, List<String>>();
	String keyWordEmb = "emb";
	String keyWordRef = "ref";
	

	public static void main(String[] args) {
		
		ScorusHW_scGen_Derivation_Main m = new ScorusHW_scGen_Derivation_Main();
		m.execDerivation();
	}
	
	public Map<String, Object[]> execDerivation(Map< Integer, String > confs){
		
		Map<String, Object[] > scResults = new HashMap<String, Object[] >();
		
		this.createMapTypes();			
        for (Map.Entry<Integer, String> entry : confs.entrySet()) {
        	String fmString = entry.getValue();        	
        	DefaultTreeModel ajsTree = this.createAJStree(fmString);
        	String ajs = createAJSchema(ajsTree);    		
    		
        	Object[] results = new Object[] {ajsTree, ajs};
        	scResults.put("schema"+entry.getKey(), results);
        }
        
        return scResults;
	}
	
	//interne
	public void execDerivation(){
		Map< Integer, String > confs = new HashMap< Integer, String >();
		confs.put(1, "schema1: colByClass ; colByClass: colB colA ; colB: tB_l0 ; colA: tA_l0 ; tB_l0: r1_ag ; r1_ag: tB_emb_tA ;");
		confs.put(2, "schema2: colByClass ; colByClass: colB colA ; colA: tA_l0 ; colB: tB_l0 ; tA_l0: r1_bLines ; r1_bLines: tA_emb*_tB ;");
		
		this.createMapTypes();			
        for (Map.Entry<Integer, String> entry : confs.entrySet()) {
        	String fmString = entry.getValue();        	
        	DefaultTreeModel ajsTree = this.createAJStree(fmString);
        	String ajs = createAJSchema(ajsTree);
        	
    		ScorusHW_graphics scorusHWgraphics = new ScorusHW_graphics();
    		scorusHWgraphics.showTreeAJS(ajsTree, ajs, "schema"+entry.getKey());
        }	
	}

	
	public DefaultTreeModel execDerivationMetTest(){
		this.createMapTypes();			
       	String fmString = "schema2: colByClass ; colByClass: colB colA ; colA: tA_l0 ; colB: tB_l0 ; tA_l0: r1_bLines ; r1_bLines: tA_emb*_tB ;";        	
        DefaultTreeModel ajsTreeTest = this.createAJStree(fmString);        
        return ajsTreeTest;
	}	
	public void execDerivationMetTestShow(DefaultTreeModel ajsTreeModel , String metrics){
        String ajs = createAJSchema(ajsTreeModel);
    	ScorusHW_graphics scorusHWgraphics = new ScorusHW_graphics();
    	scorusHWgraphics.showTreeAJSMetrics(ajsTreeModel, ajs, "schemaTest",metrics);        	
	}
	
	
	//format ready
	public DefaultTreeModel createAJStree(String fm)
	{
		Hashtable<String, DefaultMutableTreeNode> hashtable = new Hashtable<String, DefaultMutableTreeNode>();

		System.out.println();
		System.out.println(fm);		
		String[] fmNodes01 = fm.replaceAll(":\\s+",":").replaceAll(" ; ",";").replaceAll("\n", "").split(";");		
		String[] nameNodes = fmNodes01[0].split(":");
		
		System.out.println("\n"+nameNodes[0]);
		String nameSc = nameNodes[0];
		hashtable.put(nameNodes[0],new DefaultMutableTreeNode(nameNodes[0]));
        DefaultTreeModel ajsTreeModel = new DefaultTreeModel(hashtable.get(nameNodes[0]));        
        
        String   nameNodeFather = "";
        String[] nameNodesSons = null;
        
		for (int i = 0; i < fmNodes01.length; i++) {
						
			nameNodes = fmNodes01[i].split(":");
			nameNodeFather = nameNodes[0];
			nameNodesSons = nameNodes[1].split(" ");
			
			for (int j = 0; j < nameNodesSons.length; j++) {
				System.out.println("*"+nameNodesSons[j]+"*");
		        hashtable.put(nameNodesSons[j],new DefaultMutableTreeNode(nameNodesSons[j]));
		        ajsTreeModel.insertNodeInto(hashtable.get(nameNodesSons[j]), hashtable.get(nameNodeFather) , j);
		    }   
		}
        
        return ajsTreeModel;
	}
	

	public String createAJSchema(DefaultTreeModel ajsTree)
	{
	    Map< String, List<String> > mapCols = new HashMap<String, List<String>>();		
		String ajs = "";
				
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) ajsTree.getRoot();		
		DefaultMutableTreeNode colByClass = (DefaultMutableTreeNode) ajsTree.getChild(root, 0);
		
		int numCol = ajsTree.getChildCount(colByClass);
		
		for (int i = 0; i < numCol; i++) 
		{	
			String ajsCol = null;
			String colName = null;
			String type = null;
			
			DefaultMutableTreeNode nodeCol =  (DefaultMutableTreeNode) ajsTree.getChild(colByClass, i);
			colName = nodeCol.toString();
			ajsCol = colName + ":\n" + "{\n";
			
			DefaultMutableTreeNode nodeType = nodeCol.getNextNode();
			type = nodeType.toString().split("_")[0];
			List<String> atts = mapTypes.get(type);			
			for (String att : atts) { 
				ajsCol += "\t" + att + ",\n"; 
			}
			
			if ( !nodeType.isLeaf()) {				
				ajsCol += createAJSchemaSub(nodeType,1);
			}else{
				ajsCol = ajsCol.substring(0, ajsCol.length()-2);
			}
			
			ajsCol += "\n" + "}";
			
			ajs += ajsCol + "\n\n";
			
			//System.out.println();
			//System.out.println(ajsCol);
		}
		
		return ajs;
	}
	
	
	public String createAJSchemaSub(DefaultMutableTreeNode nodeType , Integer tabs){
		String type = null;
		String rol = null;
		String mat = null;
		boolean matMany = false;
		List<String> atts = null;
		String ext = null;
		
		// int numAsso = nodeType.getChildCount();				
		Enumeration<DefaultMutableTreeNode> assocs = nodeType.children();				
					
		while (assocs.hasMoreElements()) {					
			DefaultMutableTreeNode nodeAssoc = assocs.nextElement();
			rol = nodeAssoc.toString().split("_")[1];
			
			DefaultMutableTreeNode nodeMat = nodeAssoc.getNextNode();
			mat =  nodeMat.toString().split("_")[1];
			type = nodeMat.toString().split("_")[2];
			atts = mapTypes.get(type);
			
			if(mat.length()!= keyWordEmb.length()){
				mat = mat.substring(0, mat.length()-1);
				matMany = true;
			}
			

			
			if(mat.equals(keyWordEmb)){
				
				for (int t = 0; t < tabs; t++) { ext = "\t"; }			
				ext += rol + ":\n";
				
				for (int t = 0; t < tabs; t++) { ext += "\t"; }
				if(matMany == true) { ext += "[ ";}
				
				ext += "{\n";				
				for (String att : atts) {
					for (int t = 0; t < tabs+1; t++) { ext += "\t"; }
					ext += att + ",\n"; 
				}						
			}else{
				
				for (int t = 0; t < tabs; t++) { ext = "\t"; }			
				ext += rol + ":";
				if(matMany == true) { ext += " [ ";}
				
				ext += "Integer";
			}
			
			if(!nodeMat.isLeaf())
			{						
				nodeType = nodeMat.getNextNode();
				ext += createAJSchemaSub(nodeType, tabs+1);	
			}else{
				if(mat.equals(keyWordEmb)){ 
					ext = ext.substring(0, ext.length()-2) + "\n";
				}
			}
			
			for (int t = 0; t < tabs; t++) { ext += "\t"; }			
			if(mat.equals(keyWordEmb)){ ext += "} "; }
			if(matMany == true) { ext += "]";}
		}
		
		return ext;
	}
	
	
	public void createMapTypes()
	{
	    List<String> attA = new ArrayList<String>(); attA.add("idA:Integer"); attA.add("nameA:String");
	    List<String> attB = new ArrayList<String>(); attB.add("idB:Integer"); attB.add("nameB:String");
	    mapTypes.put("tA", attA); mapTypes.put("tB", attB);		
	}
	
	public Map<String, List<String>> getTypes(){
		  return this.mapTypes;	
	}
	
}

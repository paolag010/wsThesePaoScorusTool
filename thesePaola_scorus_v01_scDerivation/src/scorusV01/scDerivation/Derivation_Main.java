package scorusV01.scDerivation;

import java.util.Hashtable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultTreeModel;

public class Derivation_Main {
	
	Hashtable<String, String[]> hashTypes = new Hashtable<String, String[]>();
	Map<String, List<String>> mapTypes = new HashMap<String, List<String>>();
	String keyWordEmb = "emb";
	String keyWordRef = "ref";
	

	public static void main(String[] args) {
		
		Derivation_Main m = new Derivation_Main();
		m.execDerivation();
	}

	public void execDerivation(){
		
		Derivation scorusScGenDer = new Derivation();
		scorusScGenDer.createMapTypes();
		
		Map< Integer, String > confs = new HashMap< Integer, String >();
		confs.put(1, "schema1: colByClass ; colByClass: colB colA ; colB: tB_l0 ; colA: tA_l0 ; tB_l0: r1_ag ; r1_ag: tB_emb_tA ;");
		confs.put(2, "schema2: colByClass ; colByClass: colB colA ; colA: tA_l0 ; colB: tB_l0 ; tA_l0: r1_bLines ; r1_bLines: tA_emb*_tB ;");
		
					
        for (Map.Entry<Integer, String> entry : confs.entrySet()) {
        	String fmString = entry.getValue();        	
        	DefaultTreeModel ajsTree = scorusScGenDer.derivateAJStree(fmString);
        	String ajs = scorusScGenDer.derivateAJSchema(ajsTree);
        	
    		//ScorusV01_Graphics scorusHWgraphics = new ScorusV01_Graphics();
    		//scorusHWgraphics.showTreeAJS(ajsTree, ajs, "schema"+entry.getKey());
        }
	}
	
	public DefaultTreeModel execDerivationMetricsTest(){
		
		Derivation scorusScGenDer = new Derivation();
		scorusScGenDer.createMapTypes();
		
		String fmString = "schema2: colByClass ; colByClass: colB colA ; colA: tA_l0 ; colB: tB_l0 ; tA_l0: r1_bLines ; r1_bLines: tA_emb*_tB ;";
		DefaultTreeModel ajsTreeTest = scorusScGenDer.derivateAJStree(fmString);        
        
		return ajsTreeTest;
	}	
	
	public void execDerivationMetTestShow(DefaultTreeModel ajsTreeModel , String metrics){
		
		Derivation scorusScGenDer = new Derivation();
		scorusScGenDer.createMapTypes();
		
        String ajs = scorusScGenDer.derivateAJSchema(ajsTreeModel);
        
    	//ScorusV01_Graphics scorusHWgraphics = new ScorusV01_Graphics();
    	//scorusHWgraphics.showTreeAJSMetrics(ajsTreeModel, ajs, "schemaTest",metrics);        	
	}
	
}

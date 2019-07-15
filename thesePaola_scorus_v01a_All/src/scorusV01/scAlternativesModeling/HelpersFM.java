package scorusV01.scAlternativesModeling;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.xtext.example.mydsl.fml.FeatureEdgeKind;

import fr.familiar.FMLTest;
import fr.familiar.parser.FMBuilder;
import fr.familiar.variable.FeatureModelVariable;
import fr.familiar.variable.FeatureVariable;
import fr.familiar.variable.SetVariable;
import fr.familiar.variable.Variable;
import gsd.synthesis.Expression;


public class HelpersFM  extends FMLTest {

	// ********************************************************
	// **********   	OPERATIONS WITH FM		***************
	// ********************************************************		

	
	//  -----   	OPERATIONS WITH FM  - USING FAMILIAR OPTIONS		-----
		
	public FeatureModelVariable  createFMStrategy01(String fmName, String fm) throws Exception{		        
		// Example: FeatureModelVariable fmv = FM ("fm1", "FM (A : [B] [C] ;)");
		
		FeatureModelVariable fmv = FM (fmName, "FM (" + fm + ")");      
        return fmv;        
	}

	// alternate way to build a feature model
	public FeatureModelVariable createFMStrategy02(String fmName, String fm) throws Exception{
		// Example FeatureModelVariable fmv2 = new FeatureModelVariable ("fm2", FMBuilder.getInternalFM("A : [B] [C] ;"));
		
		FeatureModelVariable fmv = new FeatureModelVariable (fmName, FMBuilder.getInternalFM(fm));
	    return fmv;
	}	
	
	public String getConstraints(FeatureModelVariable fmv){        
		
		String cnst = "";
        Set<Expression<String>> constraints = fmv.getAllConstraints();	 
        
        for (Expression<String> cf : constraints) {
            cnst += cf.toString() + "; \n";
        }        
        
        return cnst;
	}

	public String getConstraints(Set<Expression<String>> constraints){        
		
		String cnst = "";
		
        for (Expression<String> cf : constraints) {
            cnst += cf.toString() + "; \n";
        }        
                
        return cnst;
	}
		
	public String getConstraintsFeatureImplies(Set<Expression<String>> constraints, String featureSource){        
		
		String cnst = "";
		
		for (Expression<String> cf : constraints) {
        	if (cf.getLeft().toString().equals(featureSource))
        		cnst += cf.toString() + "; \n";
        		//constraints.remove(cf);
        }        
        
        return cnst;
	}	
		
	/* If fRoot is the root, the subfeature will include the constraints
	   If fRoot is not the root, the subfeature discard the constraints
	   Extract but preserving the original fm  */
	public FeatureModelVariable extractSubFM(FeatureModelVariable fmv, String fRoot) throws Exception {
		
		FeatureModelVariable fmExtracted = null;		
		fmExtracted = new FeatureModelVariable ("fmExtracted", fmv.extract(fRoot));
		//fmv.removeFeature("sB"); 												     // Remove the entire branch
		
		return fmExtracted;
	}

	
	//   -----   	OPERATIONS WITH FM  - PAOLA's PROPOSAL   	-----   	
	
	//all child of featureRoot will be now an Xor group 
	public FeatureModelVariable insertFMXorMode(FeatureModelVariable fmvBase, 
												FeatureModelVariable fmvSub, 
												String ftIntoInsert,
												String fmvSubStringCnst) throws Exception {
		
		FeatureModelVariable fmvNew = null;
		
		String fmvBaseStringCnst = this.getConstraints(fmvBase);
		fmvBase.removeAllConstraints();
		
		String fmvBaseString 	= fmvBase.toString();
		String fmvSubString 	= fmvSub.toString();
		String ftRootfmvSub 	= fmvSub.root().getFtName();
		
		/*System.out.println("\n\n-*- string of fmvBase:\n" 	+ fmvBaseString);
		System.out.println("\n\n-*- string of fmvSub:\n" 	+ fmvSubString);
		System.out.println("\n\n");
		System.out.println("--- featureToInsert:\t" 	+ ftIntoInsert);
		System.out.println("--- featureRootfmvSub:\t" 		+ ftRootfmvSub);
			*/	
		
		//String ftInToOldExpression = ftIntoInsert+": \"R1_sB\"" ;
		String ftInToOldExpression = null;		
		String[] conds = fmvBaseString.split(";");        
        boolean exp = false;   
        int i = 0;        
        while(!exp && i < conds.length ){
        	ftInToOldExpression = conds[i];        	
        	String ft = ftInToOldExpression.substring(2, conds[i].indexOf(":") ).replace("\"", "") ;
        	if(ft.equals(ftIntoInsert)){ exp = true; }
        	i++;
        }
		
		String ftInToNewExpression = ftIntoInsert+": (";
		Set<String> children = fmvBase.getFeature(ftIntoInsert).children().names();
	    Iterator<String> it = children.iterator();
	    int compteur = 0;
        while ( it.hasNext() ){
        	String child = it.next();
        	if(compteur == 0) ftInToNewExpression += child;
        	else ftInToNewExpression += "|" + child;
        }	    
        ftInToNewExpression += "|" + ftRootfmvSub + ")"; 
        
                
        /*System.out.println("\n\n--- children of ftIntoInsert:\n");		
        System.out.println("  oldFeature:\t" + ftInToOldExpression);
        System.out.println("  newFeature:\t" + ftInToNewExpression);
		*/
        
        
        fmvBaseString = fmvBaseString.replaceFirst(ftInToOldExpression, ftInToNewExpression);        
		//System.out.println("\n\n--- string of fmvBase - feature updated :\n" + fmvBaseString);
		
		fmvBaseString = fmvBaseString.concat("\n" + fmvSubString);
		//System.out.println("\n\n--- string of fmvBase - fmvSub inserted :\n" + fmvBaseString);
		
		fmvNew = this.createFMStrategy01("fmNew",fmvBaseString+fmvBaseStringCnst+fmvSubStringCnst);		
		//System.out.println("\n\n-*- string of fmvNew:\n" + fmvNew);	
		
		
		return fmvNew;
	}
	
	
	
	
	public Map< Integer, Set<String> > getConfigurationsFM(FeatureModelVariable fmv) throws Exception{

        Map< Integer, Set<String> > confsString = new HashMap< Integer, Set<String>  >();
        int numCf = 0;		
        
        Set<Variable> allConfigs = fmv.configs();        
        for (Variable cf : allConfigs) {
            Set<String> confFts = ((SetVariable) cf).names();
            numCf++;
            confsString.put(numCf, confFts);
        }
        
        return confsString;
    }	
	
	public Map< Integer, String > getConfigurationsFMtreesV2(FeatureModelVariable fmv) throws Exception{
        
        Map<Integer, Set<String> > mapFtsDeselected = new HashMap<Integer, Set<String> >();
        Map< Integer, String > confsString = new HashMap< Integer, String  >();
        int numCf = 0;

		FeatureModelVariable fmvTest = null;  
		this.createFMStrategy01("fmTest",fmv.toString());
        
        Set<Variable> allConfigs = fmv.configs();        
        for (Variable cf : allConfigs) {
            Set<String> confFts = ((SetVariable) cf).names();
            FeatureConfiguration ftConf = new FeatureConfiguration(confFts, fmv);
            System.out.println("\t" + confFts + " " + ftConf.getConfMap());
            
            numCf++;
            mapFtsDeselected.put(numCf, ftConf.getDeselectFeatures());                        
        }
        
        for (Map.Entry<Integer, Set<String>> entry : mapFtsDeselected.entrySet()) {
        	
        	Set<String> ftsDeselected = entry.getValue();
        	fmvTest = this.createFMStrategy01("fmTest",fmv.toString());
        	fmvTest.removeAllConstraints();
	        
	        boolean ft1 = true;
	        boolean ft2 = true;
	        Iterator<String> it = ftsDeselected.iterator();	    
	        while ( it.hasNext() ){
	        	String ft = it.next();
	        	fmvTest.removeFeature(ft);
	        	
	        	if (ft.equals("colA")) {  ft1 = false; }
	        	if (ft.equals("colB")) {  ft2 = false; }
	        }
	        
	        if( ft1 == true ) fmvTest.getFeature("colA").setMandatoryStatus();
	        if( ft2 == true ) fmvTest.getFeature("colB").setMandatoryStatus();
	        fmvTest.removeAllConstraints();
	        
	        fmvTest.renameFeature("schemax", "schema"+entry.getKey());	        
	        
			String fmvString = fmvTest.toString();
			String fmvString2 = fmvString.replaceAll(" :",":").replaceAll(": ",":").replaceAll(" ;",";").replaceAll("; ",";").replaceAll("\"", "").replaceAll("rel", "").replaceAll("Many", "*");
			
			if(fmvTest.removeFeature("SView")==false)				//TODO: to validate
				confsString.put(entry.getKey(), fmvString2);
	        
			//exportFMtoXMI(fmvTest);        
	        
        	System.out.println("\n\n***** CONFIGURATION "+ entry.getKey() +" *****\n");
	        //System.out.println(" --- fts deselected:\t" + ftsDeselected);	        
	        //System.out.println("\n --- fm pruned - info:");
			System.out.println();
	        System.out.println("Valid model? : \t" + fmvTest.isValid());
	        System.out.println("Number of features: \t" + fmvTest.features().size() );
	        System.out.println("Number of config: \t" + fmvTest.counting() );  
			System.out.println();
			//System.out.println(fmvString);
			//System.out.println();
			//System.out.println("---------------------------------------------------");
			System.out.println(fmvString2);
			
        }
        
        return confsString;
    }
	

	

	

	
	
	// The insert modes OR or XOR don't work in Familiar, even if the documentation affirms it
	public FeatureModelVariable testInsertSubFM() throws Exception {
		FeatureModelVariable fmBase = null;
		String Eo = "A"; 
		String Ed = "B"; 
		String r = "R1"; 
		
		String f = "FM ( ";
		f = f + " SM"     + ": " + "SEnt [SView]"           					+ ";";
		//f = f + " SEnt"   + ": " + "("+ "s"+Eo +"|"+ "s"+Ed +")+"      		+ ";";		
		//f = f + " SEnt"   + ": " + "s"+Eo + " s"+Ed							     		+ ";";
		f = f + " SEnt"   + ": " + "s"+Eo 							     		+ ";";
		
		f = f + " s"+Eo   + ": " + "("+ "rel"+Eo +"|"+ Eo + ")"     	   		+ ";";
		f = f + " rel"+Eo + ": " + r +"_s" +Eo               					+ ";";
		f = f + r+"_s"+Eo + ": " + "("+ Eo+"emb"+Ed +"|"+ Eo+"ref"+Ed + ")"  	+";";
		
		//f = f + " s"+Ed   + ": " + "("+ "rel"+Ed +"|"+ Ed + ")"        		+ ";";
		//f = f + " rel"+Ed + ": " + r +"_s" +Ed               					+ ";";
		//f = f + r+"_s"+Ed + ": " + "("+ Ed+"emb"+Eo +"|"+ Ed+"ref"+Eo + ")"  	+";";
		
		f = f + ")";
		
		fmBase = FM ("fmBase" , f);	
		System.out.println(f);
		System.out.println("\n--- fmBase \n" + fmBase.toString());		

		
		System.out.println("\n--- fmSub");
		/*String fSub = "FM ( ";
		fSub = fSub + " s"+Ed   + ": " + "("+ "rel"+Ed +"|"+ Ed + ")"        		+ ";";
		fSub = fSub + " rel"+Ed + ": " + r +"_s" +Ed               					+ ";";
		fSub = fSub + r+"_s"+Ed + ": " + "("+ Ed+"emb"+Eo +"|"+ Ed+"ref"+Eo + ")"  	+";";
		fSub = fSub + ")";
		*/
		String fSub = "FM ( ";
		//fSub = fSub + " SEnt"   + ": " + "s"+Ed							     		+ ";";
		fSub = fSub + " s"+Ed   + ": " + "("+ "rel"+Ed +"|"+ Ed + ")"        		+ ";";
		fSub = fSub + " rel"+Ed + ": " + r +"_s" +Ed               					+ ";";
		fSub = fSub + r+"_s"+Ed + ": " + "("+ Ed+"emb"+Eo +"|"+ Ed+"ref"+Eo + ")"  	+";";
		fSub = fSub + ")";		
		FeatureModelVariable fmSub = null;
		fmSub = FM ("fmSub" , fSub);						
		//System.out.println("\n \n" + fSub);
		System.out.println(fmSub.toString());

		//fmBase.insert(fmSub, fmBase.getFeature("SEnt"), FeatureEdgeKind.MANDATORY);
		fmBase.insert(fmSub, "SEnt", FeatureEdgeKind.MANDATORY);
		
		String fmSa = "SEnt:(sA|sB);";
		FeatureModelVariable va = new FeatureModelVariable("SEnt", FMBuilder.getInternalFM(fmSa));
		FeatureVariable fmTest = new FeatureVariable("SEnt", va);
		fmBase.setOr(fmTest);		
		System.out.println("\n*** fmBase after setOr ***\n" + fmBase.toString());	
		
		Variable fmV = new FeatureVariable("SEnt", va);
		fmBase.setFeatureAttribute(fmBase.getFeature("SEnt"), fmBase.getFeature("SEnt").getIdentifier(), fmV);
		
		//EorefEd -> sEd;
		/*Expression<String> exp2left = new Expression<String>("SEnt");
		Expression<String> exp2right = new Expression<String>(ExpressionType.OR, "sA", "sB");
		Expression<String> exp1 = new Expression<String>(ExpressionType.FEATURE, exp2left, exp2right);
		 */
		
		String fmS = "SEnt:(sA|sB);";
		FeatureModelVariable v = new FeatureModelVariable("SEnt", FMBuilder.getInternalFM(fmS));
		fmBase.getFeature("SEnt").setFmw(v);
		
		System.out.println("\n--- fmBase ");
		//System.out.println("\n*** fmBase ***\n" + fmBase.toString());		
		return fmBase;
	}
	
	
	
}

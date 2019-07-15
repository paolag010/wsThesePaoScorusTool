package scorusV01.scAlternativesModeling;

import java.util.Set;

import fr.familiar.variable.FeatureModelVariable;
import gsd.synthesis.Expression;
import gsd.synthesis.ExpressionType;
import fr.familiar.FMLTest;


public class AlgoritmeAmiss  extends FMLTest {

	ShowInfoFM showInfoFM = new ShowInfoFM();
	HelpersFM helpersFM = new HelpersFM();
	
	
	// ********************************************
	// **********   	ALGO AMISS	***************
	// ********************************************		
	
	public void runAlgSchemTest() throws Exception{
		
		FeatureModelVariable fmvFull = null;         
	    FeatureModelVariable fmvToWeave = null;
	    FeatureModelVariable fmvSub = null;
	    Set<Expression<String>> fmvFullCnst = null;
	    Set<Expression<String>> fmvToWeaveCnst = null;
	    String fmvSubStringCnst = null;
	    
	    String source,target,assoc = null;
	    
	    
	    source = "A"; target = "B"; assoc = "R1"; 
	    fmvFull = generateFMBaseV2(source, target, assoc);
	    fmvFullCnst = fmvFull.getAllConstraints();
	    
	    System.out.println("\n\n*** fmvFull - AB ***\n");
	    showInfoFM.showFM(fmvFull); 
	    showInfoFM.showFMInfo(fmvFull);
	    
	    source = "B"; target = "C"; assoc = "R2";
	    fmvToWeave 	= generateFMBaseV2(source, target, assoc);
	    fmvToWeaveCnst = fmvToWeave.getAllConstraints();  
	    
	    System.out.println("\n\n*** fmvBC ***\n");
	    showInfoFM.showFM(fmvToWeave);
	    
	    
	    String fRootfmvSub 	= assoc+"_s"+source;									// R2_sB;        
	    String constLeft 	=  source+"ref"+target; 								// BrefC
	    fmvSub = helpersFM.extractSubFM(fmvToWeave, fRootfmvSub);
	    fmvSubStringCnst = helpersFM.getConstraintsFeatureImplies(fmvToWeaveCnst, constLeft);
	    
	    System.out.println("\n\n*** fmvSub ***\n");
	    showInfoFM.showFM(fmvSub);
	    System.out.println("\n Constraints: \n" + fmvSubStringCnst);
	   
	    String featureToInsert = "rel" + source;									//"relB";
	    fmvFull = helpersFM.insertFMXorMode(fmvFull, fmvSub, featureToInsert,fmvSubStringCnst);
	    
	    System.out.println("\n\n*** fmvFull - new ***\n");
	    showInfoFM.showFM(fmvFull);
	    
	}	
	
	public FeatureModelVariable runAlgSchemFMConfs() throws Exception{
		
		FeatureModelVariable fmvFull = null;         
        Set<Expression<String>> fmvFullCnst = null;
        String source,target,assoc = null;
        
        source = "A"; target = "B"; assoc = "r1"; 
        fmvFull = generateFMBaseV2(source, target, assoc);
        fmvFullCnst = fmvFull.getAllConstraints();
        
        System.out.println("\n\n*** fmvFull - AB ***\n");
        showInfoFM.showFM(fmvFull); 
        showInfoFM.showFMInfo(fmvFull);
        
        System.out.println("\nValid configurations:\n");
        
        //return getConfigurationsFMtreesV2(fmvFull);
        return fmvFull;
	}	

	public FeatureModelVariable generateFMBaseV2(String Eo, String Ed, String r) throws Exception {
		FeatureModelVariable fmBase = null;
		
		String s1 = "refsPairs";
		
		String cardD = "Many";
		String cardO = "";
		String rolD = "bLines";
		String rolO = "ag";
		
		String f = "FM ( ";
		f = f + " schemax"     + ": " + "colByClass [SView]"           					+ ";";
		
		f = f + " colByClass"   + ": " + "("+ "col"+Eo +"|"+ "col"+Ed +")+"    	  		+ ";";
		
		f = f + " col"+Eo   + ": " + "("+ "relt"+Eo +"_l0|t"+ Eo + "_l0)"    	    		+ ";";
		f = f + " relt"+Eo + "_l0: " + r +"_" +rolD               					+ ";";
		f = f + r+"_"+rolD + ": " + "(t"+ Eo+"_emb"+cardD+"_t"+Ed +"|t"+ Eo+"_ref"+cardD+"_t"+Ed + ")"  	+";";
		
		f = f + " col"+Ed   + ": " + "("+ "relt"+Ed +"_l0|t"+ Ed + "_l0)"      	  		+ ";";
		f = f + " relt"+Ed + "_l0: " + r +"_" +rolO               					+ ";";
		f = f + r+"_"+rolO + ": " + "(t"+ Ed+"_emb"+cardO+"_t"+Eo +"|t"+ Ed+"_ref"+cardO+"_t"+Eo + ")"  	+";";
		
		f = f + " SView"  + ": " + "refs2"           						    + ";"; 			//No se pueden comenzar los nombres de los features por numero
		f = f + " refs2"  + ": " + Eo + Ed + "refs"     						+ ";";
		
		//f = f + Eo + "ref" + Ed + " -> " + "s" + Ed   						+ ";";
		f = f + Eo+Ed+"refs" + " -> (" + "col"+Eo+" & "+"!"+r +"_" +rolD+" & "+"col"+Ed+" & "+"!"+r +"_" +rolO	+ ");";
		
		f = f + ")";
		
				
		fmBase = FM ("fmBase" , f);
		
		//System.out.println(f);
		//System.out.println("\n--- fmBase \n" + fmBase.toString());
		
		Expression<String> exp = null;
		Expression<String> expComplexLeft = null;
		Expression<String> expComplexRight = null;
		
		
		//Eo -> EoEdrefs OR r_sEd;
		//tEo_l0 -> EoEdrefs OR r_rolO;
		expComplexLeft = new Expression<String>("t"+Eo+"_l0");
		expComplexRight = new Expression<String>(ExpressionType.OR, Eo+Ed+"refs", r +"_"+rolO);
		exp = new Expression<String>(ExpressionType.IMPLIES, expComplexLeft, expComplexRight);
		fmBase.addConstraint(exp);
	
		//Ed -> EoEdrefs OR r_sEo;
		//tEd_l0 -> EoEdrefs OR r_rolD;
		expComplexLeft = new Expression<String>("t"+Ed+"_l0");
		expComplexRight = new Expression<String>(ExpressionType.OR, Eo+Ed+"refs", r +"_" +rolD);
		exp = new Expression<String>(ExpressionType.IMPLIES, expComplexLeft, expComplexRight);
		fmBase.addConstraint(exp);		
		
		//EorefEd -> sEd;
		//tEo_ref<cardD>_tEd -> colEd;
		exp = new Expression<String>(ExpressionType.IMPLIES, "t"+Eo + "_ref" + cardD + "_t" + Ed, "col" + Ed);
		fmBase.addConstraint(exp);
	
		//EdrefEo -> sEo;
		//tEd_ref<cardO>_tEo -> colEo;
		exp = new Expression<String>(ExpressionType.IMPLIES, "t"+Ed + "_ref" + cardO + "_t" + Eo, "col" + Eo);
		fmBase.addConstraint(exp);
	
		
		//EorefEd -> !EdrefEo;
		//tEo_ref<cardD>_tEd -> !tEd_ref<cardO>_tEo
		expComplexLeft = new Expression<String>("t"+Eo + "_ref" + cardD + "_t" + Ed);
		expComplexRight = new Expression<String>(ExpressionType.NOT, "t"+Ed + "_ref" + cardO + "_t" + Eo,null);
		exp = new Expression<String>(ExpressionType.IMPLIES, expComplexLeft, expComplexRight);
		fmBase.addConstraint(exp);
	
		//EorefEd -> !EdembEo;
		//tEo_ref<cardD>_tEd -> !tEd_emb<cardO>_tEo
		expComplexLeft = new Expression<String>("t"+Eo + "_ref" + cardD + "_t" + Ed);
		expComplexRight = new Expression<String>(ExpressionType.NOT, "t"+Ed + "_emb" + cardO + "_t" + Eo,null);
		exp = new Expression<String>(ExpressionType.IMPLIES, expComplexLeft, expComplexRight);
		fmBase.addConstraint(exp);
		
		//EdrefEo -> !EdembEo;
		//tEd_ref<cardO>_tEo -> !tEo_emb<cardD>_tEd
		expComplexLeft = new Expression<String>("t"+Ed + "_ref" + cardO + "_t" + Eo);
		expComplexRight = new Expression<String>(ExpressionType.NOT, "t"+Eo + "_emb" + cardD + "_t" + Ed,null);
		exp = new Expression<String>(ExpressionType.IMPLIES, expComplexLeft, expComplexRight);
		fmBase.addConstraint(exp);
		
		
		return fmBase;
	}
	
	// **********   	ALGO AMISS - Esqueletos	***************
	
	public FeatureModelVariable generateFMAssociation(Object r){		
		return null;
	} 
	
	private Set<Expression<String>> createConstraints(FeatureModelVariable fms){		
		return null;
	} 

	private Set<Expression<String>> createConstraintsIsolation(FeatureModelVariable fms){		
		return null;
	} 	
	
	private Set<Expression<String>> createConstraintsExistency(FeatureModelVariable fms){		
		return null;
	} 	

	private Set<Expression<String>> createConstraintsEmbededLoop(FeatureModelVariable fms){		
		return null;
	} 	
	
	private Set<Expression<String>> createConstraintsReferenceLoop(FeatureModelVariable fms){		
		return null;
	} 	

	private Set<Expression<String>> createConstraintsView(FeatureModelVariable fms){		
		return null;
	} 		
	
	public FeatureModelVariable executeAlgoAmiss(){
		return null;
	}
	
	private FeatureModelVariable fusionAssociations(FeatureModelVariable fmsFull, Object classE){
		return null;
	}
	
	private FeatureModelVariable extractExtension(FeatureModelVariable fmsR, Object e, Object r){
		return null;
	}

	private FeatureModelVariable insertBranchCommonClass(FeatureModelVariable fmsFull, FeatureModelVariable fmsR, Object e){
		return null;
	}
	
	private FeatureModelVariable createBranchVersion(FeatureModelVariable fmsFull, FeatureModelVariable fmsBranch, Object e, Integer csr){
		return null;
	}
	
	private FeatureModelVariable embedBranch(FeatureModelVariable fmsFull, FeatureModelVariable fmsBranchV, String featureExtension){
		return null;
	}
	
	private FeatureModelVariable addExtension(FeatureModelVariable fmsFull, String f, Integer niveau, Object e, Integer cse){
		return null;
	}
	
	private FeatureModelVariable insertBranchView(FeatureModelVariable fmsFull, FeatureModelVariable fmsR, Object r, Object e1, Object e2){
		return null;
	}
	
}

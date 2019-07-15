package scorusV01.scAlternativesModeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import fr.familiar.FMLTest;
import fr.familiar.parser.FMBuilder;
import fr.familiar.variable.Comparison;
import fr.familiar.variable.FeatureModelVariable;
import fr.familiar.variable.SetVariable;
import fr.familiar.variable.Variable;

public class ScorusV01_scGen_Configuration_HelloWorld  extends FMLTest {
	
	scorus_v01_scGen_Configuration_ShowInfoFM showInfoFM = new scorus_v01_scGen_Configuration_ShowInfoFM();
	
	public static void main(String[] args) {
		ScorusV01_scGen_Configuration_HelloWorld m = new ScorusV01_scGen_Configuration_HelloWorld();
		m.execTestHelloFML();
	}		

	public void execTestHelloFML(){
		try {
			this.setUp();			
			testHelloFML();
			this.tearDown();			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ****************************************************
	// **********   	ALTERNATIVES TESTS		***********
	// ****************************************************	
	
	public void testHelloFMLJUnit() throws Exception {
        FeatureModelVariable fmv = FM ("fm1", "FM (A : [B] [C] ;)"); // B and C are optional features of A (root)        
        assertEquals(4.0, fmv.counting(), 0.0);

        // alternate way to build a feature model
        FeatureModelVariable fmv2 = new FeatureModelVariable ("fm2", FMBuilder.getInternalFM("A : [B] [C] ;"));
        assertEquals(Comparison.REFACTORING, fmv2.compare(fmv));
        
        // void or unsatisfiable feature model
        FeatureModelVariable fmv3 = FM ("fm1", "FM (A : B C ; B -> !C; )");   // B and C are mandatory features and B -> !C introduces a logical contradiction     
        assertEquals(0.0, fmv3.counting(), 0.0);
        assertFalse(fmv3.isValid());
        
        // false optional, dead, core, all configs
        FeatureModelVariable fmv4 = FM ("fm1", "FM (A : B [C] [D] ; B -> !C; B -> D; )"); // C is a dead feature and D is a core feature      
        assertEquals(1.0, fmv4.counting(), 0.0);
        assertTrue(fmv4.isValid());        
        assertEquals(1, fmv4.falseOptionalFeatures().size());
        assertEquals(3, fmv4.cores().size());
        assertEquals(1, fmv4.deads().size());
                 
        Set<Variable> allConfigs = fmv.configs();	 
        for (Variable cf : allConfigs) {
            Set<String> confFts = ((SetVariable) cf).names();
            FeatureConfiguration ftConf = new FeatureConfiguration(confFts, fmv);	  
            System.err.println("" + confFts + " " + ftConf.getConfMap());
        }
        
        // slice, merge, etc       
        
    }
	
	public void testHelloFML() throws Exception {
		
		
		// Valid feature model. B and C are optional features of A (root)
		FeatureModelVariable fmv1 = this.createFMStrategy01("fm1","A : [B] [C] ;");                 
		System.out.println("\n*** fmv1 ***\n");
		showInfoFM.showFM(fmv1);
		showInfoFM.showFMInfo(fmv1);
		showInfoFM.showConfigurations(fmv1);   

        
        // alternate way to build a feature model
        FeatureModelVariable fmv2 = new FeatureModelVariable ("fm2", FMBuilder.getInternalFM("A : [B] [C] ;"));
        System.out.println("\n*** fmv2 ***\n"); 
        System.out.print("Compare fmv1 and fmv2: ");
        showInfoFM.showComparation(fmv2, fmv1);
        
        
        // void or unsatisfiable feature model
        // B and C are mandatory features and B -> !C introduces a logical contradiction
        FeatureModelVariable fmv3 = this.createFMStrategy01("fm3","A : B C ; B -> !C;");
		System.out.println("\n*** fmv1 ***\n");
		showInfoFM.showFM(fmv3);
		showInfoFM.showFMInfo(fmv3);
        
        
        // false optional, dead, core, all configs
        // FM where C is a dead feature and D is a core feature  
        FeatureModelVariable fmv4 = this.createFMStrategy01("fm4","A : B [C] [D] ; B -> !C; B -> D;");
        System.out.println("\n*** fmv4 ***\n");
        showInfoFM.showFM(fmv4);
        showInfoFM.showFMInfo(fmv4);
        showInfoFM.showConstraints(fmv4);        
        
                
        System.out.println("\n*** fmv5 - FMBase ***\n");
        FeatureModelVariable fmv5 = this.createFMStrategy01("fm4","A : B [C] [D] ; B -> !C; B -> D;"); 
        fmv5.removeFeature("B");
        showInfoFM.showFM(fmv5);

    }
	
	// *****************************************************************************************
	// **********   	OPERATIONS WITH FM	 - USING FAMILIAR OPTIONS	************************
	// *****************************************************************************************		
		
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
	
}
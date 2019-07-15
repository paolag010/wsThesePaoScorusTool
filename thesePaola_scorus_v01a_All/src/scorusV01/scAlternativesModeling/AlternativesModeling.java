package scorusV01.scAlternativesModeling;

import java.util.Map;
import java.util.Set;
import fr.familiar.FMLTest;
import fr.familiar.variable.FeatureModelVariable;

public class AlternativesModeling  extends FMLTest {

	ShowInfoFM showInfoFM = new ShowInfoFM();
	HelpersFM helpersFM = new HelpersFM();
	AlgoritmeAmiss algoFM = new AlgoritmeAmiss();

	public void execScGenConfiguration(){
		try {
			algoFM.setUp();			
			algoFM.runAlgSchemTest();			
			algoFM.tearDown();			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
	public FeatureModelVariable createFMS(Object modelUml){	
		FeatureModelVariable fms = null;
		try {			
			algoFM.setUp();			
			fms = algoFM.runAlgSchemFMConfs();
			algoFM.tearDown();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return fms;
	}	
	
	public Map< Integer, String > createFMS_Config(){
		Map< Integer, String > configs = null;
		
		try {			
			algoFM.setUp();
			FeatureModelVariable fmvFull = algoFM.runAlgSchemFMConfs();				
			configs =  this.getConfigurationsFMSTrees(fmvFull);
			algoFM.tearDown();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return configs;
	}		
	
	public Map< Integer, Set<String> > getConfigurationsFMS(FeatureModelVariable fms){
		Map< Integer, Set<String> > configs = null;
		try {			
			helpersFM.setUp();			
			configs =  helpersFM.getConfigurationsFM(fms);
			helpersFM.tearDown();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return configs;
	}		

	public Map< Integer, String > getConfigurationsFMSTrees(FeatureModelVariable fms){
		Map< Integer, String > configs = null;
		try {			
			helpersFM.setUp();			
			configs =  helpersFM.getConfigurationsFMtreesV2(fms);
			helpersFM.tearDown();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return configs;
	}

	
}
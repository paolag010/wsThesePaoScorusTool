package scorusV01.scAlternativesModeling;

import java.util.Set;

import fr.familiar.experimental.FGroup;
import fr.familiar.variable.Comparison;
import fr.familiar.variable.FeatureModelVariable;
import fr.familiar.variable.SetVariable;
import fr.familiar.variable.Variable;
import gsd.synthesis.Expression;

public class ShowInfoFM {

	
	public void showFM(FeatureModelVariable fmv){
		System.out.println(fmv.toString());
	}
	
	public void showFMInfo(FeatureModelVariable fmv){		
		System.out.println();
        System.out.println("Valid model? : \t" + fmv.isValid());
        System.out.println("Number of features: \t" + fmv.features().size() );
        System.out.println("Number of config: \t" + fmv.counting() );        
        System.out.println("Number of cores : \t" + fmv.cores().size() );
        System.out.println("Number of deads : \t" + fmv.deads().size() );
	}
	
	public void showConstraints(FeatureModelVariable fmv){
        System.out.println("Constraints :");
        Set<Expression<String>> constraints = fmv.getAllConstraints();	 
        for (Expression<String> cf : constraints) {
            String cnst = cf.toString();            	  
            System.out.println("\t" + cnst);
        }        
        System.out.println();

	}
	
	public void showComparation(FeatureModelVariable fmv1, FeatureModelVariable fmv2){
		
		String result = fmv2.compare(fmv1).toString();
		
		if(result.equals(Comparison.REFACTORING.toString())){
			System.out.print("equals true" );		
		}
		
        System.out.println();
	}
	
	public void showConfigurations(FeatureModelVariable fmv){
        System.out.println("\nValid configurations:\n");
        
        Set<Variable> allConfigs = fmv.configs();	 
        for (Variable cf : allConfigs) {
            Set<String> confFts = ((SetVariable) cf).names();
            FeatureConfiguration ftConf = new FeatureConfiguration(confFts, fmv);	  
            System.out.println("\t" + confFts + " " + ftConf.getConfMap());
        }        
        System.out.println();	
    }

	public void showGroups(FeatureModelVariable fmv){
        System.out.println("\nGroups:\n");
        
        Set<FGroup> allGroups = fmv.getGroups();     
        
        for (FGroup cf : allGroups) {
            String confFts = ((FGroup) cf).toString();
            System.out.println("\t" + confFts);
        }        
        System.out.println();	
    }	
	
}

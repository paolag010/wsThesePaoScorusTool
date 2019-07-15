package scorusV01.scAlternativesModeling;

import org.eclipse.emf.ecore.xmi.XMIResource;
import org.xtext.example.mydsl.fml.FeatureModel;

import fr.familiar.fm.basic.FMLFeatureModelReader;
import fr.familiar.fm.basic.FMLFeatureModelWriter;
import fr.familiar.variable.FeatureModelVariable;

public class scorus_v01_scGen_Configuration_ExportFM {
	
	public void exportFMtoXMI(FeatureModelVariable fmv) throws Exception{
		
		System.out.println("\n\nExporting fm to xmi ...");		
		FeatureModel fm = (FeatureModel) new FMLFeatureModelReader().parseString( fmv.toString() );
		XMIResource xmi = new FMLFeatureModelWriter((org.xtext.example.mydsl.fml.FeatureModel) fm).toXMI("examples/testing/FMs/"+fmv.getIdentifier());
		
		//https://nyx.unice.fr/projects/familiar/wiki/fmload/
		//FeatureModel fm = (FeatureModel) new FMLFeatureModelReader().parseString( fmv.toString() );
		//FeatureModel fm = new FMLFeatureModelReader().parseFile(new File("examples/testing/FMs/fm2.fml"));
		//FeatureModel fm = (FeatureModel) new FMLFeatureModelReader().parseString( "FM (A : [B] [C] ;)" );
		//XMIResource xmi = new FMLFeatureModelWriter(fm).toXMI("examples/testing/FMs/fm2ecore");		// serializing fm to XMI
		//new FMLFeatureModelWriter(fm2).toString() ;													// or to text (String representation) 
		//FeatureModel fm3 = new FMLFeatureModelReader().parseXMIFile(xmi);	
	}
	
	public void exportFMtoSplot(FeatureModelVariable fmv) throws Exception{
		
		System.out.println("Exporting fm to Splot ...");
		FeatureModel fm = (FeatureModel) new FMLFeatureModelReader().parseString( fmv.toString() );		
		fmv.toSPLOT();
		
		//FM_Familiar_FeatureIDE.get(fmv);
		//String s = new FMLtoFeatureIDE(fmv).transformToText();
	}
	
}

package scorusV01.scGraphics;

import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.SwingUtilities;

import fr.familiar.variable.FeatureModelVariable;
import scorusV01.scAlternativesModeling.AlternativesModeling;
import scorusV01.scDerivation.Derivation;
import scorusV01.scGraphics.FramesResults;
import scorusV01.scMetricsEvaluation.MetricsEvaluation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	FramesResults scorusHWgraphics = new FramesResults();
	AlternativesModeling scorusScGenConf = new AlternativesModeling();
	Derivation scorusScGenDer = new Derivation();
	MetricsEvaluation scorusScMet = new MetricsEvaluation();	
	
    public MainFrame() {
        initUI();
    }

    public final void initUI() {

       JPanel panel = new JPanel();
       getContentPane().add(panel);

       panel.setLayout(null);

       JButton btDemoScorus = new JButton("Demo Scorus");
       btDemoScorus.setBounds(50, 60, 160, 30);
       btDemoScorus.addActionListener(this);

       panel.add(btDemoScorus);

       setTitle("Scorus");
       setSize(300, 150);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Demo Scorus")) {
			this.execDemoScorus();
		}
	}    
    
	public void execDemoScorus()
	{		
		System.out.println("*******************************************************");
		System.out.println("************** scGEN - CONFIGURATION ******************");
		System.out.println("*******************************************************\n");
		
		FeatureModelVariable fms = scorusScGenConf.createFMS(null);
		Map< Integer, Set<String> > fmsConfs = scorusScGenConf.getConfigurationsFMS(fms);
		Map< Integer, String > fmsConfsStructured = scorusScGenConf.getConfigurationsFMSTrees(fms);	
		
		scorusHWgraphics.showConfigurations(fmsConfs, "Configurations FM");
		//scorusHWgraphics.showConfigurationsStructured(fmsConfsStructured, "Configurations FM (Structured)");
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scGEN - DERIVATION *********************");
		System.out.println("********************************************************\n");
		
		scorusScGenDer.createMapTypes();		
		Map<String, Object[]> resultsDerivation = scorusScGenDer.derivateSetConfigurations(fmsConfsStructured);
		Map<String, List<String>> mapTypes = scorusScGenDer.getTypes();
		
		//Show schemas (tree + ajschema)
		//for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
		//	Object[] info = entry.getValue();
		//	scorusHWgraphics.showTreeAJS((DefaultTreeModel)info[0], (String)info[1], entry.getKey());	
		//}
		
		System.out.println("\n\n********************************************************");
		System.out.println("*************** scMetrics - EVALUATION *********************");
		System.out.println("********************************************************\n");
		
		Map< String, String > resultsMetrics =  scorusScMet.evaluateAllMetrics(resultsDerivation,mapTypes);
		
		//Show schemas (tree + ajschema) et metrics
		for (Map.Entry<String, Object[]> entry : resultsDerivation.entrySet()) {
			Object[] infoDerivation = entry.getValue();
			String infoMetrics = resultsMetrics.get(entry.getKey());			
			scorusHWgraphics.showTreeAJSMetrics((DefaultTreeModel)infoDerivation[0], (String)infoDerivation[1], entry.getKey(), infoMetrics);	
		}
	}
	
    public static void main(String[] args) {       
 	   MainFrame ex = new MainFrame();
        ex.setVisible(true);       
    }
    
}
package scorusV01.scGraphics;

import java.awt.GridLayout;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;

public class FramesResults {

	
	public void showTree(DefaultTreeModel ajsTreeModel)
	{
        JFrame v = new JFrame();
        v.setLayout(new GridLayout());
        
        
        JTree tree = new JTree(ajsTreeModel);
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        
        JScrollPane psTree = new JScrollPane(tree);
        TitledBorder psTreeBorder = BorderFactory.createTitledBorder("AJStree");
        psTreeBorder.setTitleJustification(TitledBorder.CENTER);
	    psTree.setBorder(psTreeBorder);
        
        
        v.getContentPane().add(psTree);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void showTreeAJS(DefaultTreeModel ajsTreeModel,  String ajs, String titleFrame)
	{
        JFrame v = new JFrame();
        v.setLayout(new GridLayout());
        v.setTitle(titleFrame);
        
        JTree tree = new JTree(ajsTreeModel);
        for (int i = 0; i < tree.getRowCount(); i++) { 
            tree.expandRow(i);
        }
        
        JScrollPane psTree = new JScrollPane(tree);
        TitledBorder psTreeBorder = BorderFactory.createTitledBorder("AJStree");
        psTreeBorder.setTitleJustification(TitledBorder.CENTER);
	    psTree.setBorder(psTreeBorder);
	    
	    JTextArea taAJS = new JTextArea(ajs);
	    taAJS.setBorder(new EmptyBorder(10, 10, 10, 10));
	    JScrollPane psAJS = new JScrollPane(taAJS);
	        TitledBorder psAJSBorder = BorderFactory.createTitledBorder("AJSchema");
	        psAJSBorder.setTitleJustification(TitledBorder.CENTER);
	    psAJS.setBorder(psAJSBorder);
        
	    
        v.getContentPane().add(psTree);
        v.getContentPane().add(psAJS);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void showTreeAJSMetrics(DefaultTreeModel ajsTreeModel , String ajs, String titleFrame, String metrics)
	{
        JFrame v = new JFrame();
        v.setLayout(new GridLayout());
        v.setTitle(titleFrame);
        
        JTree tree = new JTree(ajsTreeModel);      
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        
        JScrollPane psTree = new JScrollPane(tree);
	        TitledBorder psTreeBorder = BorderFactory.createTitledBorder("AJStree");
	        psTreeBorder.setTitleJustification(TitledBorder.CENTER);
        psTree.setBorder(psTreeBorder);
        
        JTextArea taAJS = new JTextArea(ajs);
        taAJS.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane psAJS = new JScrollPane(taAJS);
	        TitledBorder psAJSBorder = BorderFactory.createTitledBorder("AJSchema");
	        psAJSBorder.setTitleJustification(TitledBorder.CENTER);
        psAJS.setBorder(psAJSBorder);

        JTextArea taMetrics = new JTextArea(metrics);
        taMetrics.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane psMetrics = new JScrollPane(taMetrics);        
	        TitledBorder psMetricsBorder = BorderFactory.createTitledBorder("METRICS");
	        psMetricsBorder.setTitleJustification(TitledBorder.CENTER);
        psMetrics.setBorder(psMetricsBorder);
        
        v.getContentPane().add(psTree);
        v.getContentPane().add(psAJS);
        v.getContentPane().add(psMetrics);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	public void showConfigurations(Map< Integer, Set<String>  > configs , String titleFrame)
	{
        JFrame v = new JFrame();
        v.setLayout(new GridLayout());
        v.setTitle(titleFrame);
        
		String infoConfigs = "";
		for (Map.Entry<Integer, Set<String> > entry : configs.entrySet()) {
			String confNumber  = entry.getKey() + "";
			Set<String> confFeatures = configs.get(entry.getKey());
			
			infoConfigs += "CONFIGURATION " + confNumber + ": ";
			for (String feature : confFeatures) {
				infoConfigs += feature + ", ";
			}
			infoConfigs += "\n\n";
		}
        
        JTextArea taConfigurations = new JTextArea(infoConfigs);
        taConfigurations.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane psConfigurations = new JScrollPane(taConfigurations);        
	        TitledBorder psConfigurationsBorder = BorderFactory.createTitledBorder(titleFrame);
	        psConfigurationsBorder.setTitleJustification(TitledBorder.CENTER);
        psConfigurations.setBorder(psConfigurationsBorder);
        
        v.getContentPane().add(psConfigurations);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}	
	
	public void showConfigurationsStructured(Map< Integer, String > configs , String titleFrame)
	{
        JFrame v = new JFrame();
        v.setLayout(new GridLayout());
        v.setTitle(titleFrame);
        
		String infoConfigs = "";
		for (Map.Entry<Integer, String> entry : configs.entrySet()) {
			String confNumber  = entry.getKey() + "";
			String confInfo = configs.get(entry.getKey());			
			infoConfigs += "CONFIGURATION " + confNumber + ": \n" + confInfo + "\n\n";
		}
        
        JTextArea taConfigurations = new JTextArea(infoConfigs);
        taConfigurations.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane psConfigurations = new JScrollPane(taConfigurations);        
	        TitledBorder psConfigurationsBorder = BorderFactory.createTitledBorder(titleFrame);
	        psConfigurationsBorder.setTitleJustification(TitledBorder.CENTER);
        psConfigurations.setBorder(psConfigurationsBorder);
        
        v.getContentPane().add(psConfigurations);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}	
	
}

package uml.load;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;


public class LoadUML {

	public static void main(String[] args) {
		File modelFile = new File(args[0]);
		ResourceSet res = new ResourceSetImpl();
		
		UMLResourcesUtil.init(res);
		Resource modelRes = res.getResource(URI.createFileURI(modelFile.getAbsolutePath()), true);
		
		Model model = (Model) modelRes.getContents().get(0);
		
		Class root = null;
		for (Element element : model.getOwnedElements()) {
			if (element instanceof Class) {
				root = (Class)element;
			}
		}
		
		fusion(root, new HashSet<Class>());
	}

	public static void fusion(Class root, Set<Class> visited) {
		
		if (visited.contains(root)) {
			return;
		}
		
		System.out.println(root.getName());
		
		visited.add(root);
		
		for (Association association : root.getAssociations()) {
			
			Property outgoing = outgoing(root,association);
			Property incoming = outgoing.getOpposite();
			
			System.out.println("from "+incoming.getType().getName()+" role "+incoming.getName()+"["+incoming.getLower()+".."+incoming.getUpper()+"]"+"<-"+association.getName()+"-> to "+outgoing.getType().getName()+" role "+outgoing.getName()+"["+outgoing.getLower()+".."+outgoing.getUpper()+"]");
			
			fusion((Class)outgoing.getType(), visited);
		}
	}
	
	public static Property outgoing(Class source, Association association) {
		Property role1 = association.getMemberEnds().get(0);
		Property role2 = association.getMemberEnds().get(1);
		
		return role1.getType().equals(source) ? role2 : role1;
	}
}

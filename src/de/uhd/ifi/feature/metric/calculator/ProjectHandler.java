/**
 * @author Aleksandr Soloninov
 *
 */
package de.uhd.ifi.feature.metric.calculator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;

/**
 * The Class Util selects the right project from the package explorer.
 * Final. Version 1.0.12
 * @author Aleksandr Soloninov
 */
public class ProjectHandler {

	/**
	 * Gets the current project.
	 *
	 * @return the current project
	 */
	@SuppressWarnings("restriction")
	public static IProject getCurrentProject(){    
        ISelectionService selectionService = Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();   
        ISelection selection = selectionService.getSelection();    
        IProject project = null;    
        if(selection instanceof IStructuredSelection) {    
            Object element = ((IStructuredSelection)selection).getFirstElement(); 
            if (element instanceof IResource) {    
                project= ((IResource)element).getProject();    
            } else if (element instanceof PackageFragmentRootContainer) {    
                IJavaProject jProject =  ((PackageFragmentRootContainer)element).getJavaProject();    
                project = jProject.getProject();    
            } else if (element instanceof IJavaElement) {    
                IJavaProject jProject= ((IJavaElement)element).getJavaProject();    
                project = jProject.getProject();    
            }    
        } 
        return project;    
    }
	
	
}

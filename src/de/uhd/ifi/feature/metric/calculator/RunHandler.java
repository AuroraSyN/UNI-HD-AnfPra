package de.uhd.ifi.feature.metric.calculator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.uhd.ifi.feature.metric.calculator.support.Provider;

// Final. Version 1.0.0
/**
 * The Class RunHandler.
 * Write flag in Provider and show the FMC view
 * @author Aleksandr Soloninov
 */
public class RunHandler extends AbstractHandler implements IHandler {
	
	/**
	 *  Set flag in Provider to false
	 *  and show's FMC view
	 * @param event the event
	 * @return the object
	 * @throws ExecutionException the execution exception
	 * @see org.eclipse.core.commands.
	 * IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Provider.writeFlag(false);																					
        final IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();  
        final IViewPart myView = workbenchPage.findView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
        workbenchPage.hideView(myView);
        try {
        	workbenchPage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
			workbenchPage.hideView(myView);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}

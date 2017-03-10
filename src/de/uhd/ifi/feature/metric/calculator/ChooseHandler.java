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
/**
 * The Class ChooseHandler. Final Version 1.0.4
 * Write flag in Provider and open SelectDialog, after select show the FMC view
 * @author Aleksandr Soloninov
 */

public class ChooseHandler extends AbstractHandler implements IHandler {
		
	/**
	 *  Set flag in Provider to true
	 *  and show's FMC view
	 * @param event the event
	 * @return the object
	 * @throws ExecutionException the execution exception
	 * @see org.eclipse.core.commands.
	 * IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Provider.writeFlag(true);
        final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        final IViewPart View = activePage.findView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
        activePage.hideView(View);
        try {        	
			activePage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

		
}

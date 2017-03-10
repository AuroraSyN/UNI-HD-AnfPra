
package de.uhd.ifi.feature.metric.calculator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
// Final. Version 1.0.0
/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** The Constant PLUGIN_ID. */
	// The plug-in ID
	public static final String PLUGIN_ID = "de.uhd.ifi.feature.metric.calculator"; //$NON-NLS-1$

	/** The plugin. */
	// The shared instance
	private static Activator plugin;
	
	/**
	 * (non-Javadoc).
	 *
	 * @param context the context
	 * @throws Exception the exception
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * (non-Javadoc).
	 *
	 * @param context the context
	 * @throws Exception the exception
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}

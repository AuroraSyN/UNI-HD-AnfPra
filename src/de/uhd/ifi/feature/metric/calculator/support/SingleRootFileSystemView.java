/*
 * 
 */
package de.uhd.ifi.feature.metric.calculator.support;

import java.io.File;
import java.io.IOException;


import javax.swing.filechooser.FileSystemView;
// TODO: Auto-generated Javadoc
/**
 * The Class SingleRootFileSystemView.
 */
public class SingleRootFileSystemView extends FileSystemView {
	
	/** The root. */
	final private File root;
	
	/** The roots. */
	final private File[] roots = new File[1];	
	/**
	 * Instantiates a new single root file system view.
	 *
	 * @param path the path
	 */
	public SingleRootFileSystemView(final File path) {
		super();
		try{
			root = path.getCanonicalFile();
			roots[0] = root;
		}
		catch(IOException e){
			throw new IllegalArgumentException( e );
		}
		if ( !root.isDirectory() ){
			final String message = root + " is not a directory";
			throw new IllegalArgumentException( message );
		}
	}

	/**
	 * Creates the new folder.
	 *
	 * @param containingDir the containing dir
	 * @return the file
	 * @see javax.swing.filechooser.FileSystemView#createNewFolder(java.io.File)
	 */
	@Override
	public File createNewFolder(final File containingDir) {
		final File folder = new File(containingDir, "New Folder");
		folder.mkdir();
		return folder;
	}


	/**
	 * Gets the default directory.
	 *
	 * @return the default directory
	 * @see javax.swing.filechooser.FileSystemView#getDefaultDirectory()
	 */
	@Override
	public File getDefaultDirectory(){
		return root;
	}


	/**
	 * Gets the home directory.
	 *
	 * @return the home directory
	 * @see javax.swing.filechooser.FileSystemView#getHomeDirectory()
	 */
	@Override
	public File getHomeDirectory(){
		return root;
	}


	/**
	 * Gets the roots.
	 *
	 * @return the roots
	 * @see javax.swing.filechooser.FileSystemView#getRoots()
	 */
	@Override
	public File[] getRoots(){
		return roots;
	}
}

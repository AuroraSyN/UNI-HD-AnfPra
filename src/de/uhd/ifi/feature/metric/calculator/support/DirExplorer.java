package de.uhd.ifi.feature.metric.calculator.support;

import java.io.File;
/**
 * The Class DirExplorer. Final version.
 * Version 1.0.0
 */
public class DirExplorer {
    /**
     * The Interface FileHandler.
     */
    public interface FileHandler {
        /**
         * Handle.
         *
         * @param level the level
         * @param path the path
         * @param file the file
         */
        void handle(int level, String path, File file);
    }
 
    /**
     * The Interface Filter.
     */
    public interface Filter {
        /**
         * Interested.
         *
         * @param level the level
         * @param path the path
         * @param file the file
         * @return true, if successful
         */
        boolean interested(int level, String path, File file);
    }
 
    /** The file handler. */
    private FileHandler fileHandler;
    
    /** The filter. */
    private Filter filter;
 
    /**
     * Instantiates a new dir explorer.
     *
     * @param filter the filter
     * @param fileHandler the file handler
     */
    public DirExplorer(final Filter filter, final FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }
 
    /**
     * Explore.
     *
     * @param root the root
     */
    public void explore(final File root) {
        explore(0, "", root);
    }
 
    /**
     * Explore.
     *
     * @param level the level
     * @param path the path
     * @param file the file
     */
	private void explore(final int level, final String path, final File file) {
    	if (file == null){
    		System.out.println("file is null");
    	} else {
        if (file.isDirectory()) {
            for (final File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (filter.interested(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        	}
    	}
    }
 
}

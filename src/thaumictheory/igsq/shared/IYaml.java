package thaumictheory.igsq.shared;
/**
 * Interface of The yaml implementation.
 * @see #createFiles()
 * @see #setField(String, String, Object)
 * @see #defaultField(String, String, Object)
 * @see #deleteField(String, String)
 * @see #getField(String, String)
 * @see #getPaths()
 * @see #discardAllFiles()
 * @see #discardFile(String)
 * @see #loadAllFiles()
 * @see #loadFile(String)
 * @see #saveAllFiles()
 * @see #saveFile(String)
 * @see #PATHS
 * @author ThaumicTheory
 *
 */
public interface IYaml 
{
    /**
     * PATHS is a String array of all of the files to be created into {@link java.io.File}. The path begins from the plugin Data directory.
     * @apiNote This is only for the default {@link #PATHS} implementation which includes the config and internal files ONLY. If you wish to change the files (you almost certainly will!) override {@link #getPaths()} in your implementation of this interface.
     */
    public static final String[] PATHS = {"config.yaml","internal.yaml"};
	/**
     * Creates all the files if they dont already exist. Creates instance of all files in {@link #PATHS} and the folders required to hold all the files.
     */
	public void createFiles();
	
	/**
	 * Stores an object in a yaml file.
	 * @apiNote Overrides a value at a node from a path with data.
	 * @param node the node inside the file that the data will be stored to.
	 * @param path a path in {@link #PATHS}.
	 * @param data the {@link Object} to be stored in the field.
	 * @return a boolean which is true if the field was successfully updated, false otherwise.
	 */
	public boolean setField(String node,String path, Object data);
	/**
	 * Stores an object in a yaml file if a value doesn't already exist there.
	 * @apiNote Overrides a value at a node from a path with data if the data is null.
	 * @param node the node inside the file that the data will be stored to.
	 * @param path a path in {@link #PATHS}.
	 * @param data the {@link Object} to be stored in the field.
	 * @return a boolean which is true if the field was successfully updated or not required to update, false otherwise.
	 */
	public boolean defaultField(String node,String path,Object data);
	
	/**
	 * Gets an object from a yaml file.
	 * @apiNote Overrides a value at a node from a path with data if the data is null.
	 * @param node the node inside the file that the data will be stored to.
	 * @param path a path in {@link #PATHS}.
	 * @return data the {@link Object} extracted from the yaml file. Null if the node doesnt exist
	 */
	public Object getField(String node, String path);
	
	/**
	 * Loads a yaml file to the cache.
	 * @param path a path in {@link #PATHS}.
	 * @return a boolean which is true if the file was successfully loaded, false otherwise.
	 */
	public boolean loadFile(String path);
	/**
	 * Saves the cache to a yaml file.
	 * @param path a path in {@link #PATHS}.
	 * @return a boolean which is true if the file was successfully saved, false otherwise.
	 */
	public boolean saveFile(String path);
	/**
	 * Discards current cache changes of a yaml file.
	 * @param path a path in {@link #PATHS}.
	 * @return a boolean which is true if the file was successfully discarded, false otherwise.
	 */
	public boolean discardFile(String path);
	
	
	/**
	 * Gets the current paths of the yaml file.
	 * @apiNote This is only for the default {@link #getPaths()} implementation which includes the config and internal files ONLY. If you wish to change the files (you almost certainly will!) you will need to override this method in your implementation of this interface.
	 * @return an instance of @link {@link #PATHS}.
	 */
	public default String[] getPaths() 
	{
		return PATHS;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Deletes a field with its node in a yaml file
	 * @apiNote You are not expected to override this method for standard implementations of this interface.
	 * @param node the node inside the file that the data will be stored to.
	 * @param path a path in {@link #PATHS}.
	 * @return a boolean which is true if the node was successfully deleted, false otherwise.
	 */
	public default boolean deleteField(String node,String path) 
	{
		return setField(node, path, null);
	}
	/**
	 * Loads all yaml files to the cache.
	 * @apiNote You are not expected to override this method for standard implementations of this interface.
	 * @return a boolean which is true if all the files were successfully loaded, false if 1 or more failed.
	 */
	public default boolean loadAllFiles() 
	{
    	boolean flag = false;
    	for(int i = 0; i < getPaths().length;i++)  
    	{
    		if(!loadFile(getPaths()[i])) flag = true;
    	}
    	return !flag;
	}
	/**
	 * Saves all the caches to their respective yaml file.
	 * @apiNote You are not expected to override this method for standard implementations of this interface.
	 * @return a boolean which is true if all the files were successfully saved, false if 1 or more failed.
	 */
	public default boolean saveAllFiles() 
	{
    	boolean flag = false;
    	for(int i = 0; i < getPaths().length;i++)  
    	{
    		if(!saveFile(getPaths()[i])) flag = true;
    	}
    	return !flag;
	}
	/**
	 * Discards all current cache changes for all the yaml files.
	 * @apiNote You are not expected to override this method for standard implementations of this interface.
	 * @return a boolean which is true if all the files were successfully discarded, false if 1 or more failed.
	 */
	public default boolean discardAllFiles() 
	{
    	boolean flag = false;
    	for(int i = 0; i < getPaths().length;i++)  
    	{
    		if(!discardFile(getPaths()[i])) flag = true;
    	}
    	return !flag;
	}
}

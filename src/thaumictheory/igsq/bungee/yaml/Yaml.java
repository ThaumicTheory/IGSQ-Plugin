package thaumictheory.igsq.bungee.yaml;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Communication;
import thaumictheory.igsq.shared.IYaml;

public class Yaml implements IYaml
{
	public static final String[] PATHS = {"config.yaml","player.yaml","internal.yaml","message.yaml","security.yaml","role.yaml"};
    private static final String[] SYNCED_PATHS = {"player.yaml","security.yaml","role.yaml"};
    private static File[] files;
    private static Configuration[] configurations;
    private static final ConfigurationProvider PROVIDER = ConfigurationProvider.getProvider(YamlConfiguration.class);
    
    @Override
    public void createFiles() 
    {
		 try
         {
	    	if (!Common.bungee.getDataFolder().exists()) 
	    	{
	    		Common.bungee.getDataFolder().mkdir();
	    	}
	    	files = new File[getPaths().length];
	    	configurations = new Configuration[getPaths().length];
	    	for (int i = 0; i < getPaths().length;i++) 
	    	{
	    		files[i] = new File(Common.bungee.getDataFolder(),getPaths()[i]);
	    		files[i].createNewFile();
	    	}
	    	
         }
         catch (Exception e)
		 {
			e.printStackTrace();
		 }
    	
    }
    
    @Override
    public boolean defaultField(String node,String path,Object data) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path))
    		{
    			if(!configurations[i].contains(node)) return setField(node,path,data);
    			else return true;
    		}
    	}
    	return false;
    }
    
    public Object getField(String node,String path) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path)) 
    		{
    			return configurations[i].get(node);
    		}
    	}
    	return false;
    }
    
    @Deprecated
    public static String getFieldString(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    			String data = configurations[i].getString(path);
    			for (String string : SYNCED_PATHS) 
    			{
    				if(string.equalsIgnoreCase(fileName)) 
    				{
            			Communication.sendConfigUpdate(path,fileName,data);
            			break;
    				}
    			}
    			return data;
    		}
    	}
    	return null;
    }
    
    @Deprecated
    public static Boolean getFieldBool(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    			return configurations[i].getBoolean(path);
    		}
    	}
    	return false;
    }
    
    @Deprecated
    public static int getFieldInt(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    			return configurations[i].getInt(path);
    		}
    	}
    	return -1;
    }
    
    @Override
    public boolean setField(String node,String path,Object data) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path))
    		{
    			
    			configurations[i].set(node, data);
    			for (String string : getSyncedPaths()) 
    			{
    				if(string.equalsIgnoreCase(path)) 
    				{
    					Communication.sendConfigUpdate(node,path,data);
            			return true;
    				}
    			}
    			return true;
    		}
    	}
    	return false;
    }
    @Override
    public String[] getPaths() 
    {
    	return PATHS;
    }
    public String[] getSyncedPaths() 
    {
    	return SYNCED_PATHS;
    }
    
    @Override
    public boolean loadFile(String path) 
    {
    	try 
    	{
        	for(int i = 0; i < getPaths().length;i++) 
        	{
        		if(getPaths()[i].equalsIgnoreCase(path))
        		{
        			configurations[i] = PROVIDER.load(files[i]);
        			return true;
        		}
        	}
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			return false;
		}
    	return false;
    }
    
    @Override
    public boolean saveFile(String path) 
    {
    	try 
    	{
        	for(int i = 0; i < getPaths().length;i++) 
        	{
        		if(getPaths()[i].equalsIgnoreCase(path))
        		{
        			PROVIDER.save(configurations[i], files[i]);
        			return true;
        		}
        	}
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			return false;
		}
    	return false;
    }
    @Override
    public boolean discardFile(String path) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path))
    		{
    			configurations[i] = null;
    			files[i] = null;
    			return true;
    		}
    	}
    	return false;
    }
}

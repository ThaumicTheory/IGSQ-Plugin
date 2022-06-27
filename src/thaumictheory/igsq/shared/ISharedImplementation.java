package thaumictheory.igsq.shared;

import java.util.UUID;

public interface ISharedImplementation 
{
	public String getPlayerName(UUID uuid);
	
	public default String getPlayerName(String uuid) 
	{
		return getPlayerName(uuid);
	}
	
	public InstanceType getInstanceType();
}

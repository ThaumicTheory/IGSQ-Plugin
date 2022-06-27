package thaumictheory.igsq.spigot;

import java.util.UUID;

import org.bukkit.Bukkit;

import thaumictheory.igsq.shared.ISharedImplementation;
import thaumictheory.igsq.shared.InstanceType;

public class SpigotImplementor implements ISharedImplementation 
{

	@Override
	public String getPlayerName(UUID uuid) 
	{
		return Bukkit.getPlayer(uuid).getName();
	}

	@Override
	public InstanceType getInstanceType() {
		return InstanceType.SPIGOT;
	}

}

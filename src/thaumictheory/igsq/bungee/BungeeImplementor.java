package thaumictheory.igsq.bungee;

import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import thaumictheory.igsq.shared.ISharedImplementation;
import thaumictheory.igsq.shared.InstanceType;

public class BungeeImplementor implements ISharedImplementation{

	@Override
	public String getPlayerName(UUID uuid) 
	{
		return ProxyServer.getInstance().getPlayer(uuid).getName();
	}

	@Override
	public InstanceType getInstanceType() {
		return InstanceType.BUNGEE;
	}

}

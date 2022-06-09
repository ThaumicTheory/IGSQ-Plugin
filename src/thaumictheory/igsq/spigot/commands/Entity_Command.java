package thaumictheory.igsq.spigot.commands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;

public class Entity_Command {
	private CommandSender sender;
	public Boolean result;
	private List<String> args = new ArrayList<>();
	private String display = "Yourself";
	
	public Entity_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = EntityQuery();
	}
	
    private boolean Entity() {
        EntityType entitytype;
        Player player = (Player) sender;
        try
        {
            entitytype = EntityType.valueOf(args.get(0).toUpperCase());
        }
        catch(Exception exception)
        {
            sender.sendMessage(Messaging.chatFormatter("&#CD0000This Entity could not be found!"));
            return false;
        }
        Common.spigot.getServer().getPlayer(player.getUniqueId()).getWorld().spawnEntity(player.getLocation(), entitytype);
        sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#00FFC7"+ args.get(0).toLowerCase() +" &#58FFFFto " + display));
        return true;

    }
	

	private boolean EntityQuery() 
    {
        if(sender instanceof Player && Common_Command.requirePermission("igsq.entity",sender))
        {
            if(Entity()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Messaging.chatFormatter("&#FFFF00entity [entity_ID]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
            return false;
        }
}
	}
package me.murrobby.igsq.spigot.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class Entity_Command {
	private Main_Spigot plugin;
	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	private String[] args;
	private Player player;
	private String display = "Yourself";
	
	public Entity_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		this.sender = sender;
		this.args = args;
		result = EntityQuery();
	}
	
    private boolean Entity() {
        EntityType entitytype;
        try
        {
            entitytype = EntityType.valueOf(args[0].toUpperCase());
        }
        catch(Exception exception)
        {
            sender.sendMessage(Common_Spigot.ChatFormatter("&#CD0000This Entity could not be found!"));
            return false;
        }
        if(args.length == 1 || args[1].equalsIgnoreCase("fake")) 
		{
        	return true;
		}
        else if(args[1].equalsIgnoreCase("real")) 
        {
            plugin.getServer().getPlayer(player.getUniqueId()).getWorld().spawnEntity(player.getLocation(), entitytype);
            sender.sendMessage(Common_Spigot.ChatFormatter("&#58FFFFGave &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
            return true;
        }
        else 
        {
        	
        	return false;
        }

    }
	

	private boolean EntityQuery() 
    {
        if(commands.IsPlayer() && commands.RequirePermission("igsq.entity"))
        {
            if(Entity()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Common_Spigot.ChatFormatter("&#FFFF00entity [entity_ID]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Common_Spigot.ChatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
            return false;
        }
}
	}
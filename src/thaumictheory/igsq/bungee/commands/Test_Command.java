package thaumictheory.igsq.bungee.commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.bungee.Yaml;

public class Test_Command  extends Command implements TabExecutor
{
	public Test_Command() 
	{
		super("test");
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args)
	{
		List<String> options = new ArrayList<String>();
		if(args.length == 1) 
		{
			for (String commands : Yaml.fileNames) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		if(args.length == 2) 
		{
			if(args[0].equalsIgnoreCase("player")) 
			{
				String[] types = {"discord.2fa.status","discord.id","discord.username","discord.nickname","discord.role","discord.founder","discord.birthday","discord.nitroboost","discord.supporter","discord.developer"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
		}
		return options;
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		
		if(sender instanceof ProxiedPlayer && args.length == 3) 
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(player.hasPermission("igsq.test")) 
			{
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				try
				{
					out.writeUTF(args[0]);
					if(args[0].equalsIgnoreCase("player")) out.writeUTF((player.getUniqueId().toString() + "." +args[1]));
					else out.writeUTF(args[1]);
					out.writeUTF(args[2]);
					player.getServer().getInfo().sendData( "igsq:yml", stream.toByteArray());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission")));
		}
		else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000This is an invalid arg length!")));
		
	}
}

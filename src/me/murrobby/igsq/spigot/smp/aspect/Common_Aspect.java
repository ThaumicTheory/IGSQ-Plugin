package me.murrobby.igsq.spigot.smp.aspect;

import java.util.ArrayList;
import java.util.List;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Common_Aspect 
{
	private static List<Base_Aspect> aspects = new ArrayList<>();
	public static final String ASPECT_GUI_NAME = "&#00FF00Aspect Selection";
	public static final String ASPECT_GUI_LORE = " &#DAB210Lore";

	public static List<Base_Aspect> getAspects() 
	{
		if(aspects == null || aspects.size() == 0) setAspects();
		return aspects;
	}

	private static void setAspects() 
	{
		aspects.add(new None_Aspect());
		aspects.add(new Water_Aspect());
		aspects.add(new Uncertain_Aspect());
		aspects.add(new Snow_Aspect());
		aspects.add(new Air_Aspect());
		aspects.add(new Abyss_Aspect());
	}
	public static boolean isPlayerInAspectGui(Player_SMP player)
    {
		return player.getPlayer().getOpenInventory().getTitle().equals(Messaging.chatFormatter(ASPECT_GUI_NAME));
    }
	
}

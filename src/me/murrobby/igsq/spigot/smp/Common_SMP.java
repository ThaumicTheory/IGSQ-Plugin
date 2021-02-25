package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;

import me.murrobby.igsq.spigot.Common;

public class Common_SMP 
{
	public static Team_SMP getAdminTeam()
	{
		Team_SMP adminTeam = Team_SMP.getTeamFromName("protected");
		if(adminTeam == null) adminTeam = new Team_SMP("Protected",null);
		return adminTeam;
	}

	public static boolean isProtectedName(String name) 
	{
		List<String> protectedNames = new ArrayList<>();
		protectedNames.add("WILDERNESS");
		protectedNames.add("PROTECTED");
		protectedNames.add("SPAWN");
		protectedNames.add("&");
		protectedNames.addAll(Common.illegalChats);
		for(String protectedName : protectedNames) if(name.toUpperCase().contains(protectedName.toUpperCase())) return true;
		return false;
	}
}

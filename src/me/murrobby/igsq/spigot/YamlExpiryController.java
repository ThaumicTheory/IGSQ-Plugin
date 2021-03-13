package me.murrobby.igsq.spigot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.smp.Team_SMP;

public class YamlExpiryController {
	public static List<String> getAlliance() {
		String allystring = Yaml.getFieldString("smp.ally", "expiry");
		 List<String> ally = new ArrayList<String>();
		 ally.addAll(Arrays.asList(allystring.split("<:>")));
		 return ally;
	}
	public static void setAllianceField(String alliances) {
		
		Yaml.updateField("smp.ally", "expiry", alliances);
	}

	private static void setAlliance(List<String> newAlliance) 
	{
		if(newAlliance.size() == 0) 
		{
			return;
		}
		String membersString = newAlliance.get(0).toString();
		for(int i = 1; i < newAlliance.size();i++) membersString += " " + newAlliance.get(i).toString();
		setAllianceField(membersString);
	}
	public static void addAlliance(Team_SMP team1, Team_SMP team2) 
	{
		List<String> alliances = getAlliance();
		alliances.add(team1.getUID().toString() + "<:>" + team2.getUID().toString() + "<:>" + String.valueOf(System.currentTimeMillis()));
		setAlliance(alliances);
	}
	public static void removeAlliance(Team_SMP team1, Team_SMP team2) 
	{
		List<String> alliances = getAlliance();
		alliances.remove(team1.getUID().toString() + "<:>" + team2.getUID().toString() + "<:>" + String.valueOf(System.currentTimeMillis()));
		setAlliance(alliances);
	}

	public static void applyDefault() 
    {
    	Yaml.addFieldDefault("smp.ally", "expiry", "");
    }
	
}

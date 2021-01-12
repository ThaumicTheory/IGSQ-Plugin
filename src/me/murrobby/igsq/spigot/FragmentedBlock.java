package me.murrobby.igsq.spigot;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class FragmentedBlock 
{
	private BlockData data;
	private Location location;
	public FragmentedBlock(Block block) 
	{
		data = block.getBlockData();
		location = block.getLocation();
	}
	public void place() 
	{
		location.getBlock().setBlockData(data,false);
	}
	
	public Location getLocation() 
	{
		return location;
	}
}

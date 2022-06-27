package thaumictheory.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import thaumictheory.igsq.spigot.Common;

public class BlockCluster_SMP 
{
	private Random random = new Random();
	private ArrayList<FragmentedBlock_SMP> blocks = new ArrayList<FragmentedBlock_SMP>();
	private static ArrayList<BlockCluster_SMP> clusters = new ArrayList<BlockCluster_SMP>();
	private int decayStart = 600;
	private int decayInterval = 5;
	
	private int tickingTask = -1;
	public BlockCluster_SMP(List<Block> blocks) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		core();
	}
	public BlockCluster_SMP(List<Block> blocks,int decayStart) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		this.decayStart = decayStart;
		core();
	}
	public BlockCluster_SMP(List<Block> blocks,int decayStart,int decayInterval) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		this.decayStart = decayStart;
		this.decayInterval = decayInterval;
		core();
	}
	
	public BlockCluster_SMP(ArrayList<Block> blocks) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		core();
	}
	public BlockCluster_SMP(ArrayList<Block> blocks,int decayStart) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		this.decayStart = decayStart;
		core();
	}
	public BlockCluster_SMP(ArrayList<Block> blocks,int decayStart,int decayInterval) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock_SMP(block));
		clusters.add(this);
		this.decayStart = decayStart;
		this.decayInterval = decayInterval;
		core();
	}
	
	public BlockCluster_SMP revertFully() 
	{
		for(FragmentedBlock_SMP block : blocks) 
		{
			block.place();
		}
		blocks.clear();
		return this;
	}
	public BlockCluster_SMP revert()
	{
		if(blocks.size() <= 0) return this;
		int blockNumber = random.nextInt(blocks.size());
		FragmentedBlock_SMP block = blocks.get(blockNumber);
		block.place();
		blocks.remove(blockNumber);
		return this;
	}
	
	public BlockCluster_SMP setInitially(Material material) 
	{
		for(FragmentedBlock_SMP block : blocks) 
		{
			block.getLocation().getBlock().setType(material,false);
		}
		return this;
	}
	
	public boolean isInCluster(Location location)
	{
		for(FragmentedBlock_SMP block : blocks) 
		{
			if(block.getLocation().equals(location)) return true;
		}
		return false;
	}
	
	public void delete() 
	{
		blocks.clear();
		clusters.remove(this);
	}
	
	private void core() 
	{
		tickingTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				revert();
				if(blocks.size() <= 0) 
				{
					Common.spigot.scheduler.cancelTask(tickingTask);
					System.out.println("\"BlockCluster\" Finished.");
					delete();
				}
			} 		
    	}, decayStart, decayInterval);
	}
	
	public static void cleanup() 
	{
		for(BlockCluster_SMP cluster : clusters) 
		{
			cluster.revertFully();
			//cluster.delete();
		}
	}
	
	public static boolean isInACluster(Location location) 
	{
		for(BlockCluster_SMP cluster : clusters)
		{
			if(cluster.isInCluster(location)) return true;
		}
		return false;
	}
}

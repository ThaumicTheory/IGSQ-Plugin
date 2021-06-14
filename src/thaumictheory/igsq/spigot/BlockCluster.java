package thaumictheory.igsq.spigot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockCluster 
{
	private Random random = new Random();
	private ArrayList<FragmentedBlock> blocks = new ArrayList<FragmentedBlock>();
	private static ArrayList<BlockCluster> clusters = new ArrayList<BlockCluster>();
	private int decayStart = 600;
	private int decayInterval = 5;
	
	private int tickingTask = -1;
	public BlockCluster(List<Block> blocks) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		core();
	}
	public BlockCluster(List<Block> blocks,int decayStart) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		this.decayStart = decayStart;
		core();
	}
	public BlockCluster(List<Block> blocks,int decayStart,int decayInterval) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		this.decayStart = decayStart;
		this.decayInterval = decayInterval;
		core();
	}
	
	public BlockCluster(ArrayList<Block> blocks) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		core();
	}
	public BlockCluster(ArrayList<Block> blocks,int decayStart) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		this.decayStart = decayStart;
		core();
	}
	public BlockCluster(ArrayList<Block> blocks,int decayStart,int decayInterval) 
	{
		for (Block block : blocks) this.blocks.add(new FragmentedBlock(block));
		clusters.add(this);
		this.decayStart = decayStart;
		this.decayInterval = decayInterval;
		core();
	}
	
	public BlockCluster revertFully() 
	{
		for(FragmentedBlock block : blocks) 
		{
			block.place();
		}
		blocks.clear();
		return this;
	}
	public BlockCluster revert()
	{
		if(blocks.size() <= 0) return this;
		int blockNumber = random.nextInt(blocks.size());
		FragmentedBlock block = blocks.get(blockNumber);
		block.place();
		blocks.remove(blockNumber);
		return this;
	}
	
	public BlockCluster setInitially(Material material) 
	{
		for(FragmentedBlock block : blocks) 
		{
			block.getLocation().getBlock().setType(material,false);
		}
		return this;
	}
	
	public boolean isInCluster(Location location)
	{
		for(FragmentedBlock block : blocks) 
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
		for(BlockCluster cluster : clusters) 
		{
			cluster.revertFully();
			//cluster.delete();
		}
	}
	
	public static boolean isInACluster(Location location) 
	{
		for(BlockCluster cluster : clusters)
		{
			if(cluster.isInCluster(location)) return true;
		}
		return false;
	}
}

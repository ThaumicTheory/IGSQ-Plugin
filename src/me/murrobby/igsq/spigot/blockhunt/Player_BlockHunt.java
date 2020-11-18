package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Yaml;

public class Player_BlockHunt extends GenericPlayer_BlockHunt
{
	private final Game_BlockHunt game;
	private GenericPlayer_BlockHunt generic;
	public Player_BlockHunt(Player player,Game_BlockHunt game) 
	{
		super(player);
		this.game = game;
		this.generic = new GenericPlayer_BlockHunt(player);
	}
	
	public void kill() 
    {
    	getPlayer().setHealthScale(20);
    	getPlayer().setHealth(20);
    	getPlayer().setGameMode(GameMode.ADVENTURE);
    	getPlayer().setAllowFlight(true);
    	getPlayer().setHealth(getPlayer().getHealthScale());
    	getPlayer().getInventory().clear();
    	getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000000,0, true,false));
    	getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000000,4, true,false));
    }
	public void cleanup() 
	{
		showPlayer();
		getPlayer().setGameMode(GameMode.ADVENTURE);
		getPlayer().setAllowFlight(false);
		getPlayer().setAbsorptionAmount(0);
		getPlayer().setArrowsInBody(0);
		getPlayer().setCanPickupItems(true);
		getPlayer().setExp(0);
		getPlayer().setLevel(0);
		getPlayer().setFlying(false);
		getPlayer().setFireTicks(0);
		getPlayer().setCollidable(true);
		getPlayer().setFoodLevel(20);
		getPlayer().setGliding(false);
		getPlayer().setGlowing(false);
		getPlayer().setGravity(true);
		getPlayer().setHealthScale(20);
		getPlayer().setHealth(20);
		getPlayer().setSaturation(0);
		getPlayer().setWalkSpeed(0.2f);
		getPlayer().setSprinting(false);
		Common_BlockHunt.seekersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
		Common_BlockHunt.hidersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
		getPlayer().getInventory().clear();
		for (PotionEffect effect : getPlayer().getActivePotionEffects()) getPlayer().removePotionEffect(effect.getType());
	}
	
	public void hidePlayer() 
	{
		getGame().hidePlayer(getPlayer());
	}
	public void showPlayer() 
	{
		getGame().showPlayer(getPlayer());
	}
    public Boolean isPlayerVisible(double range)
    {
    	if(getGame().isStage(Stage.PRE_SEEKER)) return false; //no one shall be visible while seekers have not spawned
    	if(isDead()) return false;
    	if(getGame().isSeeker(getPlayer())) return true;
    	for (Entity entity : getPlayer().getNearbyEntities(range, range/2, range)) 
    	{
    		if(entity instanceof Player) 
    		{
    			Player selectedPlayer = (Player) entity;
    			if(getGame().isSeeker(selectedPlayer)) return true;
    		}
    	}
    	return false;
    }
    public Boolean isPlayerSilent()
    {
    	if(getGame().isStage(Stage.PRE_SEEKER)) return true; //no one shall be heard when the seekers have not spawned
    	if(isDead()) return true;
    	if(getPlayer().isSneaking()) return true; //silent if sneaking
    	if(getGame().isHider(getPlayer()) && isPlayerVisible(Yaml.getFieldInt("visibilityrange", "blockhunt"))) return false; //if player is revealed then the player is not silent
    	if(getPlayer().isSwimming()) return false; //we dont want to encourage people to swim
    	if(getPlayer().isSprinting()) return false; //no-one can sprint silently
    	if(getGame().isSeeker(getPlayer())) return true; //seekers are allowed to walk silently
    	return false; //hiders are not allowed to walk silently
    }
	public Game_BlockHunt getGame() 
	{
		return game;
	}
	//getters
	public GenericPlayer_BlockHunt getGeneric() 
	{
		return generic;
	}
	public Seeker_BlockHunt toSeeker() 
	{
		return getGame().getSeeker(getPlayer());
		
	}
	public Hider_BlockHunt toHider() 
	{
		return getGame().getHider(getPlayer());
		
	}
	public Player_BlockHunt toPlayer()
	{
		return getGame().getPlayer(getPlayer());
		
	}
	
	public static Player_BlockHunt getPlayer(Player player) 
	{
		Game_BlockHunt game = Game_BlockHunt.getPlayersGame(player);
		if(game != null) return game.getPlayer(player);
		return null;
	}
	public static Hider_BlockHunt getHider(Player player) 
	{
		Game_BlockHunt game = Game_BlockHunt.getPlayersGame(player);
		if(game != null) return game.getHider(player);
		return null;
	}
	public static Seeker_BlockHunt getSeeker(Player player) 
	{
		Game_BlockHunt game = Game_BlockHunt.getPlayersGame(player);
		if(game != null) return game.getSeeker(player);
		return null;
	}
    //issers
	public boolean isSeeker() 
	{
		return getGame().getSeeker(getPlayer()) != null;
		
	}
	public boolean isHider() 
	{
		return getGame().getHider(getPlayer()) != null;
		
	}
	public boolean isPlayer() 
	{
		return getGame().getPlayer(getPlayer()) != null;
		
	}
}

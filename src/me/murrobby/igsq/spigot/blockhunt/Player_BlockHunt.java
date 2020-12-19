package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;

public class Player_BlockHunt extends GenericPlayer_BlockHunt
{
	private final Game_BlockHunt game;
	private SoundSystem_BlockHunt sound;
	private boolean winner;
	private int chaseState;
	public Player_BlockHunt(Player player,Game_BlockHunt game) 
	{
		super(player);
		this.game = game;
		this.sound = new SoundSystem_BlockHunt(this);
	}
	public Player_BlockHunt(Player_BlockHunt player) 
	{
		super(player.getPlayer());
		this.game = player.getGame();
		this.sound = new SoundSystem_BlockHunt(this);
	}
	
	public void kill() 
    {
		outOfGame();
    	setDead(true);
    	getSoundSystem().playKilled();
    	for(Player_BlockHunt teamMember : getTeamMembers()) teamMember.getSoundSystem().playEliminatedFriendly();
    	for(Player_BlockHunt teamMember : getEnemies()) teamMember.getSoundSystem().playEliminatedEnemy();
    }
	public void outOfGame() 
	{
		setChaseState(0);
    	getPlayer().setHealthScale(20);
    	getPlayer().setHealth(20);
    	getPlayer().setGameMode(GameMode.ADVENTURE);
    	getPlayer().setAllowFlight(true);
    	getPlayer().setInvulnerable(true);
    	getPlayer().setHealth(getPlayer().getHealthScale());
    	getPlayer().getInventory().clear();
    	for (PotionEffect effect : getPlayer().getActivePotionEffects()) getPlayer().removePotionEffect(effect.getType());
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
		getPlayer().setFallDistance(0);
		getPlayer().setInvulnerable(false);
		Common_BlockHunt.seekersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
		Common_BlockHunt.hidersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
		getPlayer().getInventory().clear();
		for (PotionEffect effect : getPlayer().getActivePotionEffects()) getPlayer().removePotionEffect(effect.getType());
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(getPlayer());
		yaml.setNameController("main");
		yaml.setChatController("mainlp");
	}
	public void delete() 
	{
		cleanup();
		getPlayer().teleport(Map_BlockHunt.getHubLocation());

		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			if(getGame().isPlayer(player) || Game_BlockHunt.getPlayersGame(player) == null) getPlayer().showPlayer(Common.spigot, player);
		}
		getSoundSystem().stopMusic();
		if(isWinner())getSoundSystem().playLeaveOnWin();
		else getSoundSystem().playLeaveOnLoss();
		getPlayer().setInvulnerable(false);
		getGame().removeHider(this);
		getGame().removeSeeker(this);
		getGame().removePlayer(this);
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(getPlayer());
		yaml.setNameController(YamlWrapper.getDefaultNameController());
		yaml.setChatController(YamlWrapper.getDefaultChatController());
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
    	if(getGame().isHider(getPlayer()) && isPlayerVisible(YamlWrapper.getBlockHuntVisibilityRange())) return false; //if player is revealed then the player is not silent
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
	public SoundSystem_BlockHunt getSoundSystem() 
	{
		return sound;
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
		if(game != null && game.isHider(player)) return game.getHider(player);
		return null;
	}
	public static Seeker_BlockHunt getSeeker(Player player) 
	{
		Game_BlockHunt game = Game_BlockHunt.getPlayersGame(player);
		if(game != null && game.isSeeker(player)) return game.getSeeker(player);
		return null;
	}
    //issers
	public boolean isWinner() 
	{
		return winner;
	}
	public boolean isChaseState() 
	{
		return chaseState > 0;
	}
	public int getChaseState() 
	{
		return chaseState;
	}
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
	public Player_BlockHunt[] getTeamMembers() 
	{
		Player_BlockHunt[] players = {};
		if(isHider()) 
		{
			for(Hider_BlockHunt hider : getGame().getHiders()) 
			{
				if(!hider.getPlayer().getUniqueId().equals(getPlayer().getUniqueId())) players = Common_BlockHunt.append(players, hider);
			}
		}
		else if(isSeeker()) 
		{
			for(Seeker_BlockHunt seeker : getGame().getSeekers()) 
			{
				if(!seeker.getPlayer().getUniqueId().equals(getPlayer().getUniqueId())) players = Common_BlockHunt.append(players, seeker);
			}
		}
		return players;
	}
	public Player_BlockHunt[] getEnemies() 
	{
		if(isHider()) 
		{
			return getGame().getSeekers();
		}
		else if(isSeeker()) 
		{
			return getGame().getHiders();
		}
		return new Player_BlockHunt[]{};
	}
	public void playSound(String sound,Location location,SoundTargets targets) 
	{
		playSound(sound,location,targets,SoundCategory.VOICE);
	}
	public void playSound(String sound,Location location,SoundTargets targets,SoundCategory type) 
	{
		Player_BlockHunt[] players = {};
		if(targets.equals(SoundTargets.SELF)) 
		{
			players = new Player_BlockHunt[]{this};
		}
		else if(targets.equals(SoundTargets.FRIENDLIES)) 
		{
			players = getTeamMembers();
		}
		else if(targets.equals(SoundTargets.ENEMIES)) 
		{
			players = getEnemies();
		}
		else if(targets.equals(SoundTargets.EVERYONE)) 
		{
			players = getGame().getPlayers();
		}
		for(Player_BlockHunt player : players) 
		{
			player.getPlayer().playSound(location,sound, type, 10000, 1);
		}
	}
	public void playSound(String sound,SoundTargets targets) 
	{
		playSound(sound,getPlayer().getLocation(),targets,SoundCategory.VOICE);
	}
	public void playSound(String sound,Location location) 
	{
		playSound(sound, location,SoundTargets.SELF,SoundCategory.VOICE);
	}
	public void playSound(String sound) 
	{
		playSound(sound, getPlayer().getLocation(),SoundTargets.SELF,SoundCategory.VOICE);
	}
	public void playSound(String sound,SoundCategory type) 
	{
		playSound(sound, getPlayer().getLocation(),SoundTargets.SELF,type);
	}
	public void playSound(String sound,Location location,SoundCategory type) 
	{
		playSound(sound, location,SoundTargets.SELF,type);
	}
	public void setWinner(boolean winner) 
	{
		this.winner = winner;
	}
	public void setChaseState(int chaseState) 
	{
		this.chaseState = chaseState;
		System.out.println(getPlayer().getName() + " : " + chaseState);
	}
}

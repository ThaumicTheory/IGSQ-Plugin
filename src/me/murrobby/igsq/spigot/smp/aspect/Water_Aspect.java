package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Weather;
import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Water_Aspect extends Base_Aspect
{

	public Water_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Water_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.WATER);
		setName("&#00FFFFAqua");
		//setColour(ChatColor.of("#00FFFF"));
		addGoodPerkDescription("Superior control over water");
		addGoodPerkDescription("Protect travelers from the depths of the sea bed");
		addBadPerkDescription("Vulnurable to worlds absent of water");
		addBadPerkDescription("Weaker on land");
		setLogo(Material.TROPICAL_FISH_BUCKET);
		
		addProtectiveEntity(EntityType.GUARDIAN);
		addProtectiveEntity(EntityType.ELDER_GUARDIAN);
		addNeutralEntity(EntityType.DROWNED);
		//addNeutralEntity(EntityType.AXOLOTYL);
		addAgressiveEntity(EntityType.BLAZE);
		setLore("TrackMania"
				+ "The first game in the series is simply called TrackMania. It was released on 21 November 2003 in France and the United Kingdom and May 2004 in the rest of the world."
				+ "TrackMania includes a series of pre-constructed tracks that players can race on to unlock \"coopers,\" the in-game currency, which can be used to buy new building blocks for their track, which includes roads, checkpoints, bends, and ramps which automatically snap to a grid. Regular roads can be dragged to create straight sections and sharp 90 degree turns. Most other blocks connect automatically when they are placed next to each other. There are three environments to choose from: Alpine, Speed, and Rally, which each offers a unique car. There are also three modes: Race, Puzzle, and Survival."
				+ "Race: Players are given a track, and they have to complete the track in a set time limit."
				+ "Puzzle: Players are given a portion of a track, and they have to complete the track in a way they think they can complete the track in the shortest time. Then, they have to complete the track under a time limit."
				+ "Survival: There are 18 tracks, and the goal is to complete all the tracks without ever coming last. There are three ghost cars, and every time you finish before two of them do, you can eliminate one of the tracks, though choices are limited. If you can beat all three ghosts, you can remove two tracks. However, over time, less ghost cars will appear on the track, but you still have to avoid coming in last."
				+ "TrackMania has three car styles, rally super minis (Renault), alpine 4x4s (Suzuki), and speed muscle cars (Ford Escort), each with numerous skins making a total of 87 skins. Using the \"car editor,\" players can create their own skins or modify the originals."
				+ "The game also features a network option, which allows players to compete via a LAN or the Internet and upload their tracks to the community-driven website, TM-Exchange, and skins and 3D car models to TrackMania Carpark."
				+ "TrackMania Sunrise"
				+ "The second game in the series, TrackMania Sunrise, was released in early 2005. It features more realistic graphics and three new environments: Bay, Coast and Island. Like the game's predecessor, each environment has its own unique car to fit the environment's characteristics. \"Island\" features fast sports cars that can turn sharply on wide roads, \"Bay\" has bouncy cars with less traction, causing accidental over-steering to happen more often, and \"Coast\" features slow cars with low traction and small roads that the cars can drift around. The game was built on an overhauled engine for better visuals, more realistic graphics and improved Internet connectivity with tracks that include skinnable advertisement panels."
				+ "Two new modes are featured: Platform and Crazy."
				+ "Platform: Players are given a track with jumps and other features that make it difficult to stay on the track. There is no time limit, but players have to finish the race while re-spawning as few times as possible."
				+ "Crazy: Players are placed on tracks with extreme obstacles, and the goal is to complete the track three times in a row under a time limit.'"
				+ "Some of the enhancements from TrackMania Sunrise were later added to the Original edition of the game, including the ability to import new car models and more customizable features."
				+ "A free expansion pack called eXtreme was released later in 2005 while the game was also repackaged as TrackMania Sunrise eXtreme. It included many new elements, including red boosters and a new stunts mode. In Stunts, players earn points by doing various stunts. They have a time limit to which they have to complete the track in, and the goal is to try and finish with the best score. When time expires, the points gradually get drained until the player makes it to the finish line."
				+ "TrackMania Original"
				+ "TrackMania Original is a version of the original TrackMania, released in mid-2005, that runs on the Sunrise graphics engine. Because of this, it features updated graphics, ad-panels, Media Tracker effects, and the ability to import 3D car models. It also features new blocks. It also has the Platform and Stunt modes."
				+ "TrackMania Nations ESWC"
				+ "On 27 January 2006, Nadeo released TrackMania Nations ESWC to promote the Electronic Sports World Cup. The game was made free so that players could train for the events, and it features one new environment called \"Stadium.\" One of the new features is the leader board, where players compete for the best times and points. The top five players are listed on the homepage. Due to being free, it became popular very quickly and a million players registered online within weeks of its launch."
				+ "TrackMania United"
				+ "Nadeo announced TrackMania United on 30 June 2006. It is a version that amalgamated all the previous editions of TrackMania into one game that features all seven environments, with the Alpine and Speed environments renamed to Snow and Desert respectively, and three modes: Race, Puzzle and Platform. It also features a peer-to-peer networking system which allows players to share custom content more easily, and a unified ranking system. A new graphics engine is featured, which accelerates the shadow mapping, which allows for more realistic shadows on the Stadium environment, which also features many new blocks, including dirt paths, water, and indoor sections."
				+ "Players who previously played TrackMania or TrackMania Sunrise can earn daily coppers in TrackMania United by registering their product keys through the game."
				+ "TrackMania Forever"
				+ "On 7 October 2007, Nadeo announced that they would be working on updated versions of TrackMania Nations and TrackMania United. Both new versions have Forever added to the end of their name and are network compatible with each other."
				+ "Some of the updates is that the new Stadium blocks added to TrackMania United are featured in TrackMania Nations Forever, allowing players to compete on the same Stadium servers, and the addition of Stunts mode. However, besides that, not much new content is featured because Nadeo was more focused on improving the engine and user interface so that the game would have a long lifespan. For instance, TrackMania United Forever does not require a CD to be in the drive."
				+ "TrackMania United Forever was released on 15 April 2008 with TrackMania Nations Forever released the next day. Both versions are available for download."
				+ "TrackMania DS"
				+ "A TrackMania game for the Nintendo DS, called TrackMania DS has been developed by Firebrand Games and released in Europe on 14 November 2008 in Europe on on 17 March 2009 in the United States. It has single-player and multiplayer modes, three of the seven environments: Rally, Desert and Stadium, and three modes: Race, Platform and Puzzle. Players can share tracks with other game owners, but not through online connectivity."
				+ "TrackMania: Build to Race"
				+ "TrackMania: Build to Race was announced by jeuxvideo.com on 30 June 2009. The game features all seven of the environments except for \"Bay\" due to its complex scenery and the Race, Puzzle and Platform modes. It has a more primitive online play system than the PC versions and a new feature where players can purchase F-class bonus tracks by earning enough metals. It was originally scheduled to be released on 20 July 2010, but was later shifted to 24 March 2011 a day before the original release date. Its release date in Europe was 23 September 2010."
				+ "TrackMania Turbo (2010)"
				+ "In 2010, a sequel to TrackMania DS, called TrackMania Turbo, was released. It features the Coast, Island, Snow and Stadium environments and nearly 150 new tracks. It also features the track editor and multiplayer mode with a new \"Hotseat\" mode, but this time, tracks can be shared through Wi-Fi."
				+ "The title of the game would later be shared with another game in the series, released in 2016. ");
		setSuggester("Space Squid#0442");
		
		setMovementSpeed(0.15f);
	}
	
	@Override
	public void aspectTick() 
	{
		if(player.getPlayer().isInWater()) 
		{
			if(player.getPlayer().hasPotionEffect(PotionEffectType.CONDUIT_POWER)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 19, 0, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 259, 0, true,false,false));
				player.getPlayer().setFlySpeed(.1f);
				setMovementSpeed(0.25f);
			}
			else 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 19, 0, true,false,false));
				player.getPlayer().setFlySpeed(.01f);
				setMovementSpeed(.2f);
			}
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 19, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 259, 0, true,false,false));
			player.getPlayer().setAllowFlight(true);
		}
		else 
		{
			
			player.getPlayer().setFlySpeed(0.2f);
			if(player.getPlayer().getGameMode().equals(GameMode.ADVENTURE) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL))player.getPlayer().setAllowFlight(false);
			
			Weather weather = Common.getWeatherEstimated(player.getPlayer());
			if(!player.getPlayer().hasPotionEffect(PotionEffectType.WATER_BREATHING)) 
			{
				if(weather.equals(Weather.RAIN)) setMovementSpeed(0.2f);
				else if(weather.equals(Weather.STORM)) setMovementSpeed(0.225f);
				else if(player.isInEnvironment(Environment.NETHER)) setMovementSpeed(0.14f);
				else setMovementSpeed(0.16f);
				if(player.isInEnvironment(Environment.NETHER))
				{
					player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 19, 0, true,false,false));
					player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 19, 0, true,false,false));
				}
			}
			else setMovementSpeed(.2f);
		}
		if(player.getPlayer().getGameMode().equals(GameMode.CREATIVE)||player.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) player.getPlayer().setAllowFlight(true);
	}
	@Override
	public void aspectSecond() 
	{
		//if(player.isInEnvironment(Environment.NETHER) && player.getPlayer().getFireTicks() < 21) player.getPlayer().setFireTicks(21);
	}
}

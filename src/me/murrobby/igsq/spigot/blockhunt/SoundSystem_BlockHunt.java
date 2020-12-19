package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;

import me.murrobby.igsq.spigot.Communication;
import me.murrobby.igsq.spigot.Dictionaries;

public class SoundSystem_BlockHunt 
{
	private Player_BlockHunt player;
	private String music = "";
	private String pack = "fs";
	private int musicUntil;
	private int musicPriority;
	public SoundSystem_BlockHunt(Player_BlockHunt player)
	{
		this.player = player;
	}
	private void setMusic(String music,int time,int priority) 
	{
		if((this.music.equals(music) && musicUntil != 0) || (musicUntil > 0 && priority <= musicPriority)) return;
		Communication.stopSound(player.getPlayer(), SoundCategory.MUSIC);
		player.getPlayer().stopSound("music");
		this.music = music;
		musicUntil = time;
		musicPriority = priority;
		player.playSound(music,SoundCategory.MUSIC);
	}
	public void stopMusic() 
	{
		Communication.stopSound(player.getPlayer(), SoundCategory.MUSIC);
		music = "";
		musicUntil = 0;
		musicPriority = 0;
	}
	
	public void playLossLoop() 
	{
		String name = "igsq.blockhunt." + pack + ".music.loss_loop";
		setMusic(name,getMusicLength(name),19);
		
	}
	public void playLoss() 
	{
		String name = "igsq.blockhunt." + pack + ".music.loss";
		setMusic(name,getMusicLength(name),20);
		
	}
	public void playLosing() 
	{
		String name = "igsq.blockhunt." + pack + ".music.losing";
		setMusic(name,getMusicLength(name),1);	
	}
	public void playImminentLoss() 
	{
		String name = "igsq.blockhunt." + pack + ".music.imminent_loss";
		setMusic(name,getMusicLength(name),3);
		
	}
	
	
	public void playWinLoop() 
	{
		String name = "igsq.blockhunt." + pack + ".music.win_loop";
		setMusic(name,getMusicLength(name),19);
		
	}
	public void playWin() 
	{
		String name = "igsq.blockhunt." + pack + ".music.win";
		setMusic(name,getMusicLength(name),20);
		
	}
	public void playWinning() 
	{
		String name = "igsq.blockhunt." + pack + ".music.winning";
		setMusic(name,getMusicLength(name),2);	
	}
	public void playImminentWin() 
	{
		String name = "igsq.blockhunt." + pack + ".music.imminent_win";
		setMusic(name,getMusicLength(name),4);
		
	}
	public void playLobby() 
	{
		String name = "igsq.blockhunt." + pack + ".music.lobby";
		setMusic(name,getMusicLength(name),-1);
		
	}
	public void playChase() 
	{
		String name = "igsq.blockhunt." + pack + ".music.chase";
		setMusic(name,getMusicLength(name),3);
		
	}
	public void playChaseFinal() 
	{
		String name = "igsq.blockhunt." + pack + ".music.chase_final";
		setMusic(name,getMusicLength(name),5);
		
	}
	public void playSeeker() 
	{
		String name = "igsq.blockhunt." + pack + ".music.seeker";
		setMusic(name,getMusicLength(name),0);
		
	}
	public void playNeutral() 
	{
		String name = "igsq.blockhunt." + pack + ".music.neutral";
		setMusic(name,getMusicLength(name),0);
		
	}
	
	public void playRoleSelect() 
	{
		String name = "igsq.blockhunt." + pack + ".role_select";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void playEliminatedEnemy() 
	{
		String name = "igsq.blockhunt." + pack + ".eliminated_enemy";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void playEliminatedFriendly() 
	{
		String name = "igsq.blockhunt." + pack + ".eliminated_friendly";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void playKilled() 
	{
		String name = "igsq.blockhunt." + pack + ".died";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void playLeaveOnLoss() 
	{
		String name = "igsq.blockhunt." + pack + ".loss";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void playLeaveOnWin() 
	{
		String name = "igsq.blockhunt." + pack + ".win";
		player.playSound(name,SoundCategory.PLAYERS);
		
	}
	
	public void soundSync() 
	{
		if(musicUntil > 0) 
		{
			musicUntil--;
			player.getPlayer().stopSound(Sound.MUSIC_CREATIVE, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_CREDITS, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_11, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_13, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_BLOCKS, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_CAT, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_CHIRP, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_FAR, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_MALL, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_MELLOHI, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_PIGSTEP, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_STAL, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_STRAD, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_WAIT, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DISC_WARD, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_DRAGON, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_END, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_GAME, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_MENU, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_NETHER_BASALT_DELTAS, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_NETHER_CRIMSON_FOREST, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_NETHER_NETHER_WASTES, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_NETHER_SOUL_SAND_VALLEY, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_NETHER_WARPED_FOREST, SoundCategory.MUSIC);
			player.getPlayer().stopSound(Sound.MUSIC_UNDER_WATER, SoundCategory.MUSIC);
		}
		
	}
	private int getMusicLength(String music) 
	{
		return Dictionaries.getMusicLength(music);
	}
	public Player_BlockHunt getPlayer() 
	{
		return player;
	}
}

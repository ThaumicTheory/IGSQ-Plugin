package thaumictheory.igsq.spigot.blockhunt;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWrapper;

public class GuiSystem_BlockHunt 
{
	private Game_BlockHunt gameInstance;
	private String time = "";
	private int focusTime;
	private String focusTarget = "";
	private String message = "";

	public GuiSystem_BlockHunt(Game_BlockHunt gameInstance) 
	{
		this.gameInstance = gameInstance;
	}
	public void updateGui() 
	{
		message = "";
		if(focusTime > 0) 
		{
			focusTime--;
			if(focusTime == 0) focusTarget = "";
		}
		if(getGame().isStage(Stage.IN_LOBBY)) lobbyGui();
		else if(getGame().isStage(Stage.PRE_SEEKER)) preSeekerGui();
		else if(getGame().isStage(Stage.IN_GAME)) gameGui();
		else if(getGame().isStage(Stage.GAME_END)) endGameGui();
		
		for(Player_BlockHunt player : getGame().getPlayers()) player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messaging.chatFormatter(message)));
	}
	
	private void lobbyGui() 
	{
		if(getGame().getPlayerCount() >= YamlWrapper.getBlockHuntMinimumPlayers() || getGame().isTestMode()) message += "&#FFb900Game Starts In &#FFFF00" + getGameTime();
		else message += "&#FF0000" + (YamlWrapper.getBlockHuntMinimumPlayers() - getGame().getPlayerCount()) + " &#00FFFFMore Players Required to Start!";
	}
	private void preSeekerGui() 
	{
		message += "&#DD0000" + getGameTime();
		message += " " + getHider() + " " + getSeeker();
	}
	private void gameGui() 
	{
		message += "&#00FFFF" + getGameTime();
		message += " " + getHider() + " " + getSeeker();
	}
	private void endGameGui() 
	{
		message += getFocusEffect() + "Game Complete";
	}
	
	public Game_BlockHunt getGame() 
	{
		return gameInstance;
	}
	
	private void setGameTime() 
	{
		int seconds = getGame().getTimer()/20;
		int minutes = 0;
		while(seconds >= 60) 
		{
			seconds-=60;
			minutes++;
		}
		if(minutes == 0) time = "" + seconds;
		time = minutes + ":" + seconds;
	}
	public String getGameTime() 
	{
		setGameTime();
		if(focusTarget.equals("time")) return getFocusEffect() + time;
		return time;
	}
	public String getHider() 
	{
		String message = getGame().getAliveHiderCount() +"/" + getGame().getHiderCount();
		if(focusTarget.equals("hiders")) return getFocusEffect() + message;
		return "&#66ccff" + message;
	}
	public String getSeeker() 
	{
		String message = getGame().getAliveSeekerCount() +"/" + getGame().getSeekerCount();
		if(focusTarget.equals("seekers")) return getFocusEffect() + message;
		return "&#ff0000" + message;
	}
	public void setFocus(String target,int time) 
	{
		if(target == null || target.equals("") || time <= 0) 
		{
			focusTarget = "";
			focusTime = 0;
		}
		else 
		{
			focusTarget = target;
			focusTime = time;
		}
	}
	private String getFocusEffect() 
	{
		int timeEffect = focusTime%20;
		if(timeEffect == 0 || timeEffect == 19) 
		{
			return "&#ff9215";
		}
		else if(timeEffect == 1 || timeEffect == 18)
		{
			return "&#ff8909";
		}
		else if(timeEffect == 2 || timeEffect == 17)
		{
			return "&#ff7f00";
		}
		else if(timeEffect == 3 || timeEffect == 16)
		{
			return "&#ff7400";
		}
		else if(timeEffect == 4 || timeEffect == 15)
		{
			return "&#ff6900";
		}
		else if(timeEffect == 5 || timeEffect == 14)
		{
			return "&#ff5d00";
		}
		else if(timeEffect == 6 || timeEffect == 13)
		{
			return "&#ff5000";
		}
		else if(timeEffect == 7 || timeEffect == 12)
		{
			return "&#ff4000";
		}
		else if(timeEffect == 8 || timeEffect == 11)
		{
			return "&#ff2c00";
		}
		else if(timeEffect == 9 || timeEffect == 10)
		{
			return "&#ff0000";
		}
		else return "";
	}
}

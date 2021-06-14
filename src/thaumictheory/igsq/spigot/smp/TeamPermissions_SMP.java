package thaumictheory.igsq.spigot.smp;

public enum TeamPermissions_SMP 
{
	INVITE(false,10),
	READ_PERMISSIONS(false,10),
	INVITE_FACTIONED(false,15),
	CLAIM(false,15),
	UNCLAIM(false,20),
	UNCLAIM_OTHERS(false,30),
	KICK(false,40),
	ENEMY(false,50),
	ALLY(false,50),
	BAN(false,60),
	MODIFY_RANKS(false,100),
	OWNER(true,9999);

	private boolean hidden;
	private int score;

	TeamPermissions_SMP(boolean hidden,int score)
	{
		this.hidden = hidden;
		this.score = score;
	}

	public boolean isImportant() 
	{
		return score >= 40;
	}

	public boolean isHidden() {
		return hidden;
	}

	public int getScore() {
		return score;
	}
	
}

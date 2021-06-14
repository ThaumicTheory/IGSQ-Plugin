package thaumictheory.igsq.spigot.smp.aspect;

import org.bukkit.Material;

import thaumictheory.igsq.spigot.smp.Player_SMP;

public class Uncertain_Aspect extends Base_Aspect
{

	public Uncertain_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Uncertain_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.UNCERTAIN);
		setName("&#522613Incertum");
		//setColour(ChatColor.of("#00FFFF"));
		addGoodPerkDescription("For the unchosen");
		addTwistedPerkDescription("Nothing good, nothing bad");
		addBadPerkDescription("Do not pick this if you can");
		setLogo(Material.PLAYER_HEAD);
		setSuggester("Space Squid#0442");
		setLore("\r\n"
				+ "\r\n"
				+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque fringilla accumsan libero, scelerisque viverra metus aliquet luctus. Donec facilisis blandit fermentum. Cras scelerisque magna ut mi euismod, eu viverra ligula feugiat. Proin scelerisque, leo ut luctus mattis, velit felis euismod justo, et volutpat est velit non turpis. Donec in vestibulum libero, quis dignissim nunc. Nam ornare nibh sed elit interdum, vitae ullamcorper ipsum euismod. Etiam fermentum diam mi, non accumsan tellus volutpat sed. Nulla facilisi. Duis hendrerit feugiat convallis. Mauris consectetur vestibulum ligula at ornare. Vestibulum nec consectetur purus. Mauris massa augue, laoreet non leo ultrices, condimentum fermentum est. Morbi nunc sem, fermentum a scelerisque in, fringilla sed lorem.\r\n"
				+ "\r\n"
				+ "Suspendisse id ipsum congue metus ullamcorper eleifend. Proin justo enim, dapibus vel blandit in, ornare eget tortor. Cras odio lacus, fringilla sit amet gravida ut, interdum ac metus. Aliquam at maximus nibh, quis sodales sapien. Aliquam consectetur mi non dolor malesuada accumsan. Etiam aliquet, ex in convallis luctus, augue erat pretium neque, eget suscipit massa lorem eu erat. Morbi vel justo dolor.\r\n"
				+ "\r\n"
				+ "Morbi ac finibus lacus. Fusce bibendum dui non nunc iaculis pulvinar. Aliquam mi odio, imperdiet et eros sit amet, varius scelerisque velit. Nunc vitae est ac lorem consequat porta. Maecenas id rutrum orci. In rhoncus sem fermentum metus hendrerit efficitur. Donec eu dui odio. Fusce dapibus tempor viverra. Quisque gravida nec nibh sed pellentesque.\r\n"
				+ "\r\n"
				+ "Donec eu porttitor eros, quis fringilla velit. Vestibulum vel blandit turpis. Vestibulum facilisis tincidunt rhoncus. Ut feugiat cursus tortor, non pretium lectus ullamcorper ac. Sed mattis tempor lorem, vel pellentesque ligula euismod a. Nullam id lectus quis ligula ultricies blandit nec nec erat. Praesent sollicitudin, mauris et aliquam feugiat, nisl purus rhoncus magna, sed pretium purus dolor eu tortor. Sed tellus odio, congue vitae gravida a, dictum eu quam.\r\n"
				+ "\r\n"
				+ "Sed sem justo, tempor ultrices quam eu, aliquet mattis risus. Aliquam at ullamcorper magna, sed fermentum libero. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris porta quam eget euismod tincidunt. Suspendisse efficitur rutrum tortor sed vestibulum. Ut leo lectus, volutpat et leo at, tincidunt ullamcorper tellus. Aliquam venenatis orci erat, in malesuada dui scelerisque id. Curabitur mollis ullamcorper mauris. Donec dapibus enim sed metus aliquet viverra. Maecenas congue magna vitae nulla molestie tempor. Nunc condimentum posuere massa, nec luctus sem posuere eu. Aenean iaculis efficitur felis, eu porta orci molestie quis. In tincidunt mattis turpis, eget semper lacus tincidunt vitae. Aenean nec mollis purus. Morbi scelerisque ante ac interdum condimentum. Sed vestibulum quis orci eu molestie.\r\n"
				+ "\r\n"
				+ "Nam a viverra odio, quis interdum purus. Suspendisse rutrum ullamcorper lorem, vel ultrices nulla cursus sed. Donec interdum est id finibus vestibulum. Quisque velit felis, sodales ac molestie a, tincidunt ac sem. Aliquam sit amet tristique tortor. Praesent mattis consectetur mauris. Duis ultrices erat non nibh volutpat, sed bibendum tellus ultrices. Mauris turpis tellus, volutpat id elit nec, lobortis aliquam sapien. Morbi mattis volutpat nibh a elementum. Aliquam porttitor enim tortor. Integer molestie dolor nisl, a tristique ante pharetra at. Proin consectetur rutrum elementum. Etiam facilisis arcu sit amet cursus bibendum. Ut dignissim iaculis odio in scelerisque. Nulla eu justo sit amet orci pellentesque hendrerit non in eros.\r\n"
				+ "\r\n"
				+ "Sed fermentum dui nec enim luctus interdum. Proin lacus quam, tempor in maximus eget, scelerisque nec libero. Donec nec tincidunt elit. Maecenas ultricies fringilla dui quis placerat. Praesent ullamcorper ligula nec diam rutrum scelerisque. Praesent bibendum urna eu magna dignissim gravida. Suspendisse arcu lorem, interdum eu ullamcorper nec, consequat id leo. Maecenas ut laoreet quam, non dignissim ante. Suspendisse tincidunt sapien risus, ut venenatis quam elementum id. Proin pulvinar a diam vitae viverra. Aliquam molestie vestibulum faucibus. Cras at accumsan nisl. Curabitur gravida purus et tellus imperdiet lobortis. Quisque id odio malesuada, viverra libero at, porta augue. Donec consequat justo vitae orci porttitor facilisis.\r\n"
				+ "\r\n"
				+ "Nunc blandit pellentesque ex, scelerisque dignissim nulla placerat sed. Vivamus sed tellus vel massa rhoncus fringilla efficitur accumsan risus. Duis id enim risus. Vestibulum mattis dignissim nibh, vel aliquam nisi eleifend a. Ut vehicula sagittis ultrices. Donec iaculis commodo nulla, a tempus tortor porttitor at. Donec fermentum id justo nec suscipit. Maecenas pretium libero vel erat vehicula pharetra. Vivamus rutrum nisi felis, ut tempus erat egestas quis. Fusce elit ante, gravida semper nisi ac, pulvinar semper purus. Phasellus convallis leo quis velit consectetur facilisis. In tristique nisl et consectetur rhoncus.\r\n"
				+ "\r\n"
				+ "Sed eu ligula orci. Nulla sed mollis leo. Suspendisse nec ante rutrum quam tristique hendrerit. Ut sit amet lacus sed tellus bibendum accumsan quis eget mi. Aliquam erat volutpat. Pellentesque ultricies ligula id quam vestibulum, nec congue sem porta. Morbi fringilla est in dui aliquam, vel tincidunt nunc fringilla. Sed ac pretium lacus. Duis fringilla risus lacus, sed rutrum diam ultrices ac.\r\n"
				+ "\r\n"
				+ "Praesent eget neque nisi. Curabitur eu lacus porta orci pretium dignissim. Integer tempus magna blandit massa posuere porttitor. Proin rhoncus, purus ac vestibulum tristique, justo ante luctus felis, a aliquam tortor risus nec lorem. Pellentesque vitae ex dapibus, commodo turpis sed, dignissim erat. Donec pulvinar velit vel urna egestas eleifend. Fusce elementum libero et mi interdum, a tincidunt elit sagittis. Donec vel finibus magna, in vulputate mi. Fusce gravida sollicitudin mi vitae pellentesque. Cras dignissim felis non purus pretium dapibus a quis sem. Aenean imperdiet maximus justo convallis feugiat.\r\n"
				+ "\r\n"
				+ "Donec commodo blandit euismod. Duis molestie, mi vitae ullamcorper lobortis, ipsum leo aliquam eros, nec viverra turpis ipsum et diam. Aenean dictum neque a ullamcorper commodo. Pellentesque dui nulla, maximus quis ligula et, tempus sodales turpis. Donec id ornare lorem. Sed bibendum ex nulla. Sed sed orci congue, sodales orci nec, lacinia orci. Maecenas faucibus, diam non tempor tincidunt, arcu velit pellentesque odio, sit amet elementum turpis nulla auctor ex. Fusce orci odio, placerat et luctus nec, dignissim id mauris. Sed id ligula sem. Quisque finibus ipsum a magna vulputate, a vestibulum est iaculis. Nam nec elementum augue, sit amet bibendum sem. Integer malesuada ac tortor sit amet malesuada. Pellentesque odio erat, tristique in tempus vel, dictum vel erat. Duis porttitor quis sem a aliquet. Vestibulum est elit, tincidunt vehicula mattis. ");
		
		setMovementSpeed(0.2f);
	}
	
	@Override
	public void aspectTick() 
	{
		
	}
	@Override
	public void aspectSecond() 
	{
		
	}
}

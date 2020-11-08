# IGSQ
Official Repo of the IGSQ Minecraft server plugin.

<img src="https://cdn.discordapp.com/attachments/741648211164528661/746779896038293574/logo.png" alt="IGSQ Logo" width="512"/>

## Tasks

**bungeecord:**
- [x] change join commands to bungee join.
- [ ] Add option for per server login locations.

**Commands:**
- [x] Move Commands to seperate files
- [ ] Fix and implement a better help menu and system.
- [ ] More usefull errors
- [x] tab completion

**Chat:**
- [x] Change Message Support to support custom inputs rather than halfs.
- [x] Hex Support
- [x] Chat Prefix
- [x] Chat Suffix

**Cleanup, optimisation and commenting:**
- [x] Remove mc_accounts lookup in spigot it is only required in 1 server bungee can handle this
- [ ] Major commenting required

## Developer Notes

**Colour Standardisation**

*Standard*
- #C8C8C8 Gray (disabled/nothing to do)
- #00FF00 Green (enabled/success)
- #CD0000 Red (Error/Deny)
- #FF0000 Blood Red (Hard Error/Hard Deny)
- #FF6161 Peach Red (Error Logging)
- #FFFF00 Yellow (Instructions)
- #FFb900 Orange (Attention)
- #00FFFF light blue (neutral)
- #a900FF purple

*Special*
- #84FF00 Expert
- #32FF82 Blood Moon
- #685985 IGSQ
- #66ccff BlockHunt Blue
- #0099ff BlockHunt Blue Secondary
- #ff0000 BlockHunt Red
- #990000 BlockHunt Red Secondary
- #ff6600 BlockHunt Powerup Cooldown
- #FFFF00 BlockHunt Highlighted
- #00ff00 BlockHunt Active Powerup
- #cccccc BlockHunt Disabled

*Ranks*
- #ff0065 council
- #fffa00 mod3
- #ffd100 mod2
- #eb913a mod

- #cbb1ff elite3
- #a183e6 elite2
- #7e65be elite
- #00f19f epic3
- #00a368 epic2
- #005a3a epic
- #77ec00 soaring
- #ff2525 flying
- #0074ff rising
- #00b7ff default


- #bdfff3 founder
- #ffcbcb birthday
- #ff61f4 nitroboost
- #ff899f supporter

- #67fd67 Developer

- #ec8d00 mod (Trial Staff LEGACY)

**Error Codes**

- ACACIA_LOG database update query failed in Database.UpdateCommand()
- BIRCH_LOG database query failed in Database.QueryCommand()
- BIRCH_PLANK database query failed to expire in Database.QueryCommand()
- COAL_BLOCK data in Discord link didnt exist or was corrupt upon reciept in DiscordLink_Security.DiscordLink()
- DIRT manually initated error in Error_Command.error()
- EMERALD_BLOCK failed to load file in Yaml.loadFile()
- HAY failed to save file changes in Yaml.saveFileChanges()
- OAK_PLANK database scalar query failed in Database.ScalarCommand()
- REDSTONE failed to create a file in Yaml.createFiles()
- REDSTONE_LAMP plugin Messaging Channel For Configurations Failed Communication.onPluginMessageReceived()
- GLOWSTONE plugin Messaging Channel For Sound Failed Communication.onPluginMessageReceived()
- STONE data in 2FA didnt exist or was corrupt upon reciept in TwoFactorAuthentication_Security.TwoFactorAuthentication()
- BEDROCK location string is not a valid location Common.parseLocationFromString()



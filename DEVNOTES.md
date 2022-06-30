# IGSQ dev notes

<img src="https://media.discordapp.net/attachments/703661349271502949/817068694575644672/IGSQ-AstroSquirrel-01.png" alt="IGSQ Logo" width="512"/>

## To Do (Listed from highest priority per category)

**Cleaning Technical Debt**
- [ ] Messaging overhaul (including colour and message standardisation, Hex Enums and logos)
- [ ] Command system overhaul (Tab complete, help menu and commands)
- [/] documentation is heavily outdated and needs to be updated
- [ ] More caught error types and bungeecord error support

**QOL**
- [ ] Add option for per server login locations.
- [ ] Hub system (/hub command)

**SMP**
- [ ] Basic Faction system (invites and claims and claim power)
- [ ] Claim Protection System (protecting claimed chunks from damage)
- [ ] Advanced Factions (Ranks, alliances,enemies, UI)
- [ ] Primative Economy
- [ ] Faction Raiding (temporary tnt, with tiers)
- [ ] text proximity chat

**Expert Mode & BlockHunt**
- On the backburner for now

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



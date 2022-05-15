# LittleLink
Tiny spigot plugin that enables Discord based whitelisting on the server

## Setup

Before you start using this plugin you will need to invite SamiLink discord bot to your server using [this link](https://discord.com/api/oauth2/authorize?client_id=770681237622095913&permissions=1099780082688&scope=bot)

Now download the latest file from releases and put it into your plugins folder

Start the server once in order to generate required config files

Now go to `plugins/LittleLink` and edit the `config.yml` based by your server needs

> If you don't know how to find a role and guild id you first need to enable discord developer mode. You can find a tutorial [here](https://www.howtogeek.com/714348/how-to-enable-or-disable-developer-mode-on-discord/)

Restart the server and enjoy your new plugin

## Information

This plugin requires a discord bot in your server in order to retrieve all data. Discord bot is hosted by me enabling the tiny footprint of the plugin.
If you want to find out more about the bot please go to [samiCraft/SamiLink](https://github.com/samiCraft/SamiLink)

Plugin does a simple HTTP GET request towards the API running inside the bot application. After processing the information it decides if it should let the player join or kick him with an info message.

> Looking for webhook integration as well? This plugin is just a core whitelisting system. It contains an api inside so other plugins can access the discord data. Webhook plugin coming soon!

## For developers

Plugin contains a simple API. In order to consume it I suggest importing the plugin jar as a library.
After receiving the instance of the plugin just access the HashMap with all the player data by `LittleLink#getPlayerData()`.

Data class is `com.pequla.link.model.DataModel`

> Before accessing the plugin instance don't forget to put LittleLink as your plugin dependency
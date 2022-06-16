# LittleLink
Tiny Spigot plugin that enables Discord based whitelisting on the server

## Setup

Before you start using this plugin you **will need to invite SamiLink discord bot** to your server using [this link](https://discord.com/api/oauth2/authorize?client_id=770681237622095913&permissions=1099780082688&scope=bot)

Now download the latest file from releases and put it into your plugins folder

Start the server once in order to generate required config files

Now go to `plugins/LittleLink` and edit the `config.yml` based by your server needs

```yaml
guild: 264801645370671114
role: 426156903555399680
```

- `role` represents the role id user is required to have in order to join
- `guild` represents the discord server id the role is located in

> If you don't know how to find a role and guild id you first need to enable discord developer mode. You can find a tutorial [here](https://www.howtogeek.com/714348/how-to-enable-or-disable-developer-mode-on-discord/)

Restart the server and enjoy your new plugin

## How it works?

This plugin requires a discord bot in your server in order to retrieve all data. Discord bot is hosted by me enabling the tiny footprint of the plugin.
If you want to find out more about the bot and its API please go to [SamiCraft/SamiLink](https://github.com/SamiCraft/SamiLink)

Plugin does a simple HTTP GET request towards the API running inside the bot application. After processing the information it decides if it should let the player join or kick it with an info message.

> Looking for webhook integration as well? This plugin is just a core whitelisting system. It contains an api inside so other plugins can access the discord data. Webhook plugin available [here](https://github.com/Pequla/LittleHooks)

## Other plugins

- [LittleWeb](https://github.com/Pequla/LittleWeb) - Provides a REST API for a more complete server status
- [LittleHooks](https://github.com/Pequla/LittleHooks) - Discord Webhook integration

> Not sure what you need? Most likely you just need [LittleHooks](https://github.com/Pequla/LittleHooks). It adds an awesome look and feel to the discord server with a bonus of boosting server activity.

## For developers

Plugin contains a simple API. In order to consume it I suggest importing the plugin jar as a library.
After receiving the instance of the plugin just access the HashMap with all the player data by `LittleLink#getPlayerData()`.

Data class is `com.pequla.link.model.DataModel`

Example setup code:
```java
@Override
public void onEnable(){
    PluginManager manager = getServer().getPluginManager();
    LittleLink plugin = (LittleLink) manager.getPlugin("LittleLink");
        
    if (plugin == null) {
        getLogger().warning("Plugin LittleLink not found");
        manager.disablePlugin(this);
        return;
    }
        // The rest of onEnable logic...
}
```

You can import the maven package from the [GitHub Maven Repository](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry)
```xml
<dependency>
    <groupId>com.pequla</groupId>
    <artifactId>little-link</artifactId>
    <version>1.4</version>
    <scope>provided</scope>
</dependency>
```

> Before accessing the plugin instance don't forget to put LittleLink as your plugin dependency in `resources/plugin.yml`
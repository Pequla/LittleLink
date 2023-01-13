# LittleLink

Tiny Spigot plugin that enables Discord based whitelisting on the server

## Setup

Before you start using this plugin you **will need to invite SuperLink discord bot** to your server
using [this link](https://discord.com/api/oauth2/authorize?client_id=770681237622095913&permissions=8&scope=applications.commands%20bot)

Now download the latest file from [releases](https://github.com/Pequla/LittleLink/releases/latest) and put it into your
plugins folder

Start the server once in order to generate required config files

Now go to `plugins/LittleLink` and edit the `config.yml` based by your server needs

```yaml
guild: 264801645370671114
role:
  use: false
  id: 426156903555399680
```

- `guild` represents the discord server id the role is located in or the server user is required to be member of
- `role.use` enable role based authentication, default is false (plugin will check for guild only)
- `role.id` represents the role id user is required to have in order to join

> If you don't know how to find a role and guild id you first need to enable discord developer mode. You can find a tutorial [here](https://www.howtogeek.com/714348/how-to-enable-or-disable-developer-mode-on-discord/)

Restart the server and enjoy your new plugin

### Lookup command

Command allows you to quickly find a Discord user based of the Minecraft username. Works only for people who have linked
accounts. The lookup is global meaning users don't need to be online or be part of your Discord server

> In order for players to use this command they will need the permission `littlelink.command.lookup`

## How it works?

This plugin requires a discord bot in your server in order to retrieve all data. Discord bot is hosted by me enabling
the tiny footprint of the plugin. If you want to find out more about the bot and its API please go
to [Pequla/SuperLink](https://github.com/Pequla/SuperLink)

Plugin does a simple HTTP GET request towards the API running inside the bot application. After processing the
information it decides if it should let the player join or kick it with an info message.

> Looking for webhook integration as well? This plugin is just a core whitelisting system. It contains an api inside so other plugins can access the discord data. Webhook plugin available [here](https://github.com/Pequla/LittleHooks)

## Other plugins

- [LittleWeb](https://github.com/Pequla/LittleWeb) - Provides a REST API for a more complete server status
- [LittleHooks](https://github.com/Pequla/LittleHooks) - Discord Webhook integration

> Not sure what you need? Most likely you just need [LittleHooks](https://github.com/Pequla/LittleHooks). It adds an awesome look and feel to the discord server with a bonus of boosting server activity.

## For developers

Plugin contains a simple API. In order to consume it I suggest importing the plugin jar as a library. After receiving
the instance of the plugin just access the HashMap with all the player data by `LittleLink#getPlayerData()`.

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

You can import the maven package from my maven repository

```xml
<repository>
    <id>pequla-repo</id>
    <url>https://maven.pequla.com/releases</url>
</repository>

<dependency>
    <groupId>com.pequla</groupId>
    <artifactId>little-link</artifactId>
    <version>1.7</version>
    <scope>provided</scope>
</dependency>
```

> Before accessing the plugin instance don't forget to put LittleLink as your plugin dependency in `resources/plugin.yml`
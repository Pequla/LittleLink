package com.pequla.link;

import com.pequla.link.model.DataModel;
import com.pequla.link.service.DataService;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class LittleLink extends JavaPlugin implements Listener {

    private final Map<UUID, DataModel> playerData = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(this, this);
        getCommand("lookup").setExecutor(new LookupCommand());
    }

    @Override
    public void onDisable() {
        playerData.clear();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handlePlayerLogin(PlayerLoginEvent event) {
        try {
            Player player = event.getPlayer();
            if (getServer().getBannedPlayers().stream().anyMatch(p -> p.getUniqueId().equals(player.getUniqueId()))) {
                return;
            }

            String uuid = player.getUniqueId().toString();
            String guild = getConfig().getString("guild");
            String role = getConfig().getString("role");
            DataModel data = DataService.getInstance().getLinkData(uuid, guild, role);
            playerData.put(player.getUniqueId(), data);
            getLogger().info("Player " + player.getName() + " authenticated as " + data.getName());
        } catch (Exception e) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        playerData.remove(event.getPlayer().getUniqueId());
    }
}

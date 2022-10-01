package net.kormons.bitcoinplugintest.listener;

import net.kormons.bitcoinplugintest.model.BitCoinManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        OfflinePlayer offlinePlayer = (OfflinePlayer) event.getPlayer();

        if (!offlinePlayer.hasPlayedBefore()) {

            BitCoinManager.setDataConfig((Player) offlinePlayer);

        }
    }

}

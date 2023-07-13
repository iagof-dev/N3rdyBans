package com.n3rdydev.bans.events;

import com.n3rdydev.bans.entity.MySql;
import com.n3rdydev.bans.settings.config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {

    @EventHandler
    public void verifyPlayer(PlayerJoinEvent e){
        if(config.get().getBoolean("bungeecord.active") != true){
            Player p = e.getPlayer();
            MySql.verifyUser(p);
        }

    }

}

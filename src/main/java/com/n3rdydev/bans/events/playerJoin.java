package com.n3rdydev.bans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {

    @EventHandler
    public void verifyPlayer(PlayerJoinEvent e){
        Player p = e.getPlayer();




    }

}
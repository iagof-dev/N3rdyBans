package com.n3rdydev.bans;

import com.n3rdydev.bans.commands.banir;
import com.n3rdydev.bans.commands.desbanir;
import com.n3rdydev.bans.entity.MySql;
import com.n3rdydev.bans.events.playerJoin;
import com.n3rdydev.bans.settings.config;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class main extends JavaPlugin {

    private static main plugin;

    public static main getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§ePlugin carregando...");
        config.start();
        MySql.getCon();
        MySql.create_table();

    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage("§aPlugin inicializado");

        //Eventos
        Listener playerJoin = new playerJoin();
        this.getServer().getPluginManager().registerEvents(playerJoin, this);

        //Commandos
        getCommand("banir").setExecutor(new banir());
        getCommand("desbanir").setExecutor(new desbanir());

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§aPlugin desligado.");
    }
}

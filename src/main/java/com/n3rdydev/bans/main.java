package com.n3rdydev.bans;

import com.n3rdydev.bans.commands.banir;
import com.n3rdydev.bans.commands.desbanir;
import com.n3rdydev.bans.entity.MySql;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class main extends JavaPlugin {
    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aPlugin inicializado");

        getCommand("banir").setExecutor(new banir());
        getCommand("desbanir").setExecutor(new desbanir());

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§aPlugin desligado");
    }
}

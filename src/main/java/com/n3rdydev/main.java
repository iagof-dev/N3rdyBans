package com.n3rdydev;

import com.n3rdydev.commands.banir;
import com.n3rdydev.events.handlePlayerJoin;
import com.n3rdydev.sql.MySql;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class main extends Plugin {

    private static main instance;

    public static main getInstance() {
        return instance;
    }

    public static void setInstance(main instance) {
        main.instance = instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("N3rdyBans | Carregando configurações...");
        config.Load();
        getLogger().info("N3rdyBans | Configurações carregadas!");


        //Eventos
        getProxy().getPluginManager().registerListener(this, new handlePlayerJoin());

        //Comandos
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new banir());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new com.n3rdydev.commands.unban());

        if (config.config.getBoolean("database.mysql.enabled")) {
            MySql.LoadConfig();
            try {
                getLogger().info("Tentando conexão com: " + config.config.getString("database.mysql.ip"));
                Connection con = MySql.CreateCon();
                if (!con.isClosed()) {
                    getLogger().info("§6================");
                    getLogger().info("§a[N3rdyBans] Inicializaçao Estabelecida!");
                    getLogger().info("§6================");
                }

            } catch (Exception e) {
                getLogger().info("§d================");
                getLogger().info("§a[N3rdyBans] Mysql nao configurado corretamente");
                getLogger().info("§d================");
            }
        }
    }

    @Override
    public void onLoad() {
        setInstance(this);
        com.n3rdydev.sql.setup.run();
    }

    @Override
    public void onDisable() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config.config, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("§c[N3rdyBans] Plugin Desabilitado.");
    }


}

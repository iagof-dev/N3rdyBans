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
        //Eventos
        getProxy().getPluginManager().registerListener(this, new handlePlayerJoin());

        //Comandos
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new banir());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new com.n3rdydev.commands.unban());
            MySql.LoadConfig();
            try {
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

    @Override
    public void onLoad() {
        setInstance(this);
        com.n3rdydev.sql.setup.run();
    }

    @Override
    public void onDisable() {
        getLogger().info("§c[N3rdyBans] Plugin Desabilitado.");
    }


}

package com.n3rdydev;

import com.n3rdydev.events.listener;
import com.n3rdydev.sql.MySql;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;

public class main extends Plugin {

    @Override
    public void onEnable(){
        //Eventos
        getProxy().getPluginManager().registerListener(this, new listener());

        //Comandos
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new com.n3rdydev.commands.ban());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new com.n3rdydev.commands.unban());

        Connection con = MySql.CreateCon();

        if(con != null){
            getLogger().info("§6================");
            getLogger().info("§a[N3rdyBans] Inicialização Estabelecida!");
            getLogger().info("§6================");

        }
        else{
            getLogger().info("§d================");
            getLogger().info("§a[N3rdyBans] Mysql Não Configurado!");
            getLogger().info("§d================");
        }
    }


    @Override
    public void onDisable(){
        getLogger().info("§c[N3rdyBans] Plugin Desabilitado.");
    }
}

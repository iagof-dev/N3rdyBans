package com.n3rdydev.settings;

import com.n3rdydev.main;
import com.n3rdydev.sql.MySql;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class config {

    private static File file;
    public static Configuration config;

    public static void setup() {

        if (!main.getInstance().getDataFolder().exists()) {
            main.getInstance().getDataFolder().mkdir();
        }

        file = new File(main.getInstance().getDataFolder(), "config.yml");


        if (!file.exists()) {
            try {
                file.createNewFile();
                config.set("database.mysql.enabled", true);
                config.set("database.mysql.ip", "localhost");
                config.set("database.mysql.port", 3306);
                config.set("database.mysql.user", "root");
                config.set("database.mysql.pass", "132490kj");
                config.set("database.mysql.db", "N3rdyBans");
                config.set("database.mysql.table", "users_banned");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void Load(){
        config.get("database.mysql.enabled", true);
        config.get("database.mysql.ip", "localhost");
        config.get("database.mysql.port", 3306);
        config.get("database.mysql.user", "root");
        config.get("database.mysql.pass", "132490kj");
        config.get("database.mysql.db", "N3rdyBans");
        config.get("database.mysql.table", "users_banned");

        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(main.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

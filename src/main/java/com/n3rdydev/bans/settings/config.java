package com.n3rdydev.bans.settings;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class config {

    public static File file;
    public static FileConfiguration config;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("N3rdyBans").getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§a[N3rdyBans] Arquivo de config.yml foi criado!");
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§c[N3rdyBans] Configuracao erro!\n" + e.getMessage());
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        Bukkit.getConsoleSender().sendMessage("[N3rdyBans] Salvando configurações...");
        try {
            config.save(file);
            Bukkit.getConsoleSender().sendMessage("§a[N3rdyBans] Configurações Salvas!");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c[N3rdyBans] Configurações não foi salva... erro:\n" + e.getMessage());
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        Bukkit.getConsoleSender().sendMessage("§a[N3rdyBans] Configurações foi atualizada!");

    }

    public static void start() {
        setup();

        //Database MySql
        get().addDefault("#Configuração do banco de dados", null);
        get().addDefault("database.MySql.ip", "127.0.0.1");
        get().addDefault("database.MySql.port", 3306);
        get().addDefault("database.MySql.user", "root");
        get().addDefault("database.MySql.pass", "123");
        get().addDefault("database.MySql.database", "N3rdyBans");
        get().addDefault("database.MySql.user_table", "n3rdybans_users");

        //Mensagens
        get().addDefault("#Mensagens", null);
        get().addDefault("Messages.loja", "loja.localhost.com");
        get().addDefault("Messages.discord", "localhost.com/discord");



        get().options().copyDefaults(true);
        get().options().copyHeader(true);
        save();
    }

}

package com.n3rdydev.bans.entity;

import com.n3rdydev.bans.settings.config;

public class server {

    public static String loja = config.get().getString("messages.loja");
    public static String discord = config.get().getString("messages.discord");

}

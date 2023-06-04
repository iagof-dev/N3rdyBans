package com.n3rdydev.commands;

import com.n3rdydev.sql.mysql;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class ban extends Command {

    public ban() {
        super("banir");
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if (p.hasPermission("n3rdydev.command.banir") || p.hasPermission("n3rdydev.*")) {
                String motivo = args[1];
                String player = args[0];

                if (sender.getName() != player) {


                    ProxiedPlayer vitima = ProxyServer.getInstance().getPlayer(player);

                    if (vitima != null) {
                        mysql.ban_user(player, motivo);
                        p.sendMessage("§aVocê baniu o jogador " + player + "!");
                    } else {
                        mysql.ban_user(player, motivo);
                        p.sendMessage("§aVocê baniu o jogador " + player + "!");
                    }
                }
                else{
                    p.sendMessage("§cNão é possível banir a si mesmo!");
                }

            } else {
                p.sendMessage("§cSem permissão.");
            }

        }
    }

}

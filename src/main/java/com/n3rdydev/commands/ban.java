package com.n3rdydev.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Proxy;

public class ban extends Command {

    public ban() {
        super("banir");
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            boolean is_temp = false;
            boolean chat_global = true;
            boolean temp_ban = false;

            //=======Verificações======
            boolean permissao = false;
            if (p.hasPermission("n3rdydev.command.banir") || p.hasPermission("n3rdydev.*")) {
                permissao = true;
            }
            if (args.length == 0) {
                p.sendMessage("[N3rdyBans] §cErro!\n§eArgumentos: /banir [Nick] [Hack/Bugs/Comportamento] <temp/-s> <tempo>\nExemplo: /banir Notch hack");
                return;
            }
            if (args.length == 1) {
                p.sendMessage("cErro! §eArgumentos: /banir [Nick] [Hack/Bugs/Comportamento] <temp/-s> <tempo>");
                return;
            }
            if (args.length > 2) {
                switch (args[2]) {
                    case "-s":
                        chat_global = false;
                        break;
                    case "temp":
                        is_temp = true;
                        break;
                    default:
                        is_temp = false;
                        chat_global = true;
                        break;
                }
            }

            if (((ProxiedPlayer) sender).getDisplayName().toLowerCase() == args[0].toLowerCase()) {
                p.sendMessage("§cNão é possível banir a si mesmo!");
                return;
            }

            //==============================

            if (permissao != false) {
                String motivo = args[1];
                String player = args[0];

                motivo = motivo.toLowerCase();

                ProxiedPlayer vitima = ProxyServer.getInstance().getPlayer(player);

                if (vitima != null) {
                    com.n3rdydev.sql.HandleBan.ban_user(player, motivo);
                    p.sendMessage("§aVocê baniu o jogador " + player + "!");
                } else {
                    com.n3rdydev.sql.HandleBan.ban_user(player, motivo);
                    p.sendMessage("§aVocê baniu o jogador " + player + "! (Offline)");
                }

                if (ProxyServer.getInstance().getPlayer(player).isConnected()) {
                    if (chat_global != false && args[1].equals("hack")) {
                        Server a = vitima.getServer();
                        ProxyServer.getInstance().broadcast("§cUm jogador foi removido desta sala por uso de trapaça.");
                    }
                }


            } else {
                p.sendMessage("§cSem permissão.");
                return;
            }


        }
    }

}

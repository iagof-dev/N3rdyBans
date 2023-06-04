package com.n3rdydev.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ban extends Command {

    public ban() {
        super("banir");
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;


            //=======Verificações======
            if (!p.hasPermission("n3rdydev.command.banir") || !p.hasPermission("n3rdydev.*")) {
                p.sendMessage("§cSem permissão.");
                return;
            }
            if (args.length == 0) {
                p.sendMessage("§cErro! Especifique o jogador.");
                return;
            }
            if (args.length == 1) {
                p.sendMessage("§cErro! Especifique o motivo.");
                p.sendMessage("§eExemplo:\n/banir <jogador> hack");
                return;
            }
            //==============================

            String motivo = args[1];
            String player = args[0];

            if (sender.getName() != player) {
                ProxiedPlayer vitima = ProxyServer.getInstance().getPlayer(player);

                if (vitima != null) {
                    com.n3rdydev.sql.HandleBan.ban_user(player, motivo);
                    p.sendMessage("§aVocê baniu o jogador " + player + "!");
                } else {
                    com.n3rdydev.sql.HandleBan.ban_user(player, motivo);
                    p.sendMessage("§aVocê baniu o jogador " + player + "!");
                }
            } else {
                p.sendMessage("§cNão é possível banir a si mesmo!");
            }


        }
    }

}

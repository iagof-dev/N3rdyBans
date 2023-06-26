package com.n3rdydev.commands;

import com.n3rdydev.sql.handleBan;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class banir extends Command {

    public banir() {
        super("banir");
    }

    public void execute(CommandSender sender, String[] args) {

        //  /banir <jogador> <motivo> <opcionais>
        //          0           1       2

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cApenas jogadores podem executar o comando!");
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (!(p.hasPermission("n3rdydev.command.banir") || p.hasPermission("n3rdydev.*"))) {
            p.sendMessage("§cSem permissão.");
            return;
        }

        boolean chat_global = true;
        boolean is_temp = false;
        boolean temp_ban = false;


        //=======Verificações======

        if (args.length == 0) {
            p.sendMessage("[N3rdyBans] §cErro!\n§eArgumentos: /banir [Nick] [Hack/Bugs/Comportamento] <temp/-s> <tempo>\nExemplo: /banir Notch hack");
            return;
        } else if (args.length == 1) {
            p.sendMessage("cErro! §eArgumentos: /banir [Nick] [Hack/Bugs/Comportamento] <temp/-s> <tempo>");
            return;
        } else if (args.length == 2) {
            switch (args[2]) {
                case "-s":
                    chat_global = false;
                    break;
                default:
                    is_temp = false;
                    chat_global = true;
                    break;
            }
        }

        if (args[0].toLowerCase() == p.getDisplayName().toLowerCase()) {
            p.sendMessage("§cNão é possível banir a si mesmo!");
            return;
        }

        //==============================

        String motivo = args[1];
        String player = args[0];

        motivo = motivo.toLowerCase();

        ProxiedPlayer vitima = ProxyServer.getInstance().getPlayer(player);

        if (vitima != null) {
            handleBan.ban_user(player, motivo);
            p.sendMessage("§aVocê baniu o jogador " + player + "!");
            if (chat_global != false && args[1].equals("hack")) {
                Server servidor = vitima.getServer();
                Collection<ProxiedPlayer> players = servidor.getInfo().getPlayers();
                for (ProxiedPlayer p_list : players) {
                    p_list.sendMessage("§cUm jogador utilizando trapaças em sua sala foi banido.");
                }
            }
        } else {
            handleBan.ban_user(player, motivo);
            p.sendMessage("§aVocê baniu o jogador " + player + "! (Offline)");
        }


    }
}



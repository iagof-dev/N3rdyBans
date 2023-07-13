package com.n3rdydev.bans.commands;

import com.n3rdydev.bans.entity.MySql;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class banir implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            System.out.println("§cApenas jogadores pode usar este comando!");
            return true;
        }

        Player p = (Player) commandSender;

        if (!(p.hasPermission("n3rdydev.command.banir") || p.hasPermission("n3rdydev.*"))) {
            p.sendMessage("§cSem permissão!");
            return true;
        }
        if (strings.length == 0) return false;

        if (strings.length == 1) {
            p.sendMessage("§cInforme o motivo");
            return true;
        }

        boolean temp = false;
        boolean silent = false;
        String reason = "";

        /*
              /banir jogador motivo [-s/-t] 1d
         */

        switch (strings[1]) {
            case "hack":
                reason = "Uso de Trapaças.";
                break;
            case "comportamento":
                reason = "Comportamento Ofensivo/Inadequado.";
                break;
            case "bugs":
                reason = "Abuso de Bugs/Glitchs.";
                break;
            default:
                reason = "Não especificado.";
                break;
        }

        if (strings.length > 2) {
            switch (strings[2]) {
                case "-s":
                    silent = true;
                    break;
                case "-t":
                    temp = true;
                    break;
            }
        }


        try {
            MySql.ban_player(strings[0], reason, p, temp, null);

        } catch (Exception e) {
            p.sendMessage(e.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);
        if (target.isOnline() || target != null){
            if (silent != true) {
                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    all.sendMessage("§cUm jogador utilizando trapaças em sua sala foi banido.");
                }
            }
            MySql.verifyUser(target);
        }



        return true;
    }
}

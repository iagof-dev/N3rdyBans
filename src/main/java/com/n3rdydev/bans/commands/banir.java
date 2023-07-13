package com.n3rdydev.bans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class banir implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            System.out.println("§cApenas jogadores pode usar este comando!");
            return true;
        }

        Player p = (Player) commandSender;

        if (!(p.hasPermission("n3rdydev.command.banir") || p.hasPermission("n3rdydev.*"))) {
            p.sendMessage("§cSem permissão!");
            return true;
        }

        if(strings.length == 0){
            return false;
        }





        return true;
    }
}

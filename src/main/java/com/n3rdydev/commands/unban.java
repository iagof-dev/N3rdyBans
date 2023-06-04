package com.n3rdydev.commands;

import com.n3rdydev.sql.MySql;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class unban extends Command {
    public unban() {
        super("unban");
    }

    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission("n3rdydev.command.banir") || sender.hasPermission("n3rdydev.*")){
            if(args[0] != null){
                boolean success = MySql.unban_user(args[0]);
                if(success != false){
                    sender.sendMessage("§aVocê desbaniu o jogador " + args[0] + "!");
                }
                else{
                    sender.sendMessage("§cErro! não identificado");
                }
            }
            else{
                sender.sendMessage("§cEspecifique um jogador...");
            }
        }
        else{
            sender.sendMessage("§cSem permissão!");
        }

    }
}

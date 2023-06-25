package com.n3rdydev.sql;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class handleBan {
    public static boolean ban_user(String nickname, String motivo) {
        String com = "";
        String final_reason = "";
        ProxiedPlayer victim = null;

        motivo = motivo.toLowerCase();

        switch (motivo) {
            case "hack":
                final_reason = "Uso de Trapaças.";
                break;
            case "comportamento":
                final_reason = "Comportamento Ofensivo/Inadequado.";
                break;
            case "bugs":
                final_reason = "Abuso de Bugs/Glitchs.";
                break;
            default:
                final_reason = "Não especificado.";
                break;
        }

        if (ProxyServer.getInstance().getPlayer(nickname) != null) {
            victim = (ProxiedPlayer) ProxyServer.getInstance().getPlayer(nickname);
            UUID victim_uuid = null;
            if (victim == null) {
                victim_uuid = victim.getUniqueId();
            }
            if (victim_uuid != null) {
                com = ("insert into " + MySql.db_table + " values(default, default, '" + victim_uuid + "', '" + nickname + "','" + final_reason + "', true)");
            } else {
                com = ("insert into " + MySql.db_table + " values(default, default, null, '" + nickname + "','" + final_reason + "', true)");
            }

        }
        else{
            com = ("insert into " + MySql.db_table + " values(default, default, null, '" + nickname + "','" + final_reason + "', true)");
        }
        try {
            Connection mysqlcon = MySql.CreateCon();
            PreparedStatement st = mysqlcon.prepareStatement(com);
            st.executeUpdate();
        } catch (Exception flsql) {
            System.out.println("§cErro!\n" + flsql);
        }
        String f_motivo = "§cVocê está banido permanentemente!\n§cMotivo: " + final_reason + "\n \n§eAdquira Seu unban: §bloja.localhost.com§f\n§cBanido injustamente? Contate-nos via localhost.com/discord";
        try {
            victim.disconnect(f_motivo);
        } catch (Exception p_error) {
            System.out.println(p_error.getMessage());
        }

        return true;
    }
}

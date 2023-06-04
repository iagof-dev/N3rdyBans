package com.n3rdydev.sql;


import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class mysql extends Plugin {

    static String db_ip = "localhost";
    static int db_port = 3306;
    static String db_user = "root";
    static String db_pass = "N3rdygamerbr@123";
    static String db_database = "N3rdyBans";
    static String db_table = "users_banned";
    static String db_type = "jdbc:mysql://";
    public static String db = db_type + db_ip + ":" + db_port + "/" + db_database + "?jdbcCompliantTruncation=false";

    public static Connection CreateCon() {
        try {
            return DriverManager.getConnection(db, db_user, db_pass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] is_banned(String name) {

        Connection con = CreateCon();

        String com = "select * from " + db_table + " where nickname='" + name + "' and active=true;";
        try {
            PreparedStatement st = con.prepareStatement(com);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String motivo = rs.getString("reason");
                String hash_ban = rs.getString("id_hash");
                String UUID = rs.getString("UUID");
                String nickname = rs.getString("nickname");
                String ativo =  "";
                if(rs.getBoolean("active") != false){
                    ativo = "true";
                }
                else{
                    ativo = "false";
                }
                String[] info = {String.valueOf(id), hash_ban, UUID, nickname, motivo, ativo};
                return info;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }


    }


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
                com = ("insert into " + db_table + " values(default, default, '" + victim_uuid + "', '" + nickname + "','" + final_reason + "', true)");
            } else {
                com = ("insert into " + db_table + " values(default, default, null, '" + nickname + "','" + final_reason + "', true)");
            }

        }
        else{
            com = ("insert into " + db_table + " values(default, default, null, '" + nickname + "','" + final_reason + "', true)");
        }
        try {
            Connection mysqlcon = mysql.CreateCon();
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

    public static boolean unban_user(String nickname) {

        String com = "update " + db_table + " set active=false where nickname='" + nickname + "';";

        try{
            Connection mysqlcon = mysql.CreateCon();
            PreparedStatement st = mysqlcon.prepareStatement(com);
            st.executeUpdate();
        }
        catch (Exception eax){

        }

        return true;

    }


}

package com.n3rdydev.bans.entity;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;
import java.sql.Timestamp;


public class MySql {

    static String ip = "localhost";
    static Integer port = 3306;
    static String user = "root";
    static String pass = "N3rdygamerbr@123";
    static String database = "n3rdybans";
    static String user_table = "n3rdybans_users";
    static String db_type = "jdbc:mysql://";
    static String db = db_type + ip + ":" + port + "/" + database + "?user=" + user + "&password=" + pass;

    public static Connection con = null;

    public static Connection init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(db);
            System.out.println("Conectado com sucesso!");
            return con;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
    static boolean sendCommand(String comando) {

        try {
            PreparedStatement st = init().prepareStatement(comando);
            Integer sucess = st.executeUpdate();
            if (sucess > 0) return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    static ResultSet getValue(String comando) {
        try {
            PreparedStatement st = init().prepareStatement(comando);
            return st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create_table() {
        String com = "create table if not exists " + user_table + "( id int auto_increment primary key, active bool not null, UUID varchar(64) not null, motivo longtext not null, autor mediumtext not null, temp bool not null, time datetime );";
        sendCommand(com);
        return;
    }

    public static boolean is_banned(UUID p_uuid) {
        String com = "select * from " + user_table + " where UUID='" + p_uuid + "'; ";

        ResultSet rs = getValue(com);

        try {
            if (rs.next()) {
                return rs.getBoolean("active");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    public static void verifyUser(Player p) {
        boolean banido = is_banned(p.getUniqueId());

        if (banido) {
            String kick_reason;
            String com = "select * from " + user_table + " where UUID='" + p.getUniqueId() + "'; ";

            ResultSet rs = getValue(com);

            try {
                if (rs.next()) {
                    p.kickPlayer("§cVocê está banido permanentemente!\n§cMotivo: " + rs.getString("motivo") + "\n \n§eAdquira Seu unban: §bloja.localhost.com§f\n§cBanido injustamente? Contate-nos via localhost.com/discord");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return;
    }


    public static void ban_player(Player vitima, String motivo, Player autor, boolean temp, Timestamp tempo) {

    }

    public static void unban_player(Player vitima) {

    }


}

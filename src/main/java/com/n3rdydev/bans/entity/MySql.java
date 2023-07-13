package com.n3rdydev.bans.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;
import java.sql.Timestamp;


public class MySql {

    static String ip = "172.93.111.154";
    static Integer port = 3306;
    static String user = "u186_1XGS9oYOEP";
    static String pass = "2M5.n+nugGUAVAJO+!7O4K4C";
    static String database = "s186_fafa";
    static String user_table = "n3rdybans_users";
    static String db_type = "jdbc:mysql://";
    public static String db = db_type + ip + ":" + port + "/" + database;

    public static Connection con = null;

    static Connection init() {
        try {
            con = DriverManager.getConnection(db, user, pass);
            return con;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Connection getCon() {
        init();
        return con;
    }

    public static void closeCon() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    static boolean getStatus() {
        try {
            if (getCon().isClosed() != true) {
                return true;
            } else {
                init();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    static boolean sendCommand(String comando) {
        getStatus();
        try {
            PreparedStatement st = init().prepareStatement(comando);
            Integer sucess = st.executeUpdate();
            if (sucess == 0) return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    static ResultSet getValue(String comando) {
        getStatus();
        try {
            PreparedStatement st = init().prepareStatement(comando);
            return st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create_table() {
        String com = "create table if not exists " + MySql.user_table + "( id int auto_increment primary key, active bool not null, hash varchar(12) default (LEFT(UUID(), 8)) unique, UUID varchar(64), nickname varchar(128) not null,motivo longtext not null, autor mediumtext not null, temp bool not null, time datetime );";
        Bukkit.getConsoleSender().sendMessage("Tabela: " + sendCommand(com));
    }

    public static boolean is_banned(String nick) {
        String com = "select * from " + user_table + " where nickname='" + nick + "'; ";
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
        boolean banido = is_banned(p.getName());

        if (banido) {
            String kick_reason;
            String com = "select * from " + user_table + " where nickname='" + p.getName() + "'; ";

            ResultSet rs = getValue(com);

            try {
                if (rs.next()) {
                    p.kickPlayer("§cVocê está banido permanentemente! (#" + rs.getString("hash") + ")\n§cMotivo: " + rs.getString("motivo") + "\n \n§eAdquira Seu unban: §bloja.localhost.com§f\n§cBanido injustamente? Contate-nos via localhost.com/discord");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return;
    }


    public static void ban_player(String nome, String motivo, Player autor, boolean temp, Timestamp tempo) {
        String com = "insert into " + MySql.user_table + " values (default, true, default, null,'" + nome + "', '" + motivo + "', '" + autor.getName() + "', false, null);";
        sendCommand(com);
        autor.sendMessage("§aJogador banido com sucesso!");
    }

    public static void unban_player(Player vitima) {

    }


}

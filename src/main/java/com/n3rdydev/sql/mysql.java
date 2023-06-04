package com.n3rdydev.sql;


import net.md_5.bungee.api.plugin.Plugin;

import java.sql.*;

public class MySql extends Plugin {

    static String db_ip = "localhost";
    static int db_port = 3306;
    static String db_user = "root";
    static String db_pass = "N3rdygamerbr@123";
    static String db_database = "N3rdyBans";
    static String db_table = "users_banned";
    static String db_type = "jdbc:mysql://";
    public static String db = db_type + db_ip + ":" + db_port + "/" + db_database + "?jdbcCompliantTruncation=false";

    public static Connection con;

    public static Connection CreateCon() {
        try {
            return DriverManager.getConnection(db, db_user, db_pass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] is_banned(String name) {

        Connection con = MySql.CreateCon();

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




    public static boolean unban_user(String nickname) {

        String com = "update " + db_table + " set active=false where nickname='" + nickname + "';";

        try{
            Connection mysqlcon = MySql.CreateCon();
            PreparedStatement st = mysqlcon.prepareStatement(com);
            st.executeUpdate();
        }
        catch (Exception eax){

        }

        return true;

    }


}

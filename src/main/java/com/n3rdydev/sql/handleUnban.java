package com.n3rdydev.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.n3rdydev.sql.MySql;

public class handleUnban {
    public static boolean unban_user(String nickname) {

        String com = "update " + MySql.db_table + " set active=false where nickname='" + nickname + "';";

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

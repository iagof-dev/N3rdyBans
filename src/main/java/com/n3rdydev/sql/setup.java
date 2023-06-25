package com.n3rdydev.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class setup {

    private static String create_table = "create table if not exists banned_users( id int auto_increment primary key, id_hash varchar(10) default (SUBSTR(MD5(RAND()), 1, 8)), uuid varchar(40) unique, nickname TINYTEXT not null, reason text not null, active bool not null default(true), temp bool not null default(false), temp_time datetime );";

    public static void run(){
        Connection con = MySql.CreateCon();
        try {
            PreparedStatement send = con.prepareStatement(create_table);
            send.executeUpdate();
        }
        catch (Exception ex){
            //
        }
    }
}

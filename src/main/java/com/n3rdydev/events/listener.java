package com.n3rdydev.events;

import com.n3rdydev.sql.mysql;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.Handshake;

import java.util.UUID;

public class listener implements Listener {
    @EventHandler
    public void onPlayerJoin(PreLoginEvent e) {

        String user_name = e.getConnection().getName();

        String[] info = mysql.is_banned(user_name);

        if (info != null && info[5].equals("true")) {

            String f_motivo = "§cVocê está banido permanentemente! (" + info[3] + ")\n§cMotivo: " + info[4] + "\nID: #" + info[1] + "\n \n§eAdquira Seu unban: §bloja.localhost.com§f\n§cBanido injustamente? Contate-nos via localhost.com/discord";

            if (info[1] != null) {
                e.getConnection().disconnect(f_motivo);
            }

        }

    }
}

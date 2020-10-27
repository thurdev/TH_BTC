package com.thur.btc.Events;

import com.thur.btc.DB.Connect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterPlayer implements Listener {

    private Connection con = Connect.con;

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        try{
            PreparedStatement stm = con.prepareStatement("SELECT * FROM players WHERE UUID = ? OR NAME = ?");
            stm.setString(1, String.valueOf(p.getUniqueId()));
            stm.setString(2, p.getName());
            ResultSet rs = stm.executeQuery();
            if(rs.next() == false){
               PreparedStatement stm2 = con.prepareStatement("INSERT INTO players (UUID, NAME, BTC) VALUES (?,?,?)");
               stm2.setString(1, String.valueOf(p.getUniqueId()));
               stm2.setString(2, p.getName());
               stm2.setInt(3, 0);
               stm2.execute();
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }

    }

}

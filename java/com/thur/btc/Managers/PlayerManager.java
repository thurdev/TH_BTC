package com.thur.btc.Managers;


import com.thur.btc.DB.Connect;
import com.thur.btc.Object.PlayerInfo;
import com.thur.btc.Utils.Global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerManager {

    public static Double getBTC(UUID uuid){
        try{
            Connection con = Connect.con;
            PreparedStatement stm = con.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            stm.setString(1, String.valueOf(uuid));
            ResultSet rs = stm.executeQuery();
            if(rs.next() != false){
                String player_uuid = rs.getString("UUID");
                if(player_uuid.equals(String.valueOf(uuid))){
                    return rs.getDouble("BTC");
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return 0d;
    }

    public static void setBTC(UUID uuid, Double btc){
        try{
            Connection con = Connect.con;
            PreparedStatement stm = con.prepareStatement("UPDATE players SET BTC = ? WHERE UUID = ?");
            stm.setDouble(1, btc);
            stm.setString(2, String.valueOf(uuid));
            stm.execute();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    public static void addBTC(UUID uuid, Double btc){
        try{
            Connection con = Connect.con;
            PreparedStatement stm = con.prepareStatement("UPDATE players SET BTC = ? WHERE UUID = ?");
            stm.setDouble(1, getBTC(uuid) + btc);
            stm.setString(2, String.valueOf(uuid));
            stm.execute();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    public static ResultSet getAllData(){
        try{
            Connection con = Connect.con;
            PreparedStatement stm = con.prepareStatement("SELECT * FROM players ORDER BY BTC DESC LIMIT 5");
            ResultSet rs = stm.executeQuery();
            if(rs.next() != false){
                return rs;
            }else{
                return null;
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }


}

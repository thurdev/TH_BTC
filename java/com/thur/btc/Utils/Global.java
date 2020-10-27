package com.thur.btc.Utils;

import com.thur.btc.Object.Booster;
import com.thur.btc.Object.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Global {
    public static ArrayList<UUID> sellingConfirm = new ArrayList<UUID>();
    public static ArrayList<UUID> buyConfirm = new ArrayList<UUID>();
    private static HashMap<Player, Player> playerOnline = new HashMap<Player,Player>();
    public static ArrayList<PlayerInfo> allPlayersBTC = new ArrayList<PlayerInfo>();
    public static HashMap<UUID, Booster> hasBoostActive = new HashMap<UUID, Booster>();
    public static HashMap<UUID, Integer> boostCountdown = new HashMap<UUID, Integer>();

    public static Boolean isOnline(String name, Player p){
        for(Player players: Bukkit.getOnlinePlayers()){
            if(players.getDisplayName().equals(name)){
                playerOnline.put(p, players);
            }
        }
        if(playerOnline.containsKey(p)){
            return true;
        }
        return false;
    }

    public static Player getPlayer(Player p){
        if(playerOnline.containsKey(p)){
            return playerOnline.get(p);
        }
        return null;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

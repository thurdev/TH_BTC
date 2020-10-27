package com.thur.btc;

import com.thur.btc.DB.Connect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NPC {

    private Location npcLocation;
    private String npcName;
    private ArmorStand npcAS;

    public NPC(String name, Location loc){
        this.npcLocation = loc;
        this.npcName = name;
    }

    public Location getLocation() {
        return npcLocation;
    }

    public void setLocation(Location npcLocation) {
        this.npcLocation = npcLocation;
    }

    public String getName() {
        return npcName;
    }

    public void setName(String npcName) {
        this.npcName = npcName;
    }

    public ArmorStand getArmorStand() {
        return npcAS;
    }

    public void setArmorStand(ArmorStand npcAS) {
        this.npcAS = npcAS;
    }

    public void spawn(NPC npc){

    }

    public void remove(NPC npc){

    }

    public void delete(NPC npc){

    }

    public static class Manager{
        public static ArrayList<NPC> spawnedNpcs = new ArrayList<NPC>();

        public static ArrayList<NPC> getSpawnedNpcs() {
            return spawnedNpcs;
        }

        public static void setSpawnedNpcs(ArrayList<NPC> spawnedNpcs) {
            Manager.spawnedNpcs = spawnedNpcs;
        }

        public static void addSpawnedNpcs(NPC npc){
            spawnedNpcs.add(npc);
        }
    }

    public static class Config{
        private static Connection con = Connect.con;
        public static void saveNpcs(NPC npc){

        }
        public static void loadNpcs(){
            NPC.Manager.setSpawnedNpcs(getNpcs());
        }
        private static ArrayList<NPC> getNpcs(){
            try{
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM npcs");
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    while(rs.next()){
                        String npcName = rs.getString("NAME");
                        String[] npcLoc = rs.getString("LOCATION").split(" ");
                        String[] npcAs = rs.getString("ARMORSTAND").split(" ");
                        Location loc = new Location(Bukkit.getWorld(npcLoc[0]), Double.parseDouble(npcLoc[1]),Double.parseDouble(npcLoc[2]),Double.parseDouble(npcLoc[3]));
                        ArrayList<NPC> npcs = new ArrayList<NPC>();
                        npcs.add(new NPC(npcName, loc));
                        return npcs;
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }

        private static boolean npcExist(NPC npc){
            try{
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM npcs WHERE NAME = ?");
                stmt.setString(1, npc.getName());
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    return true;
                }else{
                    return false;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return false;
        }
    }

}

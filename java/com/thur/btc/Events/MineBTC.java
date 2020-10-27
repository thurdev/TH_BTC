package com.thur.btc.Events;

import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.ActionBar;
import com.thur.btc.Utils.Global;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class MineBTC implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void onMine(BlockBreakEvent e){
        Block block = e.getBlock();
        Player p = e.getPlayer();
        if(instance.getConfig().getBoolean("mine_btc.mine_options.special_item_to_mine_btc")){
            for(String materials: instance.getConfig().getStringList("mine_btc.mine_options.special_item")){
                if(p.getItemInHand().getType().equals(Material.getMaterial(materials))){
                    for(String blocks: instance.getConfig().getStringList("mine_btc.block_that_give_btc")){
                        if(block.getType().equals(Material.getMaterial(blocks))){
                            genBtc(p);
                        }
                    }

                }
            }
        }else{
            for(String blocks: instance.getConfig().getStringList("mine_btc.block_that_give_btc")){
                if(block.getType().equals(Material.getMaterial(blocks))){
                    genBtc(p);
                }
            }
        }
    }


    public void genBtc(Player p){
        if(Global.hasBoostActive.containsKey(p.getUniqueId())){
            Random r = new Random();
            Double low = 0.0000000001;
            Double high = 0.00001;
            Double result = (low + (high - low) * r.nextDouble()) * Global.hasBoostActive.get(p.getUniqueId()).getMultiply();
            PlayerManager.setBTC(p.getUniqueId(), result + PlayerManager.getBTC(p.getUniqueId()));
            ActionBar.sendMessage(p, instance.getConfig().getString("mine_btc.got_btc.actionbar_message")
                    .replace("{btc_wons}", String.format("%.10f", result)) + " &b[" + Global.hasBoostActive.get(p.getUniqueId()).getMultiply() +  "x]");
        }else{
            Random r = new Random();
            Double low = 0.0000000001;
            Double high = 0.00001;
            Double result = low + (high - low) * r.nextDouble();
            PlayerManager.setBTC(p.getUniqueId(), result + PlayerManager.getBTC(p.getUniqueId()));
            ActionBar.sendMessage(p, instance.getConfig().getString("mine_btc.got_btc.actionbar_message")
                    .replace("{btc_wons}", String.format("%.10f", result)));
        }
    }

}

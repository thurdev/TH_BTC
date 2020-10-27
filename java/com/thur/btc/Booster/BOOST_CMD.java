package com.thur.btc.Booster;

import com.thur.btc.Events.UseBooster;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Global;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BOOST_CMD implements CommandExecutor {

    private final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("thbtc.admin")){
                if(args.length == 2){
                    String player_name = args[0];
                    HashMap<UUID, String> boostExist = new HashMap<UUID, String>();
                    if(Global.isOnline(player_name, p)){
                        Player onlinePlayer = Global.getPlayer(p);
                        if(Global.isNumeric(args[1])){
                            Integer booster_id = Integer.parseInt(args[1]);
                            for(String boosters: instance.getConfig().getConfigurationSection("boosters").getKeys(false)){
                                if(booster_id == Integer.parseInt(boosters)){
                                    boostExist.put(p.getUniqueId(), args[1]);
                                }
                            }
                            if(boostExist.containsKey(p.getUniqueId())){
                                ItemStack boost = new ItemStack(Material.EXP_BOTTLE, 1);
                                ItemMeta meta = boost.getItemMeta();
                                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("boosters." + booster_id + ".name")));
                                List<String> lore = new ArrayList<String>();
                                for(String lores: instance.getConfig().getStringList("boosters." + booster_id + ".lore")){
                                    lore.add(ChatColor.translateAlternateColorCodes('&',
                                            lores.replace("{multiply}", instance.getConfig().getString("boosters." + booster_id + ".multiply"))
                                            .replace("{work_time}", String.valueOf(instance.getConfig().getInt("boosters." + booster_id + ".work_time")))
                                            ));
                                }
                                meta.setLore(lore);
                                boost.setItemMeta(meta);
                                onlinePlayer.getInventory().addItem(boost);
                            }else{
                                p.sendMessage("§cEste booster não existe.");
                            }
                        }else{
                            p.sendMessage("§cInsira um valor válido.");
                        }
                    }else{
                        p.sendMessage("§cEste jogador esta offline.");
                    }
                }else{
                    p.sendMessage("§cUtilize: /btcboost <player> [booster_id]");
                }
            }
        }
        return false;
    }
}

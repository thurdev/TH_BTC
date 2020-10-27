package com.thur.btc.Events;

import com.thur.btc.Main;
import com.thur.btc.Object.Booster;
import com.thur.btc.Utils.Global;
import com.thur.btc.Utils.Heads;
import com.thur.btc.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UseBooster implements Listener {

    private final Main instance = Main.getInstance();

    private HashMap<UUID, Booster> almostUsingBooster = new HashMap<UUID, Booster>();

    @EventHandler
    public void onUse(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(p.getItemInHand().getType() != Material.AIR){
                if(p.getItemInHand().hasItemMeta()){
                    if(p.getItemInHand().getType().equals(Material.EXP_BOTTLE)){
                        for(String boosters_id: instance.getConfig().getConfigurationSection("boosters").getKeys(false)){
                            String booster_names = ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("boosters." + boosters_id + ".name"));
                            if(p.getItemInHand().getItemMeta().getDisplayName().equals(booster_names)){
                                e.setCancelled(true);
                                Inventory inv = Bukkit.createInventory(null, 3*9,"§aUsar Boost?");
                                        ItemStack is1 = new ItemStack(Material.STAINED_CLAY, 1 , (short) 5);
                                        ItemMeta meta = is1.getItemMeta();
                                        meta.setDisplayName("§aConfirmar");
                                        List<String> lore = new ArrayList<String>();
                                        lore.add(" ");
                                        lore.add("§aClique para usar o booster");
                                        lore.add(" ");
                                        meta.setLore(lore);
                                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                        is1.setItemMeta(meta);
                                        inv.setItem(15, is1);
                                        ItemStack is2 = new ItemStack(Material.STAINED_CLAY, 1 , (short) 14);
                                        ItemMeta meta2 = is2.getItemMeta();
                                        meta2.setDisplayName("§cCancelar");
                                        List<String> lore2 = new ArrayList<String>();
                                        lore2.add(" ");
                                        lore2.add("§cClique para cancelar");
                                        lore2.add(" ");
                                        meta2.setLore(lore2);
                                        meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                        is2.setItemMeta(meta2);
                                        inv.setItem(11, is2);
                                p.openInventory(inv);
                                Booster booster = new Booster(instance.getConfig().getInt("boosters." + boosters_id + ".work_time"), Integer.parseInt(instance.getConfig().getString("boosters." + boosters_id + ".multiply").replace("x", "")),instance.getConfig().getString("boosters." + boosters_id + ".name"));
                                almostUsingBooster.put(p.getUniqueId(), booster);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getName().equals("§aUsar Boost?")){
                if(e.getCurrentItem().getType() != null){
                    e.setCancelled(true);
                    if(e.getSlot() == 11){
                        Player player = (Player) e.getWhoClicked();
                        player.closeInventory();
                        if(almostUsingBooster.containsKey(player.getUniqueId())) {
                            almostUsingBooster.remove(player.getUniqueId());
                        }
                    }else if(e.getSlot() == 15){
                        Player player = (Player) e.getWhoClicked();
                        if(almostUsingBooster.containsKey(player.getUniqueId())){
                            Booster booster = almostUsingBooster.get(player.getUniqueId());
                            if(Global.hasBoostActive.containsKey(player.getUniqueId())){
                                player.sendMessage("§cVocê já possui um booster ativo.");
                                player.closeInventory();
                            }else{
                                Global.hasBoostActive.put(player.getUniqueId(), booster);
                                player.sendMessage("§aBooster ativado com sucesso!");
                                almostUsingBooster.remove(player.getUniqueId());
                                player.closeInventory();
                                Item.removeBoosterInventory(player.getInventory(), player.getItemInHand(), 1);
                                Global.boostCountdown.put(player.getUniqueId(), booster.getWork_time());
                                Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
                                    int seconds = Global.boostCountdown.get(player.getUniqueId());
                                    @Override
                                    public void run() {
                                        if(seconds >= 0){
                                            seconds--;
                                            Global.boostCountdown.remove(player.getUniqueId());
                                            Global.boostCountdown.put(player.getUniqueId(), seconds);
                                        }
                                        if(seconds == 0){
                                            Global.hasBoostActive.remove(player.getUniqueId());
                                            Global.boostCountdown.remove(player.getUniqueId());
                                            player.sendMessage("§cSeu booster acabou.");
                                        }
                                    }
                                }, 0, booster.getMultiply()*20);
                            }
                        }
                    }
                }
            }
        }
    }
}

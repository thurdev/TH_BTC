package com.thur.btc.Events;

import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BMGui_ClickEvent implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("black_market.name")))){
                if(e.getCurrentItem().getType() != null){
                    e.setCancelled(true);
                    for(String bm_itens: instance.getConfig().getConfigurationSection("black_market.itens").getKeys(false)){
                        Integer clickedSlot = instance.getConfig().getInt("black_market.itens." + bm_itens + ".slot");
                        if(e.getSlot() == clickedSlot){
                            String item_name = instance.getConfig().getString("black_market.itens." + bm_itens + ".name");
                            Player p = (Player) e.getWhoClicked();
                            p.closeInventory();
                            Double btc_player = PlayerManager.getBTC(p.getUniqueId());
                            Double price_item = instance.getConfig().getDouble("black_market.itens." + bm_itens + ".price");
                            if(btc_player >= price_item){
                                String type = instance.getConfig().getString("black_market.itens." + bm_itens + ".type");
                                if(type.equals("ITEM")){
                                    ItemStack give_item = new ItemStack(Material.getMaterial(instance.getConfig().getString("black_market.itens." + bm_itens + ".give_item.material")));
                                    ItemMeta itemMeta = give_item.getItemMeta();
                                    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                            instance.getConfig().getString("black_market.itens." + bm_itens + ".give_item.name")));
                                    List<String> lore = new ArrayList<>();
                                    for(String lores: instance.getConfig().getStringList("black_market.itens." + bm_itens + ".give_item.lore")){
                                        lore.add(ChatColor.translateAlternateColorCodes('&', lores));
                                    }
                                    itemMeta.setLore(lore);
                                    if(instance.getConfig().getBoolean("black_market.itens." + bm_itens + ".give_item.enchant_options.has_enchant")){
                                        for(String enchants: instance.getConfig().getStringList("black_market.itens." + bm_itens + ".give_item.enchant_options.enchant")){
                                            String[] enchantments = enchants.split(":");
                                            Enchantment ench = Enchantment.getByName(enchantments[0]);
                                            give_item.addUnsafeEnchantment(ench, Integer.parseInt(enchantments[1]));
                                            itemMeta.addEnchant(ench, Integer.parseInt(enchantments[1]), true);
                                        }
                                    }
                                    give_item.setItemMeta(itemMeta);
                                    p.getInventory().addItem(give_item);
                                    PlayerManager.setBTC(p.getUniqueId(), PlayerManager.getBTC(p.getUniqueId()) - price_item);
                                    p.sendMessage("§aVocê comprou '" + ChatColor.translateAlternateColorCodes('&',
                                            instance.getConfig().getString("black_market.itens." + bm_itens + ".give_item.name"))  + "§a' por '" + String.format("%.10f", price_item) + "BTC(s)'");
                                }else if(type.equals("CMD")){
                                    String command = instance.getConfig().getString("black_market.itens." + bm_itens + ".command").replace("{player}", p.getName());
                                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                                    PlayerManager.setBTC(p.getUniqueId(), PlayerManager.getBTC(p.getUniqueId()) - price_item);
                                    p.sendMessage(command);
                                    p.sendMessage("§aVocê comprou '" + ChatColor.translateAlternateColorCodes('&',
                                            instance.getConfig().getString("black_market.itens." + bm_itens + ".name"))  + "§a' por '" + String.format("%.10f", price_item) + "BTC(s)'");
                                }else if(type.equals("BOOSTER")){
                                    Integer booster_id = instance.getConfig().getInt("black_market.itens." + bm_itens + ".booster_id");
                                    PlayerManager.setBTC(p.getUniqueId(), PlayerManager.getBTC(p.getUniqueId()) - price_item);
                                    p.sendMessage("§aVocê comprou '" + ChatColor.translateAlternateColorCodes('&',
                                            instance.getConfig().getString("black_market.itens." + bm_itens + ".name"))  + "§a' por '" + String.format("%.10f", price_item) + "BTC(s)'");

                                    for(String boosters: instance.getConfig().getConfigurationSection("boosters").getKeys(false)){
                                        if(booster_id == Integer.parseInt(boosters)){
                                            String NAME = ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("boosters." + boosters + ".name"));
                                            String MULTIPLY = instance.getConfig().getString("boosters." + boosters + ".multiply");
                                            int WORK_TIME = instance.getConfig().getInt("boosters." + boosters + ".work_time");
                                            List<String> lore = new ArrayList<>();
                                            for(String lores: instance.getConfig().getStringList("boosters." + boosters + ".lore")){
                                                lore.add(ChatColor.translateAlternateColorCodes('&',
                                                        lores.replace("{multiply}", MULTIPLY)
                                                        .replace("{work_time}", String.valueOf(WORK_TIME))
                                                        ));
                                            }
                                            ItemStack boost = new ItemStack(Material.EXP_BOTTLE);
                                            ItemMeta itemMeta = boost.getItemMeta();
                                            itemMeta.setDisplayName(NAME);
                                            itemMeta.setLore(lore);
                                            boost.setItemMeta(itemMeta);
                                            p.getInventory().addItem(boost);
                                        }
                                    }

                                }else{
                                    instance.getPluginLoader().disablePlugin(instance);
                                    Bukkit.getConsoleSender().sendMessage("§c[TH_BTC] File Error: 'config.yml' invalid item type: " + type);
                                }
                            }else{
                                p.sendMessage("§cVocê não tem dinheiro o suficiente para comprar este item.");
                            }
                        }
                    }
                }
            }
        }
    }
}

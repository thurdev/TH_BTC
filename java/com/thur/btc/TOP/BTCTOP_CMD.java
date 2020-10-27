package com.thur.btc.TOP;

import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Object.PlayerInfo;
import com.thur.btc.Utils.Global;
import com.thur.btc.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BTCTOP_CMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            ResultSet rs = PlayerManager.getAllData();
                try {
                        Inventory inv = Bukkit.createInventory(null, 5*9, "§eTOP BTC's");
                        int i = 19;
                        int y = 0;
                        while(rs.next()){
                            y++;
                            i++;
                            ItemStack head = Item.getPlayerHead(rs.getString("NAME"), "§7" + rs.getString("NAME"));
                            ItemMeta meta = head.getItemMeta();
                            List<String> lore = new ArrayList<String>();
                            lore.add(" ");
                            lore.add("§7         TOP " + y);
                            lore.add(" ");
                            lore.add("§a » §6" + String.format("%.10f", rs.getDouble("BTC")) + "BTC's");
                            lore.add(" ");
                            meta.setLore(lore);
                            head.setItemMeta(meta);
                            inv.setItem(i, head);
                        }
                        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
                        ItemMeta glassMeta = glassPane.getItemMeta();
                        glassMeta.setDisplayName("§r ");
                        glassPane.setItemMeta(glassMeta);
                        inv.setItem(0, glassPane);
                        inv.setItem(1, glassPane);
                        inv.setItem(7, glassPane);
                        inv.setItem(8, glassPane);
                        inv.setItem(9, glassPane);
                        inv.setItem(17, glassPane);
                        inv.setItem(27, glassPane);
                        inv.setItem(36, glassPane);
                        inv.setItem(37, glassPane);
                        inv.setItem(35, glassPane);
                        inv.setItem(43, glassPane);
                        inv.setItem(44, glassPane);

                        p.openInventory(inv);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


        }
        return false;
    }
}

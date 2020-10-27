package com.thur.btc.Events;

import com.thur.btc.API.BTC;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Global;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BolsaGui_ClickEvent implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getName().equals("§bBolsa de Valores")){
                if(e.getCurrentItem().getType() != null){
                    e.setCancelled(true);
                    if(e.getSlot() == 15){
                        String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
                        Double btc_atual = PlayerManager.getBTC(p.getUniqueId());
                        Double valor_venda = BTC.getSellValue(instance.getConfig().getString("btc_options.currency"));
                        if(btc_atual > 0){
                            Double sell_por = valor_venda * btc_atual;
                            Global.sellingConfirm.add(p.getUniqueId());
                            p.closeInventory();
                            p.sendMessage("§aVocê tem certeza que quer vender");
                            p.sendMessage("§a'" + String.format("%.10f", btc_atual) + "BTC(s)' por '" + currency_symbol + String.format("%.2f", sell_por) + "'");
                            p.sendMessage("§7Digite §a'sim' §7 para confirmar ou §c'não' §7para cancelar.");
                        }else{
                            p.closeInventory();
                            p.sendMessage("§cVocê não possui BTC's para vender.");
                        }
                    }else if(e.getSlot() == 11){
                        Global.buyConfirm.add(p.getUniqueId());
                        p.closeInventory();
                        p.sendMessage(" ");
                        p.sendMessage("§7 Digite no chat a quantia que quer comprar.");
                        p.sendMessage("");
                    }
                }
            }
        }
    }
}
package com.thur.btc.Events;

import com.thur.btc.API.BTC;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Global;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ConfirmSellBTC implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(Global.sellingConfirm.contains(p.getUniqueId())){
            e.setCancelled(true);
            String msg = e.getMessage();
            if(msg.equalsIgnoreCase("sim")){
                String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
                Double btc_atual = PlayerManager.getBTC(p.getUniqueId());
                Double valor_venda = BTC.getSellValue(instance.getConfig().getString("btc_options.currency"));
                Double sell_por = valor_venda * btc_atual;
                p.sendMessage("§aVocê vendeu '" + String.format("%.10f", btc_atual) + "BTC(s)' por '" + currency_symbol + String.format("%.2f", sell_por) + "'");
                instance.getEconomy().depositPlayer(p, sell_por);
                PlayerManager.setBTC(p.getUniqueId(), 0d);
                Global.sellingConfirm.remove(p.getUniqueId());
            }else if(msg.equalsIgnoreCase("não")){
                p.sendMessage("§cOperação cancelada.");
                Global.sellingConfirm.remove(p.getUniqueId());
            }else{
                p.sendMessage("§7Digite §a'sim' §7ou §c'não'");
            }
        }
    }

}

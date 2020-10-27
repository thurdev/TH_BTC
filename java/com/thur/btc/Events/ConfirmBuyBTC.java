package com.thur.btc.Events;

import com.thur.btc.API.BTC;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Global;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ConfirmBuyBTC implements Listener {

    private final Main instance = Main.getInstance();

    private HashMap<UUID, Double> almostBuying = new HashMap<UUID, Double>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(Global.buyConfirm.contains(p.getUniqueId())){
            e.setCancelled(true);
            String msg = e.getMessage();
            if(Global.isNumeric(msg)){
                if(Double.parseDouble(msg) <= 0){
                    p.sendMessage("§cDigite um valor válido.");
                    Global.buyConfirm.remove(p.getUniqueId());
                }else{
                    if(msg.length() > 12){
                        p.sendMessage("§cDigite um valor menor do que 12 caracteres.");
                        Global.buyConfirm.remove(p.getUniqueId());
                    }else{
                        String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
                        Double valor_venda = BTC.getBuyValue(instance.getConfig().getString("btc_options.currency"));
                        Double valor_compra = Double.valueOf(msg) * valor_venda;
                        if(instance.getEconomy().getBalance(p) >= valor_compra){
                            p.sendMessage("§aTem certeza que quer comprar '" + String.format("%.10f", Double.parseDouble(msg)) + "BTC(s)' por '" + currency_symbol + String.format("%.2f", valor_compra) + "'");
                            p.sendMessage("§7Digite §a'sim' §7para confirmar ou §c'não' §7para cancelar.");
                            almostBuying.put(p.getUniqueId(), Double.parseDouble(msg));
                            Global.buyConfirm.remove(p.getUniqueId());
                            e.setCancelled(true);
                        }else{
                            p.sendMessage("§cVocê não tem dinheiro o suficiente para comprar '" + msg + "BTC(s)'");
                            p.sendMessage("§cVocê possui '" + currency_symbol + String.format("%.2f", instance.getEconomy().getBalance(p)) + "' falta '" + currency_symbol +
                                    String.format("%.2f", valor_compra - instance.getEconomy().getBalance(p)) + "'");
                        }
                    }
                }

            }else{
                p.sendMessage("§cDigite um valor válido.");
                Global.buyConfirm.remove(p.getUniqueId());
            }

        }

    }

    @EventHandler
    public void onConfirm(AsyncPlayerChatEvent e){
        if(almostBuying.containsKey(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
            Player p = e.getPlayer();
            String msg = e.getMessage();
            if(msg.equalsIgnoreCase("sim")){
                Double btc_to_buy = almostBuying.get(p.getUniqueId());
                String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
                Double btc_atual = PlayerManager.getBTC(p.getUniqueId());
                Double valor_compra = BTC.getBuyValue(instance.getConfig().getString("btc_options.currency"));
                Double valor_final = btc_to_buy * valor_compra;
                instance.getEconomy().withdrawPlayer(p, valor_final);
                PlayerManager.setBTC(p.getUniqueId(), PlayerManager.getBTC(p.getUniqueId()) + btc_to_buy);
                p.sendMessage("§aVocê comprou '" + String.format("%.10f", btc_to_buy) + "BTC(s)' por '" + currency_symbol + String.format("%.2f", valor_final) + "'");
                Global.buyConfirm.remove(p.getUniqueId());
                almostBuying.remove(p.getUniqueId());
            }else if(msg.equalsIgnoreCase("não")){
                p.sendMessage("§cOperação cancelada.");
                Global.buyConfirm.remove(p.getUniqueId());
                almostBuying.remove(p.getUniqueId());
            }else{
                p.sendMessage("§7Digite §a'sim' §7ou §c'não'");
            }
        }
    }

}

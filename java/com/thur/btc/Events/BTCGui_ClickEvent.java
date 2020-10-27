package com.thur.btc.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BTCGui_ClickEvent implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getName().equals("§eBTC Info")){
                if(e.getCurrentItem().getType() != null){
                    e.setCancelled(true);
                }
            }
        }
    }

}

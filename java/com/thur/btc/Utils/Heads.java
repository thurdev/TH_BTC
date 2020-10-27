package com.thur.btc.Utils;

import org.bukkit.inventory.ItemStack;

public enum Heads {
    BUY("OTIxOTI4ZWE2N2QzYThiOTdkMjEyNzU4ZjE1Y2NjYWMxMDI0Mjk1YjE4NWIzMTkyNjQ4NDRmNGM1ZTFlNjFlIn19fQ==", "Comprar"),
    SELL("MjVlZjY4ZGNiZDU4MjM0YmE3YWVlMmFkOTFjYTZmYTdjZTIzZjlhMzIzNDViNDhkNmU1ZjViODZhNjhiNWIifX19", "Vender"),
    CONFIRM("OTIxOTI4ZWE2N2QzYThiOTdkMjEyNzU4ZjE1Y2NjYWMxMDI0Mjk1YjE4NWIzMTkyNjQ4NDRmNGM1ZTFlNjFlIn19fQ==", "Confirmar"),
    CANCEL("MjVlZjY4ZGNiZDU4MjM0YmE3YWVlMmFkOTFjYTZmYTdjZTIzZjlhMzIzNDViNDhkNmU1ZjViODZhNjhiNWIifX19", "Cancelar");

    private ItemStack item;
    private String idTag;
    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    private Heads(String texture, String id)
    {
        item = Item.createSkull(prefix + texture, id);
        idTag = id;
    }


    public ItemStack getItemStack()
    {
        return item;
    }

    public String getName()
    {
        return idTag;
    }
}

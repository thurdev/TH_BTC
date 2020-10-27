package com.thur.btc.Object;

import com.thur.btc.Utils.Global;

import java.util.UUID;

public class PlayerInfo {
    private Double btc;
    private UUID uuid;
    private String name;
    public PlayerInfo(UUID uuid, String name, Double btc){
        setUuid(uuid);
        setBtc(btc);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

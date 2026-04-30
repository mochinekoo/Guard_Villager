package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.status.KitType;

import java.util.UUID;

public abstract class KitBase {

    protected final UUID uuid;
    protected final KitType kitType;
    protected int coin;

    public KitBase(UUID uuid, KitType kitType) {
        this.uuid = uuid;
        this.kitType = kitType;
        this.coin = 0;
    }

    public UUID getUUID() {
        return uuid;
    }

    public KitType getKitType() {
        return kitType;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        if (coin < 0) coin = 0;
        this.coin = coin;
    }

    public abstract void runClickAction();
    public abstract void addKitItem();

    public abstract void release();

}

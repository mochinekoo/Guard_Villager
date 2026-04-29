package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.status.KitType;

import java.util.UUID;

public abstract class KitBase {

    protected final UUID uuid;
    protected final KitType kitType;

    public KitBase(UUID uuid, KitType kitType) {
        this.uuid = uuid;
        this.kitType = kitType;
    }

    public UUID getUUID() {
        return uuid;
    }

    public KitType getKitType() {
        return kitType;
    }

    public abstract void runClickAction();
    public abstract void addKitItem();

    public abstract void release();

}

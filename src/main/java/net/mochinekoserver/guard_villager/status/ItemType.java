package net.mochinekoserver.guard_villager.status;

public enum ItemType {
    UNDROPPABLE("ドロップ不可能"),
    UNTRANSFERABLE("譲渡不可能"),
    KIT_ITEM("キットアイテム");

    private final String name; //あまり使わない

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

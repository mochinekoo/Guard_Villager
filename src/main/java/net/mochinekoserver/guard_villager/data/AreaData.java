package net.mochinekoserver.guard_villager.data;

import net.mochinekoserver.guard_villager.manager.DeserializedJson;

import java.util.Map;

public class AreaData extends DeserializedJson {

    public Map<String, GuardData> guard_data;

    public static class GuardData {
        public int[] start;
        public int[] end;
    }

}
